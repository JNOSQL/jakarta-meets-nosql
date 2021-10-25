/*
 * Copyright (c) 2021 Otávio Santana and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Otavio Santana (@otaviojava)
 * Carlos Santos (@carlosepdsJava)
 */

package jakarta.nosql.demo;


import org.eclipse.jnosql.mapping.graph.EntityTree;
import org.eclipse.jnosql.mapping.graph.GraphTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class GraphApp3 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            GraphTemplate template =
                    container.select(GraphTemplate.class)
                            .get();

            God diana = template.getTraversalVertex()
                    .hasLabel(God.class)
                    .has("name", "Diana")
                    .<God>next()
                    .orElseGet(() ->
                            template.insert(God.of("Diana", "Hunt")));

            God apollo = template.getTraversalVertex()
                    .hasLabel(God.class)
                    .has("name", "Apollo")
                    .<God>next()
                    .orElseGet(() ->
                            template.insert(God.of("Apollo", "Sun")));

            God zeus = template.getTraversalVertex()
                    .hasLabel(God.class)
                    .has("name", "Zeus")
                    .<God>next()
                    .orElseGet(() ->
                            template.insert(God.of("Zeus", "Thunder")));

            template.edge(diana, "brother", apollo);
            template.edge(apollo, "brother", diana);
            template.edge(zeus, "father", apollo);
            template.edge(zeus, "father", diana);

            EntityTree tree = template.getTraversalVertex().out("father").tree();
            System.out.println("The leaf");
            tree.getLeaf().forEach(System.out::println);
            System.out.println("The root");
            tree.getRoots().forEach(System.out::println);

            template.delete(diana.getId());
            template.delete(zeus.getId());
            template.delete(apollo.getId());
        }
        System.exit(0);
    }
}