package com.fastcampus10pjt.testdata.controller;

import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import com.fastcampus10pjt.testdata.domain.constant.MockDataType;
import com.fastcampus10pjt.testdata.domain.dto.request.TableSchemaExportRequest;
import com.fastcampus10pjt.testdata.domain.dto.request.TableSchemaRequest;
import com.fastcampus10pjt.testdata.domain.dto.response.SchemaFieldResponse;
import com.fastcampus10pjt.testdata.domain.dto.response.TableSchemaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TableSchemaController {

    private final ObjectMapper mapper;

    @GetMapping("/table-schema")
    public String tableSchema(Model model) {
        var tableSchema = defaultTableSchema();

        model.addAttribute("tableSchema", tableSchema);
        model.addAttribute("mockDataTypes", MockDataType.toObjects());
        model.addAttribute("fileTypes", Arrays.stream(ExportFileType.values()).toList());

        return "table-schema";
    }

    @PostMapping("/table-schema")
    public String createOrUpdateTableSchema(
            TableSchemaRequest tableSchemaRequest,
            RedirectAttributes redirectAttrs
    ) {
        redirectAttrs.addFlashAttribute("tableSchemaRequest", tableSchemaRequest);
        return "redirect:/table-schema";
    }

    @GetMapping("/table-schema/my-schemas")
    public String mySchema() {
        return "my-schemas";
    }

    @PostMapping("/table-schema/my-schemas/{schemaName}")
    public String deleteMySchma(
            @PathVariable String schemaName,
            RedirectAttributes redirectAttrs){
        return "redirect:/my-schemas";
    }

    @GetMapping("/table-schema/export")
    public ResponseEntity<String> exportTableSchema(TableSchemaExportRequest tableSchemaExportRequest) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table_schema.txt")
                .body(json(tableSchemaExportRequest)); // TODO: 나중에 데이터 바꿔야 함
    }

    private TableSchemaResponse defaultTableSchema() {
        return new TableSchemaResponse(
                "schema_name",
                "uno",
                List.of(
                        new SchemaFieldResponse("fieldName1", MockDataType.STRING, 1, 0, null, null),
                        new SchemaFieldResponse("fieldName2", MockDataType.NUMBER, 2, 10, null, null),
                        new SchemaFieldResponse("fieldName3", MockDataType.NAME, 3, 20, null, null)
                )
        );
    }

    private String json(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

}
