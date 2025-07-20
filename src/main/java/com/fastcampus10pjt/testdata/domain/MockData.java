package com.fastcampus10pjt.testdata.domain;

import com.fastcampus10pjt.testdata.domain.constant.MockDataType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MockData {

    private MockDataType mockDataType;
    private String mockDataValue;

}
