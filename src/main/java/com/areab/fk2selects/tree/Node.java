package com.areab.fk2selects.tree;

import com.areab.fk2selects.models.Relation;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    public Relation relation;
    public Set<Node> children = new LinkedHashSet<>();

    public Node(Relation relation) {
        this.relation = relation;
    }

    public boolean isEdge() {
        return this.relation.type == Relation.Type.EDGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return relation.equals(node.relation) &&
                children.equals(node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, children);
    }

    @Override
    public String toString() {
        return "Node{" +
                "relation=" + relation +
                ", children=" + children +
                '}';
    }
}
