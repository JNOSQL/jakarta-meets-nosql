package jakarta.nosql.demo;

import jakarta.nosql.mapping.PreparedStatement;
import jakarta.nosql.mapping.document.DocumentTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;

public class DocumentApp2 {

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer
                .newInstance().initialize()) {

            God diana = new God(1L, "Diana", "Hunt");

            DocumentTemplate template =  container.select(DocumentTemplate.class)
                            .get();

            template.insert(diana);

            Optional<God> god = template.singleResult("select * from God where _id = 1");
            System.out.println("Plain query text : " + god);

            PreparedStatement prepare = template.prepare("select * from God where _id = @id");
            prepare.bind("id", 1L);

            System.out.println("Query by prepare query" + prepare.getSingleResult());


            template.query("delete from God where _id = 1");

            System.out.println("query : " + template.find(God.class, 1L));
        }

        System.exit(0);
    }
}
