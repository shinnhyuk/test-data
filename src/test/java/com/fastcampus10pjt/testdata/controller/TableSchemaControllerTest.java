package com.fastcampus10pjt.testdata.controller;

import com.fastcampus10pjt.testdata.config.SecurityConfig;
import com.fastcampus10pjt.testdata.domain.constant.ExportFileType;
import com.fastcampus10pjt.testdata.domain.constant.MockDataType;
import com.fastcampus10pjt.testdata.domain.dto.TableSchemaDto;
import com.fastcampus10pjt.testdata.domain.dto.request.SchemaFieldRequest;
import com.fastcampus10pjt.testdata.domain.dto.request.TableSchemaExportRequest;
import com.fastcampus10pjt.testdata.domain.dto.request.TableSchemaRequest;
import com.fastcampus10pjt.testdata.domain.dto.response.SimpleTableSchemaResponse;
import com.fastcampus10pjt.testdata.domain.dto.security.GithubUser;
import com.fastcampus10pjt.testdata.service.TableSchemaService;
import com.fastcampus10pjt.testdata.util.FormDataEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@Disabled("우선 구현 전 테스트를 먼저 작성함. 테스트로 스펙을 전달하고, 아직 구현이 없으므로 비활성화.")
@DisplayName("[Controller] 테이블 스키마 컨트롤러 테스트")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(TableSchemaController.class)
class TableSchemaControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private FormDataEncoder formDataEncoder;
    @Autowired private ObjectMapper mapper;

    @MockitoBean private TableSchemaService tableSchemaService;

    @DisplayName("[GET] 테이블 스키마 조회, 비로그인 최초 진입 (정상)")
    @Test
    void givenNothing_whenRequesting_thenShowsTableSchemaView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/table-schema"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                .andExpect(model().attributeExists("mockDataTypes"))
                .andExpect(model().attributeExists("fileTypes"))
                .andExpect(view().name("table-schema"));

    }

    @DisplayName("[GET] 테이블 스키마 조회, 로그인 + 특정 테이블 스키마 (정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenRequesting_thenShowsTableSchemaView() throws Exception {
        // Given
        var githubUser = new GithubUser("test-id", "test-name", "test@email.com");
        var schemaName = "test_schema";

        // When & Then
        mvc.perform(
                get("/table-schema")
                        .queryParam("schemaName", schemaName)
                        .with(oauth2Login().oauth2User(githubUser))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                //.andExpect(model().attribute("tableSchema", hasProperty("schemaName", schemaName)))
                .andExpect(model().attributeExists("mockDataTypes"))
                .andExpect(model().attributeExists("fileTypes"))
                .andExpect(content().string(containsString(schemaName))) // html 전체 검사로
                .andExpect(view().name("table-schema"));

    }

    @DisplayName("[POST] 테이블 스키마 생성, 변경 (정상)")
    @Test
    void givenTableSchemaRequest_when_CreatingOrUpdating_thenRedirectsToTableSchemaView() throws Exception {
        // Given
        var githubUser = new GithubUser("test-id", "test-name", "test@email.com");
        TableSchemaRequest request = TableSchemaRequest.of(
                "test_schema",
                "홍길동",
                List.of(
                        SchemaFieldRequest.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldRequest.of("name", MockDataType.NAME, 2, 10, null, null),
                        SchemaFieldRequest.of("age", MockDataType.NUMBER, 3, 20, null, null)
                )
        );

        // When & Then
        mvc.perform(
                        post("/table-schema")
                                .content(formDataEncoder.encode(request))
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(oauth2Login().oauth2User(githubUser))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("tableSchemaRequest", request))
                .andExpect(redirectedUrl("/table-schema"));
    }

    @DisplayName("[GET] 내 스키마 목록 조회 (비로그인)")
    @Test
    void givenNothing_whenRequestingMySchemas_thenRedirectsToLogin() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/table-schema/my-schemas"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/oauth2/authorization/github")); // Ant style pattern
        then(tableSchemaService).shouldHaveNoInteractions(); // 서비스 호출 없음
    }

    @DisplayName("[GET] 내 스키마 목록 조회 (정상)")
    @Test
    void givenAuthenticatedUser_whenRequestingMySchemas_thenShowsMySchemasView() throws Exception {
        // Given
        var githubUser = new GithubUser("test-id", "test-name", "test@email.com");
        given(tableSchemaService.loadMySchemas(githubUser.id()))
                .willReturn(List.of());

        // When & Then
        mvc.perform(
                get("/table-schema/my-schemas")
                        .with(oauth2Login().oauth2User(githubUser))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("tableSchemas", List.of()))
                .andExpect(view().name("my-schemas"));
        then(tableSchemaService).should().loadMySchemas(githubUser.id());
    }

    @DisplayName("[POST] 내 스키마 삭제 (정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenDeleting_thenRedirectsToTableSchemaView() throws Exception {
        // Given
        var githubUser = new GithubUser("test-id", "test-name", "test@email.com");
        String schemaName = "test_schema";

        // When & Then
        mvc.perform(
                        post("/table-schema/my-schemas/{schemaName}", schemaName)
                                .with(csrf())
                                .with(oauth2Login().oauth2User(githubUser))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/table-schema/my-schemas"));
    }

    @DisplayName("[GET] 테이블 스키마 파일 다운로드 (정상)")
    @Test
    void givenTableSchema_whenDownloading_thenReturnsFile() throws Exception {
        // Given
        TableSchemaExportRequest request = TableSchemaExportRequest.of(
                "test",
                77,
                ExportFileType.JSON,
                List.of(
                        SchemaFieldRequest.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldRequest.of("name", MockDataType.NAME, 1, 0, "option", "well"),
                        SchemaFieldRequest.of("age", MockDataType.NUMBER, 3, 20, null, null)
                )
        );

        String queryParam = formDataEncoder.encode(request, false);

        // When & Then
        mvc.perform(get("/table-schema/export?" + queryParam))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table_schema.txt"))
                .andExpect(content().json(mapper.writeValueAsString(request))); // TODO: 나중에 데이터 바꿔야 함
    }
    
}
