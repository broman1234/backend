package com.mm.backend.dto

import com.mm.backend.models.Book
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class BookRequest(
    val title: String? = null,
    val author: String? = null,
    val category: String? = null,
    val publisher: String? = null,
)

object BookSpecifications {

    fun withRequest(bookRequest: BookRequest): Specification<Book> {
        return Specification { root: Root<Book>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (!bookRequest.title.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + bookRequest.title + "%"))
            }

            if (!bookRequest.author.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + bookRequest.author + "%"))
            }

            if (!bookRequest.category.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + bookRequest.category + "%"))
            }

            if (!bookRequest.publisher.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("publisher"), "%" + bookRequest.publisher + "%"))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}
