package com.lib.repository;

import com.lib.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT count(*) FROM Book b join b.imageFile img where img.id=:id")
    Integer findBookCountByImageId(@Param("id") String id);





}
