package com.github.ograndebe.x2d;

import com.github.ograndebe.x2d.metadata.Column;
import com.github.ograndebe.x2d.metadata.Table;

public class DMLInsertGenerator {

    private final Table table;

    public DMLInsertGenerator(Table table) {

        this.table = table;
    }

    public String generate() {
        StringBuilder sql = new StringBuilder("insert into ").append(table.getName()).append(" (");

        StringBuilder sp = new StringBuilder();
        boolean first = true;
        for (Column column: table.getColumns()) {
            if (!first) {
                sql.append(", ");
                sp.append(", ");
            }
            sql.append(column.getName());
            sp.append("?");
            first = false;
        }
        sql.append(")\nvalues (").append(sp).append(")");
        return sql.toString();
    }

}
