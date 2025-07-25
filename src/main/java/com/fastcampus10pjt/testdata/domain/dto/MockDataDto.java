package com.fastcampus10pjt.testdata.domain.dto;

import com.fastcampus10pjt.testdata.domain.MockData;
import com.fastcampus10pjt.testdata.domain.constant.MockDataType;

public record MockDataDto(
        Long id,
        MockDataType mockDataType,
        String mockDataValue
) {
    public static MockDataDto fromEntity(MockData entity){
        return new MockDataDto(
                entity.getId(),
                entity.getMockDataType(),
                entity.getMockDataValue());
    }
}
