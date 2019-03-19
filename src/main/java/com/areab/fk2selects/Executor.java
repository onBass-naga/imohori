package com.areab.fk2selects;

import com.areab.fk2selects.models.Relation;
import com.areab.fk2selects.tree.Node;
import com.areab.schema2json.SchemaMetaFactory;
import com.areab.schema2json.Settings;
import com.areab.schema2json.model.Database;

import java.util.Set;

public class Executor {

    public void execute() throws ClassNotFoundException {
        Database databaseMeta = createDatabaseMeta();
        Set<Relation> relations = RelationFactory.convertFrom(databaseMeta);
        Set<Node> trees = TreeFactory.create(relations);
        System.out.println(trees);

        search("select * from customers;", trees);
    }

    public void search(String sql, Set<Node> trees) {

    }

    public Database createDatabaseMeta() throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Settings settings = new Settings();
        settings.setOutputDirectory("./dist");
        settings.setDriverClassName("org.postgresql.Driver");
        settings.setUrl("jdbc:postgresql://localhost:5432/test_db");
        settings.setUsername("root");
        settings.setPassword("password");
        settings.setSchema("ec");

        return SchemaMetaFactory.create(settings);

    }
}
