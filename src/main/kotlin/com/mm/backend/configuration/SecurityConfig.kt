package com.mm.backend.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.mm.backend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userDetailsService: UserDetailsService,
    private val userRepository: UserRepository,
) {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    fun authenticationManager(http: HttpSecurity, bCryptPasswordEncoder: BCryptPasswordEncoder): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build()
    }

    @Bean
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        val customAuthenticationFilter =
            CustomAuthenticationFilter(authenticationManager, jwtTokenUtil)
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login")
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests { authz ->
            authz.antMatchers("/api/auth/**").permitAll()
            authz.antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN")
            authz.anyRequest().authenticated()
        }
        http.addFilter(customAuthenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(jwtTokenUtil, userRepository, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}

class CustomAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) :
    UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authentication: Authentication
    ) {
        val user = authentication.principal as User
        val accessToken = jwtTokenUtil.generateTokenWithRoles(user)
        val refreshToken = jwtTokenUtil.generateToken(user)
        val tokens = mapOf(
            "access_token" to accessToken,
            "refresh_token" to refreshToken
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }
}

class CustomAuthorizationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsService
) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.matches(Regex("^/api/auth.*"))) {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader = request.getHeader("Authorization")
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val token = authorizationHeader.substring("Bearer ".length)
                    val username = jwtTokenUtil.getUsernameFromToken(token)
                    val user = userRepository.findByUsername(username)?: throw UsernameNotFoundException("User not found with username: $username")
                    val userDetails = userDetailsService.loadUserByUsername(user.username)
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (exception: Exception) {
                    response.setHeader("error", exception.message)
                    response.status = HttpStatus.FORBIDDEN.value()
                    val error = mapOf("error_message" to exception.message)
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}