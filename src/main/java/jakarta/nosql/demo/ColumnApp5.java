package jakarta.nosql.demo;

import org.eclipse.jnosql.artemis.DatabaseQualifier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class ColumnApp5 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            GodRepository repository =
                    container.select(GodRepository.class, DatabaseQualifier.ofColumn())
                            .get();

            repository.save(diana);

            System.out.println("Query by name : " + repository.findByName("Diana"));

        }
        System.exit(0);
    }
}
