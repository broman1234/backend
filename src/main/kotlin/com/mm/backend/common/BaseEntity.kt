package com.mm.backend.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity() {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    lateinit var createdAt: Instant

    @LastModifiedDate
    @Column(name = "updated_at", updatable = true)
    lateinit var updatedAt: Instant
}