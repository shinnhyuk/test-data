package com.fastcampus10pjt.testdata.domain.dto.request;

import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;

import java.util.List;
import java.util.stream.Collectors;

public record TableSchemaRequest(
        String schemaName,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaRequest of(String schemaName, List<SchemaFieldRequest> schemaFields) {
        return new TableSchemaRequest(schemaName, schemaFields);
    }

    public TableSchemaDto toDto(String userId) {
        return TableSchemaDto.of(
                schemaName,
                userId,
                null, // exportedAt is not provided in the request
                schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
