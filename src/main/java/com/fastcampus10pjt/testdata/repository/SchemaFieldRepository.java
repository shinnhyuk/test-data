package com.fastcampus10pjt.testdata.repository;

import com.fastcampus10pjt.testdata.domain.SchemaField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaFieldRepository extends JpaRepository<SchemaField, Long> {
}