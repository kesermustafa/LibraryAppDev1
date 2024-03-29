package com.lib.repository;

import com.lib.domain.Author;
import com.lib.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Page<Author> findAll(Pageable pageable);

    @Query("select b from Book b where b.author.id=:authorId")
    List<Book> findAllByAuthorId(@Param("authorId")Long authorId);
}