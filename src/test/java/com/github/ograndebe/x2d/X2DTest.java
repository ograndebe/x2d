package com.github.ograndebe.x2d;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.assertEquals;

public class X2DTest {

    private Connection conn;

    @Before
    public void convert() throws SQLException, IOException, InvalidFormatException {
        final X2D x2d = new X2D(X2DTest.class.getResourceAsStream("/sample_sheet.xls"));

        conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        x2d.setConnection(conn);
        x2d.convert();
    }


    @Test
    public void testRowNumbers() throws SQLException, IOException, InvalidFormatException {

        int orders = countRows("select * from ORDERS");
        assertEquals(9994, orders);

        int returns = countRows("select * from RETURNS");
        assertEquals(296, returns);

        int peoples = countRows("select * from PEOPLE");
        assertEquals(4, peoples);
    }

    private int countRows(String sql) throws SQLException {
        int cont = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            try (final ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private String getPipedRow(ResultSet rs) throws SQLException {
        final ResultSetMetaData rsmd = rs.getMetaData();
        final int columnCount = rsmd.getColumnCount();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            sb.append(rs.getObject(i));
            if (i != columnCount) sb.append("|");
        }
        return sb.toString();
    }

    private String getFirstPipedRow(String sql) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            try (final ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    final String pipedRow = getPipedRow(rs);
                    return pipedRow;
                }
            }
        }
        return "";
    }

    @Test
    public void testFirstRows() throws SQLException {
        String row = getFirstPipedRow("select * from PEOPLE");
        assertEquals("Anna Andreadi|West",row);

        row = getFirstPipedRow("select * from RETURNS");
        assertEquals("Yes|CA-2017-153822",row);

    }

}
