package com.fastcampus10pjt.testdata.domain.dto.request;

import com.fastcampus10pjt.testdata.domain.constant.MockDataType;
import com.fastcampus10pjt.testdata.domain.dto.SchemaFieldDto;

public record SchemaFieldRequest(
        String fieldName,
        MockDataType mockDataType,
        Integer fieldOrder,
        Integer blankPercent,
        String typeOptionJson,
        String forceValue
) {

    public static SchemaFieldRequest of(String fieldName, MockDataType mockDataType, Integer fieldOrder, Integer blankPercent, String typeOptionJson, String forceValue) {
        return new SchemaFieldRequest(fieldName, mockDataType, fieldOrder, blankPercent, typeOptionJson, forceValue);
    }

    public SchemaFieldDto toDto(){
        return SchemaFieldDto.of(
                fieldName(),
                mockDataType(),
                fieldOrder(),
                blankPercent(),
                typeOptionJson(),
                forceValue()
        );
    }

}
