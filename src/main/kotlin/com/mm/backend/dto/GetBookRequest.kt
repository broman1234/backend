package com.mm.backend.dto

import com.mm.backend.models.Book
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class GetBookRequest(
    val title: String? = null,
    val author: String? = null,
    val category: String? = null,
    val publisher: String? = null,
)

data class UpdatedBookRequest(
    val id: Long,
    val title: String? = null,
    val author: String? = null,
    val category: String? = null,
    val publisher: String? = null,
    val description: String? = null
)

object BookSpecifications {

    fun withRequest(getBookRequest: GetBookRequest): Specification<Book> {
        return Specification { root: Root<Book>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (!getBookRequest.title.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + getBookRequest.title + "%"))
            }

            if (!getBookRequest.author.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + getBookRequest.author + "%"))
            }

            if (!getBookRequest.category.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + getBookRequest.category + "%"))
            }

            if (!getBookRequest.publisher.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("publisher"), "%" + getBookRequest.publisher + "%"))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}
