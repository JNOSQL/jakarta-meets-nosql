package jakarta.nosql.demo;

import jakarta.nosql.mapping.PreparedStatement;
import jakarta.nosql.mapping.column.ColumnTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
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
