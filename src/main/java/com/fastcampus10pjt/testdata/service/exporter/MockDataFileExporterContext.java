package com.fastcampus10pjt.testdata.service.exporter;

import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MockDataFileExporterContext {

    private final Map<ExportFileType, MockDataFileExporter> mockDataFileExporterMap;

    public MockDataFileExporterContext(List<MockDataFileExporter> mockDataFileExporters) {
        this.mockDataFileExporterMap = mockDataFileExporters.stream()
                .collect(Collectors.toMap(MockDataFileExporter::getType, Function.identity()));
    }

    public String export(ExportFileType fileType, TableSchemaDto dto, Integer rowCount) {
        MockDataFileExporter fileExporter = mockDataFileExporterMap.get(fileType);

        if (fileExporter == null) {
            throw new IllegalArgumentException("지원하지 않는 파일 유형입니다: " + fileType);
        }

        return fileExporter.export(dto, rowCount);
    }
}
