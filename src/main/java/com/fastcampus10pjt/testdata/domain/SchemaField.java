package com.fastcampus10pjt.testdata.domain;

import com.fastcampus10pjt.testdata.domain.constant.MockDataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchemaField {

    private String fieldName;
    private MockDataType mockDataType;
    private Integer fieldOrder;
    private Integer blackPercent;
    private String typeOptionJson;
    private String forceValue;

}
