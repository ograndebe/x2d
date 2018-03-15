package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class MetadataRetrieverTest {

    @Test
    public void retrieveMetadataTest() throws IOException, InvalidFormatException {

        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/sample_sheet.xls");

        Workbook wb = WorkbookFactory.create(resourceAsStream);

        MetadataRetriever mr = new MetadataRetriever(wb);

        final List<Table> tables = mr.retrieveMetadata();

        assertEquals(tables.size(), 3);

        assertThat(tables, CoreMatchers.hasItems(new Table("ORDERS"), new Table("RETURNS"), new Table("PEOPLE")));

        for (Table table :
                tables) {
            assertNotNull(table);
            assertNotNull(table.getColumns());

            if (table.getName().equals("ORDERS")) {
                assertEquals(21,table.getColumns().size());
            } else if (table.getName().equals("RETURNS")) {
                assertEquals(2,table.getColumns().size());
            } else if (table.getName().equals("PEOPLE")) {
                assertEquals(2,table.getColumns().size());
            }
        }



    }
}
