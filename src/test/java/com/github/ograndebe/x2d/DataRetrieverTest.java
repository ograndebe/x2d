package com.github.ograndebe.x2d;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataRetrieverTest {

    @Test
    public void retrieve() throws IOException, InvalidFormatException {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/sample_sheet.xls");
        Workbook wb = WorkbookFactory.create(resourceAsStream);
        DataRetriever dr = new DataRetriever(wb);

        List<List<Object>> data = dr.retrieve("PEOPLE");

        assertNotNull(data);
        assertEquals(4,data.size());

        for (List<Object> row : data) {
            assertEquals(2,row.size());
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() throws IOException, InvalidFormatException {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/sample_sheet.xls");
        Workbook wb = WorkbookFactory.create(resourceAsStream);
        DataRetriever dr = new DataRetriever(wb);

        dr.retrieve("NAME_THAT_NOT_EXISTS");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNull() throws IOException, InvalidFormatException {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/sample_sheet.xls");
        Workbook wb = WorkbookFactory.create(resourceAsStream);
        DataRetriever dr = new DataRetriever(wb);

        dr.retrieve(null);
    }

}
