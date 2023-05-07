package com.lib.repository;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    boolean existsByUser(User user);


    Page<Loan> findAllByUser(User user, Pageable pageable);


    List<Loan> findAllByUserId(Long id);


    void existsByUserId(Long id);

    List<Loan> findByBookId(Long id);

    @Query("SELECT l from Loan l " +
            "where l.expireDate<:current and  l.returnDate is null and l.user.id=:userId")
    List<Loan> findExpiredLoansBy(@Param("userId")Long userId, @Param("current") LocalDateTime current);


    List<Loan> findLoansByUserIdAndExpireDateIsNull(User user);

    @Query("SELECT l from Loan l where l.user.id=:id")
    Page<Loan> findAllWithPageByUserId(@Param ("id")Long id, Pageable pageable);


    Page<Loan> findAllByBookId(Book book, Pageable pageable);

    @Query("SELECT l from Loan l WHERE l.id = :loanId and l.user.id = :userId")
    Optional<Loan> findByIdAndUserId(@Param("loanId")Long loanId, @Param("id") Long userId);


    Optional<Loan> findByUserId(Long userId);
}
