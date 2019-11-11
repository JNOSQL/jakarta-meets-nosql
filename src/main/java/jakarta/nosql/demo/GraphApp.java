package jakarta.nosql.demo;

import org.eclipse.jnosql.artemis.graph.GraphTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;

public class GraphApp {

    public static void main(String[] args)  {

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
                            template.insert(new God(null, "Diana", "Hunt")));


            System.out.println("query : " + diana);

            template.delete(diana.getId());

            Optional<God> god = template.getTraversalVertex(diana.getId()).next();

            System.out.println("Query: " + god);

        }

        System.exit(0);
    }
}