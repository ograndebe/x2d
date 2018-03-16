package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;

import java.util.LinkedList;
import java.util.List;

public class DataRetriever {

    private final Workbook workbook;

    public DataRetriever(Workbook workbook) {
        this.workbook = workbook;
    }


    public List<List<Object>> retrieve(String tableName) {
        if (tableName == null) throw new IllegalArgumentException("Table name must not be null");

        final int numberOfSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            final Sheet sheet = workbook.getSheetAt(i);
            if (tableName.equals(tableName(sheet))) {
                LinkedList<List<Object>> rowsToReturn = new LinkedList<>();
                for (int j = sheet.getFirstRowNum()+1; j <= sheet.getLastRowNum(); j++) {
                    final Row row = sheet.getRow(j);
                    LinkedList<Object> list = new LinkedList<>();

                    for (int z = row.getFirstCellNum(); z <= row.getLastCellNum(); z++) {
                        final Cell cell = row.getCell(z);
                        list.add(getValue(cell));
                    }

                    rowsToReturn.add(list);
                }
                return rowsToReturn;
            }
        }
        throw new IllegalArgumentException("There is no table named: "+tableName);
    }

    private Object getValue(Cell cell) {
        if (cell == null) return "";
        Object value;
        final CellType cellType = cell.getCellTypeEnum();
        if (cellType == null) return "";
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case BOOLEAN:
                final boolean booleanCellValue = cell.getBooleanCellValue();
                value = booleanCellValue?"true":"false";
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = "error";
                break;
            case FORMULA:
                try {
                    value = cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        value = cell.getNumericCellValue();
                    } catch (Exception e2) {
                        value = "error";
                    }
                }
                break;
            case _NONE:
                value = "error";
                break;
            default:
                value = "";
                break;

        }

        return value;
    }

    private String tableName(final Sheet sheet) {
        if (sheet == null) throw new IllegalArgumentException("Sheet must be not null");
        return sheet.getSheetName().replace(" ","_").toUpperCase();
    }

}
