package com.github.ograndebe.x2d;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 */
public class X2DCli {

    public static void main(String[] args) throws SQLException, IOException, InvalidFormatException {
        try {
            X2D x2d = new X2D(System.in);
            x2d.setConnectionFromEnvironment();
            x2d.convert();
        } catch (Exception e) {
            System.err.println("There are some problem with conversion");
            e.printStackTrace();
            System.out.println("Usage (configure ENV variables X2D_URL/X2D_USER/X2D_PASS) then: java -jar x2d.jar < somefile.xls");
        }
    }
}
