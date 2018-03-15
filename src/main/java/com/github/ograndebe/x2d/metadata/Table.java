package com.github.ograndebe.x2d.metadata;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Table {
    private String name;
    private List<Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(name, table.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
