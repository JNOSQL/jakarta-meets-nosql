package jakarta.nosql.demo;

import org.eclipse.jnosql.artemis.DatabaseQualifier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;

public class KeyValueApp4 {

    public static void main(String[] args) throws InterruptedException {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            GodRepository repository =
                    container.select(GodRepository.class
                    , DatabaseQualifier.ofKeyValue())
                            .get();

            repository.save(diana);
            Optional<God> result = repository.findById(1L);
            System.out.println("Find by id: " + result);

        }

        System.exit(0);

    }
}
