package com.fastcampus10pjt.testdata.domain.dto.response;

import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;

import java.time.LocalDateTime;

public record SimpleTableSchemaResponse(
        String schemaName,
        String userId,
        LocalDateTime modifiedAt
) {

    public static SimpleTableSchemaResponse fromDto(TableSchemaDto dto) {
        return new SimpleTableSchemaResponse(dto.schemaName(), dto.userId(), dto.modifiedAt());
    }

}
