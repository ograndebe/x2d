import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class FirstTest {

    public static void main (String[] args) throws IOException, InvalidFormatException {
        InputStream inp = new FileInputStream("C:\\rfe\\projetos_git\\xlsToDatabase\\sample\\Sample - Superstore.xls");
        //InputStream inp = new FileInputStream("workbook.xlsx");

        Workbook wb = WorkbookFactory.create(inp);

        final int numberOfSheets = wb.getNumberOfSheets();

        for (int i = 0; i < numberOfSheets; i++) {
            final Sheet sheet = wb.getSheetAt(i);
            System.out.println(sheet.getSheetName());
            final Row firstRow = sheet.getRow(sheet.getFirstRowNum());
            for (int j = firstRow.getFirstCellNum(); j <= firstRow.getLastCellNum(); j++) {
                final Cell cell = firstRow.getCell(j);
                if (cell != null) {
                    final String stringCellValue = cell.getStringCellValue();
                    System.out.printf("\t%s\n", stringCellValue);
                }
            }

        }
    }
}
