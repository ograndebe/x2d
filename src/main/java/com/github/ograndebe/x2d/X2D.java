package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class X2D {

    private InputStream inputStream;
    private Connection connection;
    private boolean closeConnectionOnFinish;

    public X2D(String filename) throws FileNotFoundException {
        inputStream = new FileInputStream(filename);
    }

    public X2D(File file) throws FileNotFoundException {
        inputStream = new FileInputStream(file);
    }

    public X2D(InputStream is) {
        inputStream = is;
    }

    /**
     * Defines a connection using passed parameters
     * @param url
     * @param user
     * @param password
     * @throws SQLException
     */
    public void setConnection(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        this.closeConnectionOnFinish = true;
    }

    /**
     * Defines a conneciton with passed connection
     * @param conn
     */
    public void setConnection(Connection conn) {
        this.connection = conn;
        this.closeConnectionOnFinish = false;
    }

    /**
     * Defines a connection using environment variables
     * X2D_URL
     * X2D_USER
     * X2D_PASS
     * @throws SQLException
     */
    public void setConnectionFromEnvironment() throws SQLException {
        final String x2DUrl = System.getenv("X2D_URL");
        final String x2DUser = System.getenv("X2D_USER");
        final String x2DPass = System.getenv("X2D_PASS");
        this.setConnection(x2DUrl, x2DUser, x2DPass);
    }


    public void convert() throws SQLException, IOException, InvalidFormatException {
        final Workbook wb = WorkbookFactory.create(this.inputStream);

        final List<Table> tables = new MetadataRetriever(wb).retrieveMetadata();

        final DDLGenerator ddlGen = new DDLGenerator(tables);
        final String ddl = ddlGen.generateDDL();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(ddl)) {
            preparedStatement.execute();
        }

        for (Table table : tables) {
            final String insertCommand = new DMLInsertGenerator(table).generate();
            try (final PreparedStatement insertStmt = connection.prepareStatement(insertCommand)) {
                final List<List<Object>> data = new DataRetriever(wb).retrieve(table.getName());
                for (List<Object> row: data){
                    int i = 1;
                    for (Object col: row) {
                        insertStmt.setObject(i++, col);
                    }
                    insertStmt.executeUpdate();
                }
            }
        }
        if (this.closeConnectionOnFinish) {
            this.connection.close();
        }
    }

}
