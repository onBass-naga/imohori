package com.areab.fk2selects.models;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Relation {
    /** 同じ親テーブルに対するFK */
    public Set<ImportedKey> importedKeys;
    public String tableName;
    public Type type;

    public Relation(Set<ImportedKey> importedKeys, String tableName, Boolean isParent) {
        this.importedKeys = importedKeys;
        this.tableName = tableName;
        this.type = isParent ? Type.MIDDLE : Type.EDGE;
    }

    public Relation(String tableName) {
        this.importedKeys = Collections.emptySet();
        this.tableName = tableName;
        this.type = Type.ROOT;
    }

    public String getParentTableName() {
        return importedKeys.iterator().next().parentTableName;
    }

    public enum Type {
        ROOT, MIDDLE, EDGE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return importedKeys.equals(relation.importedKeys) &&
                tableName.equals(relation.tableName) &&
                type == relation.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(importedKeys, tableName, type);
    }

    @Override
    public String toString() {
        return "Relation{" +
                "importedKeys=" + importedKeys +
                ", tableName='" + tableName + '\'' +
                ", type=" + type +
                '}';
    }
}
