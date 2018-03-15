package com.github.ograndebe.x2d.metadata;

public class Column {
    private String name;
    private String sqlType;
    private int length;
    private int sheetColumnIndex;

    public Column() {

    }

    public Column(String name, String sqlType, int length, int sheetColumnIndex) {
        this.name = name;
        this.sqlType = sqlType;
        this.length = length;
        this.sheetColumnIndex = sheetColumnIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSheetColumnIndex() {
        return sheetColumnIndex;
    }

    public void setSheetColumnIndex(int sheetColumnIndex) {
        this.sheetColumnIndex = sheetColumnIndex;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }
}
