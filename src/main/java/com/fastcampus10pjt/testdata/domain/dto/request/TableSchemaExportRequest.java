package com.fastcampus10pjt.testdata.domain.dto.request;

import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public record TableSchemaExportRequest(
        String schemaName,
        Integer rowCount,
        ExportFileType fileType,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaExportRequest of(
            String schemaName,
            Integer rowCount,
            ExportFileType fileType,
            List<SchemaFieldRequest> schemaFields
    ) {
        return new TableSchemaExportRequest(schemaName, rowCount, fileType, schemaFields);
    }

    public TableSchemaDto toDto(String userId) {
        return TableSchemaDto.of(
                schemaName(),
                userId,
                null, // exportedAt is not provided in the request
                schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
