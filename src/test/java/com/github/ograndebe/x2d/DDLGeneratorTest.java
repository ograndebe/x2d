package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class DDLGeneratorTest {

    @Test
    public void generate() {

        Table table1 = new Table("MY_TABLE1");
        table1.setColumns(Arrays.asList(
                new Column("COLUMN1","VARCHAR",25,0),
                new Column("COLUMN2","VARCHAR",10,1),
                new Column("COLUMN3","NUMERIC",0,2)
        ));

        Table table2 = new Table("MY_TABLE2");
        table2.setColumns(Arrays.asList(
                new Column("COLUMN_1","VARCHAR",50,0),
                new Column("COLUMN_2","VARCHAR",50,1),
                new Column("COLUMN_3","NUMERIC",0,2),
                new Column("COLUMN_4","NUMERIC",0,3)
        ));

        final DDLGenerator ddlGenerator = new DDLGenerator(Arrays.asList(table1, table2));
        final String ddl = ddlGenerator.generateDDL();

        assertNotNull(ddl);
        assertNotEquals("", ddl);
        assertEquals("create table MY_TABLE1 ( \n" +
                "    COLUMN1    VARCHAR(25),\n" +
                "    COLUMN2    VARCHAR(10),\n" +
                "    COLUMN3    NUMERIC\n" +
                ");\n" +
                "create table MY_TABLE2 ( \n" +
                "    COLUMN_1    VARCHAR(50),\n" +
                "    COLUMN_2    VARCHAR(50),\n" +
                "    COLUMN_3    NUMERIC,\n" +
                "    COLUMN_4    NUMERIC\n" +
                ");\n", ddl);


    }
}
