/*
 * Copyright (c) 2019 Otávio Santana and others
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

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.nosql.mapping.PreparedStatement;
import jakarta.nosql.mapping.column.ColumnTemplate;
import java.util.Optional;

public class ColumnApp3 {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            ColumnTemplate template =
                    container.select(ColumnTemplate.class)
                            .get();
            template.insert(diana);

            Optional<God> god = template.singleResult("select * from God where _id = 1");
            System.out.println("Plain query text : " + god);

            PreparedStatement prepare = template.prepare("select * from God where _id = @id");
            prepare.bind("id", 1L);

            System.out.println("Query by prepare query" + prepare.getSingleResult());
        }
        System.exit(0);
    }
}
