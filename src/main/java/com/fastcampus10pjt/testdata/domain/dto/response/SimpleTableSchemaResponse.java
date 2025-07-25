package com.fastcampus10pjt.testdata.domain.dto.response;

public record SimpleTableSchemaResponse(
        String schmaName,
        String userId
) {
    public static SimpleTableSchemaResponse fromDto(String schemaName, String userId) {
        return new SimpleTableSchemaResponse(schemaName, userId);
    }
}
