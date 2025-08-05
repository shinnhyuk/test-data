package com.fastcampus10pjt.testdata.service;

import com.fastcampus10pjt.testdata.domain.TableSchema;
import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;
import com.fastcampus10pjt.testdata.repository.TableSchemaRepository;
import com.fastcampus10pjt.testdata.service.exporter.MockDataFileExporterContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchemaExportService {

    private final MockDataFileExporterContext mockDataFileExporterContext;
    private final TableSchemaRepository tableSchemaRepository;

    public String export(ExportFileType fileType, TableSchemaDto dto, Integer rowCount) {
        if (dto.userId() != null) {
            tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())
                    .ifPresent(TableSchema::markExported);
        }

        return mockDataFileExporterContext.export(fileType, dto, rowCount);
    }
}
