package com.areab.fk2selects.models;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ImportedKey {
    public String parentTableName;
    public String parentColumnName;
    public String childTableName;
    public String childColumnName;

    public ImportedKey(String parentTableName,
                       String parentColumnName,
                       String childTableName,
                       String childColumnName) {
        this.parentTableName = parentTableName;
        this.parentColumnName = parentColumnName;
        this.childTableName = childTableName;
        this.childColumnName = childColumnName;
    }

    public static ImportedKey create(String src) {
        List<String> parts = Arrays.asList(src.split("\\.|:"));
        return new ImportedKey(parts.get(0), parts.get(1), parts.get(2), parts.get(3));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportedKey that = (ImportedKey) o;
        return parentTableName.equals(that.parentTableName) &&
                parentColumnName.equals(that.parentColumnName) &&
                childTableName.equals(that.childTableName) &&
                childColumnName.equals(that.childColumnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentTableName, parentColumnName, childTableName, childColumnName);
    }

    @Override
    public String toString() {
        return "ImportedKey{" +
                "parentTableName='" + parentTableName + '\'' +
                ", parentColumnName=" + parentColumnName +
                ", childTableName='" + childTableName + '\'' +
                ", childColumnName=" + childColumnName +
                '}';
    }
}
