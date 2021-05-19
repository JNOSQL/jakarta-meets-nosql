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
import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class KeyValueApp2 {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            KeyValueTemplate template =
                    container.select(KeyValueTemplate.class)
                            .get();

            template.put(diana, Duration.ofSeconds(1L));

            final Optional<God> god = template.get(1L, God.class);
            System.out.println("query : " + god);
            TimeUnit.SECONDS.sleep(2L);
            System.out.println("query again: " +
                    template.get(1L, God.class));
        }
        System.exit(0);
    }
}
