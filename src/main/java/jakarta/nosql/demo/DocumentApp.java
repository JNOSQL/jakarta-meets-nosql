package jakarta.nosql.demo;

import org.eclipse.jnosql.artemis.DatabaseQualifier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class DocumentApp {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            GodRepository repository =
                    container.select(GodRepository.class,
                            DatabaseQualifier.ofDocument())
                            .get();

            repository.save(diana);

            System.out.println("Query " + repository.findByName("Diana"));

            System.out.println("Query 2 " + repository.query("Diana"));
        }

        System.exit(0);
    }
}
