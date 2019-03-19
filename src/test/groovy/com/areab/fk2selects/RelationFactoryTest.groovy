package com.areab.fk2selects

import com.areab.fk2selects.models.ImportedKey
import com.areab.fk2selects.models.Relation
import com.areab.schema2json.model.Column
import com.areab.schema2json.model.Database
import com.areab.schema2json.model.Table
import groovy.transform.CompileStatic
import org.junit.Test

@CompileStatic
class RelationFactoryTest {

    @Test
    void "リレーションを持たないテーブルは変換せず除外する"() throws Exception {
        Database databaseMeta = Helper.initMeta("customers", [], [
                new Column("id", "BIGINT", false),
                new Column("name", "VARCHAR", false),
                new Column("created_at", "TIMESTAMP", false)])

        Set<Relation> expected = Collections.emptySet()

        Set<Relation> actual = RelationFactory.convertFrom(databaseMeta)
        assert(actual == expected)
    }

    @Test
    void "リレーションを持つテーブルを変換する"() throws Exception {
        Database databaseMeta = Helper.initMeta("customers", [], [
                new Column("id", "BIGINT", false),
                new Column("name", "VARCHAR", false),
                new Column("created_at", "TIMESTAMP", false)])
        Helper.addTable(databaseMeta, "customer_addresses", ["customers.id:customer_addresses.customer_id"], [
                new Column("id", "BIGINT", false),
                new Column("customer_id", "BIGINT", false),
                new Column("zip", "VARCHAR", false),
                new Column("address", "VARCHAR", false),
                new Column("created_at", "TIMESTAMP", false)])

        Set<Relation> expected = [
                new Relation("customers"),
                new Relation([
                        new ImportedKey("customers","id","customer_addresses","customer_id")].toSet(),
                        "customer_addresses", false
                )
        ].toSet()

        Set<Relation> actual = RelationFactory.convertFrom(databaseMeta)
        assert(actual == expected)
    }


    static class Helper {
        static Database initMeta(String tableName, List<String> foreignKeys, List<Column> columns) {
            Database databaseMeta = new Database()
            databaseMeta.name = "test_db"
            databaseMeta.schema = "public"
            Table table = new Table()
            table.name = tableName
            table.columns = columns
            table.primaryKeys = ["id"]
            table.foreignKeys = foreignKeys
            databaseMeta.tables = [table]

            return databaseMeta
        }

        static void addTable(Database databaseMeta, String tableName, List<String> foreignKeys, List<Column> columns) {
            Table table = new Table()
            table.name = tableName
            table.columns = columns
            table.primaryKeys = ["id"]
            table.foreignKeys = foreignKeys
            databaseMeta.tables.add(table)
        }
    }
}