package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class MetadataRetriever {

    private final Workbook workbook;

    public MetadataRetriever(Workbook workbook) {
        this.workbook = workbook;
    }

    public List<Table> retrieveMetadata() {
        final int numberOfSheets = workbook.getNumberOfSheets();

        ArrayList<Table> tables = new ArrayList<>(numberOfSheets);
        for (int i = 0; i < numberOfSheets; i++) {
            final Sheet sheet = workbook.getSheetAt(i);
            Table table = new Table(tableName(sheet));

            final Row firstRow = sheet.getRow(sheet.getFirstRowNum());

            for (int j = firstRow.getFirstCellNum(); j <= firstRow.getLastCellNum(); j++) {
                final Cell cell = firstRow.getCell(j);
                if (cell != null) {
                    Column column = new Column();
                    column.setName(columnName(cell));
                    column.setSheetColumnIndex(j);
                    detectLengthAndType(column, sheet, j);
                    table.getColumns().add(column);
                }
            }
            tables.add(table);
        }
        return tables;
    }

    private void detectLengthAndType(Column column, Sheet sheet, int columnIndex) {
        CellType type = null;
        int length = 0;
        for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
            final Row row = sheet.getRow(i);
            final Cell cell = row.getCell(columnIndex);

            if (type == null) {
                type = cell.getCellTypeEnum();
            } else if (!cell.getCellTypeEnum().equals(type) ) {
                try {
                    final String scv = cell.getStringCellValue();
                    type = CellType.STRING;
                    if (scv != null) length = Math.max(scv.length(), length);
                } catch (Exception e) {
                }
            } else {
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        type = CellType.STRING;
                        final String stringCellValue = cell.getStringCellValue();
                        if (stringCellValue != null) length = Math.max(stringCellValue.length(), length);
                        break;
                    case NUMERIC:
                        type = CellType.NUMERIC;
                        length = Math.max(length,String.valueOf(cell.getNumericCellValue()).length());
                        break;
                    case BOOLEAN:
                        type = CellType.BOOLEAN;
                        length = Math.max(length,5);
                        break;
                    case BLANK:
                        type = CellType.BLANK;
                        length = Math.max(length,0);
                        break;
                    case ERROR:
                        type = CellType.ERROR;
                        length = Math.max(length,5);
                        break;
                    case FORMULA:
                        try {
                            final String scv = cell.getStringCellValue();
                            type = CellType.STRING;
                            if (scv != null) length = Math.max(scv.length(), length);
                        } catch (Exception e) {
                            try {
                                length = Math.max(length,String.valueOf(cell.getNumericCellValue()).length());
                                type = CellType.NUMERIC;
                            } catch (Exception e2) {
                                type = CellType.ERROR;
                                length = Math.max(length,5);
                            }
                        }
                        break;
                    case _NONE:
                        type = CellType.BLANK;
                        length = Math.max(length,0);
                        break;
                }
            }

        }
        column.setLength(length);
        if (CellType.STRING.equals(type)){
            column.setSqlType("VARCHAR");
        } else if (CellType.NUMERIC.equals(type)) {
            column.setSqlType("NUMERIC");
        }
    }


    private String tableName(final Sheet sheet) {
        if (sheet == null) throw new IllegalArgumentException("Sheet must be not null");
        return sheet.getSheetName().replaceAll("[^A-Za-z0-9]","_").toUpperCase();
    }


    private String columnName(final Cell cell) {
        if (cell == null) throw new IllegalArgumentException("Cell must be not null");
        return cell.getStringCellValue().replaceAll("[^A-Za-z0-9]","_").toUpperCase();
    }



}
