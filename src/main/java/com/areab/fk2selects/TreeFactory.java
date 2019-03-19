package com.areab.fk2selects;

import com.areab.fk2selects.models.Relation;
import com.areab.fk2selects.tree.Node;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeFactory {

    public static Set<Node> create(Set<Relation> relations) {

        // rootから走査していく
        Separated separated = Separated.create(relations);
        Map<String, Set<Relation>> othersMap = groupedByParentTableName(separated.others);
        Set<Node> rootNodes = separated.roots.stream().map(Node::new).collect(Collectors.toSet());

        for (Node root: rootNodes) {
            addChildrenNodes(root, othersMap, 0);
        }

        return rootNodes;
    }

    private static void addChildrenNodes(Node node, Map<String, Set<Relation>> othersMap, int count) {

        if (count > 50) {
            // avoid a stack overflow
            return;
        }

        if (!othersMap.containsKey(node.relation.tableName) || node.isEdge()) {
            return;
        }

        Set<Relation> children = othersMap.get(node.relation.tableName);
        othersMap.remove(node.relation.tableName);

        int currentCount = count + 1;
        for (Relation relation: children) {
            Node child = new Node(relation);
            node.children.add(child);
            addChildrenNodes(child, othersMap, currentCount);
        }
    }

    private static Map<String, Set<Relation>> groupedByParentTableName(Set<Relation> relations) {
        Map<String, Set<Relation>> dist = new HashMap<>();

        for (Relation relation: relations) {
            String key = relation.getParentTableName();

            if (dist.containsKey(key)) {
                dist.get(key).add(relation);
            } else {
                Set<Relation> set = new LinkedHashSet<>();
                set.add(relation);
                dist.put(key, set);
            }
        }

        return dist;
    }

    static class Separated {
        Set<Relation> roots;
        Set<Relation> others;

        private Separated() {
            this.roots = new LinkedHashSet<>();
            this.others = new LinkedHashSet<>();
        }

        static Separated create(Set<Relation> relations) {
            Separated dist = new Separated();
            for (Relation relation: relations) {
                if (relation.type == Relation.Type.ROOT) {
                    dist.roots.add(relation);
                    continue;
                }

                dist.others.add(relation);
            }
            return dist;
        }

        @Override
        public String toString() {
            return "Separated{" +
                    "roots=" + roots +
                    ", others=" + others +
                    '}';
        }
    }
}
