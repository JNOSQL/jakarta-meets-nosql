package jakarta.nosql.demo;

import org.eclipse.jnosql.artemis.graph.GraphTemplate;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Optional;

public class GraphApp2 {

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
                            template.insert(new God(null, "Diana", "Hunt")));

            God apollo = template.getTraversalVertex()
                    .hasLabel(God.class)
                    .has("name", "Apollo")
                    .<God>next()
                    .orElseGet(() ->
                            template.insert(new God(null, "Apollo", "Sun")));

            God zeus = template.getTraversalVertex()
                    .hasLabel(God.class)
                    .has("name", "Zeus")
                    .<God>next()
                    .orElseGet(() ->
                            template.insert(new God(null, "Zeus", "Thunder")));


            template.edge(diana, "brother", apollo);
            template.edge(apollo, "brother", diana);

            template.edge(zeus, "father", apollo);
            template.edge(zeus, "father", diana);


            template.delete(diana.getId());
            template.delete(zeus.getId());
            template.delete(apollo.getId());


        }

        System.exit(0);
    }
}