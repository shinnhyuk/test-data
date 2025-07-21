package com.fastcampus10pjt.testdata.repository;

import com.fastcampus10pjt.testdata.domain.TableSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableSchemaRepository extends JpaRepository<TableSchema, Long> {
}