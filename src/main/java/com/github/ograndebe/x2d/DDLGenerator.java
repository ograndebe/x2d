package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;

import java.util.List;

public class DDLGenerator {

    private final List<Table> tables;

    public DDLGenerator(List<Table> tables) {

        this.tables = tables;
    }

    public String generateDDL() {
        StringBuilder sql = new StringBuilder();


        for (Table table: tables) {
            sql.append("create table ").append(table.getName()).append(" ( \n");
            boolean first = true;
            for (Column column: table.getColumns()) {
                if (!first) sql.append(",\n");
                sql.append("    ").append(column.getName()).append("    ").append(column.getSqlType());
                if ("VARCHAR".equals(column.getSqlType())) sql.append("(").append(column.getLength()).append(")");
                first = false;
            }
            sql.append("\n);\n");
        }

        return sql.toString();
    }
}
