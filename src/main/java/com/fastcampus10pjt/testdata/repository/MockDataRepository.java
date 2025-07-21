package com.fastcampus10pjt.testdata.repository;

import com.fastcampus10pjt.testdata.domain.MockData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockDataRepository extends JpaRepository<MockData, Long> {
}
