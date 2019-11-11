package jakarta.nosql.demo;

import jakarta.nosql.mapping.PreparedStatement;
import jakarta.nosql.mapping.keyvalue.KeyValueTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class KeyValueApp3 {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            KeyValueTemplate template =
                    container.select(KeyValueTemplate.class)
                            .get();

            template.put(diana, Duration.ofSeconds(1L));
            Optional<God> result = template.getSingleResult("get 1", God.class);
            System.out.println("Query by plain text: " + result);
            PreparedStatement prepare = template.prepare("get @id", God.class);
            prepare.bind("id", 1L);
            System.out.println("Query by prepare query");
            prepare.getSingleResult().ifPresent(System.out::println);

        }

        System.exit(0);

    }
}
