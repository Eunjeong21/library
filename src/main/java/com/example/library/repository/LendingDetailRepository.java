package com.example.library.repository;

import com.example.library.domain.LendingDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingDetailRepository extends JpaRepository<LendingDetail, Long> {

    List<LendingDetail> findFirst5ByMemberIdOrderByIdDesc(Long memberId);
}
