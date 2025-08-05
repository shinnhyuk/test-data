package com.fastcampus10pjt.testdata.service.exporter;

import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import org.springframework.stereotype.Component;

@Component
public class TSVFileExporter extends DelimiterBasedFileExporter implements MockDataFileExporter {

    @Override
    public ExportFileType getType() {
        return ExportFileType.TSV;
    }

    @Override
    public String getDelimiter() {
        return "\t";
    }

}
