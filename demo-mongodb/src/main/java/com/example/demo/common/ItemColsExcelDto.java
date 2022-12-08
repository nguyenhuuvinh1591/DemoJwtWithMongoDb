package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemColsExcelDto {
    private String colName;
    private int colIndex;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

}
