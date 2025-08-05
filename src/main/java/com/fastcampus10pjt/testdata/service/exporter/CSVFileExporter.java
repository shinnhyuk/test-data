package com.fastcampus10pjt.testdata.service.exporter;

import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import org.springframework.stereotype.Component;

@Component
public class CSVFileExporter extends DelimiterBasedFileExporter implements MockDataFileExporter {

    @Override
    public ExportFileType getType() {
        return ExportFileType.CSV;
    }

    @Override
    public String getDelimiter() {
        return ",";
    }

}
