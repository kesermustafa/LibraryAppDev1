package com.lib.repository;

import com.lib.domain.Loan;
import com.lib.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    boolean existsByUser(User user);


    Page<Loan> findAllByUser(User user, Pageable pageable);


    List<Loan> findAllByUserId(Long id);


    void existsByUserId(Long id);

    List<Loan> findByBookId(Long id);


}
