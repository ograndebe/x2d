package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class DMLInsertGeneratorTest {


    @Test
    public void generate () {

        Table table = new Table("MY_TABLE2");
        table.setColumns(Arrays.asList(
                new Column("COLUMN_1","VARCHAR",50,0),
                new Column("COLUMN_2","VARCHAR",50,1),
                new Column("COLUMN_3","NUMERIC",0,2),
                new Column("COLUMN_4","NUMERIC",0,3)
        ));


        DMLInsertGenerator generator = new DMLInsertGenerator(table);

        final String insert = generator.generate();

        assertNotNull(insert);
        assertNotEquals("", insert);
        assertEquals("insert into MY_TABLE2 (COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4)\n" +
                "values (?, ?, ?, ?)", insert);


    }
}
