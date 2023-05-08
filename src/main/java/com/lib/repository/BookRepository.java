package com.lib.repository;

import com.lib.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT count(*) FROM Book b join b.imageFile img where img.id=:id")
    Integer findBookCountByImageId(@Param("id") String id);


    @EntityGraph(attributePaths = {"author", "publisher", "category", "imageFile"})
    Optional<Book> findById(Long id);

    boolean existsByIsbn(String isbn);

    @EntityGraph(attributePaths = {"author", "publisher", "category","imageFile"})
    Page<Book> findAll(Pageable pageable);


    Page<Book> findBookByLoanableIsFalse(Pageable pageable);
}
