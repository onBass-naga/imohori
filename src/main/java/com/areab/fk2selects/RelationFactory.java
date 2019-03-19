package com.areab.fk2selects;

import com.areab.fk2selects.models.ImportedKey;
import com.areab.fk2selects.models.Relation;
import com.areab.schema2json.model.Database;
import com.areab.schema2json.model.Table;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelationFactory {

    public static Set<Relation> convertFrom(Database databaseMeta) {
        List<Table> tables = databaseMeta.tables;

        // 関連を持たないテーブルは処理対象外とする。対象は1.外部キーを持つ 2.他のテーブルから参照されている
        Set<String> relatedTables = tables.stream()
                .flatMap(RelationFactory::fkTableName)
                .collect(Collectors.toSet());

        Set<Relation> relations = new LinkedHashSet<>();
        for (Table table: tables) {
            if (table.foreignKeys.isEmpty() && !relatedTables.contains(table.name)) {
                continue;
            }

            if (table.foreignKeys.isEmpty()) {
                relations.add(new Relation(table.name));
            }

            Map<String, List<String>> fkGroupedByTable = groupedByTableName(table);
            boolean isParent = relatedTables.contains(table.name);
            for (List<String> keys : fkGroupedByTable.values()) {
                relations.add(new Relation(
                        keys.stream().map(ImportedKey::create).collect(Collectors.toSet()),
                        table.name, isParent));
            }
        }

        return relations;
    }

    private static <T extends Table> Stream<String> fkTableName(T table) {
        return table.foreignKeys.stream().map(key -> key.substring(0, key.indexOf(".")));
    }

    private static Map<String, List<String>> groupedByTableName(Table table) {
        return table
                .foreignKeys
                .stream()
                .collect(Collectors.groupingBy(key -> key.substring(0, key.indexOf("."))));
    }

}
