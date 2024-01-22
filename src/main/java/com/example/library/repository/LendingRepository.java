package com.example.library.repository;

import com.example.library.domain.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {

}
