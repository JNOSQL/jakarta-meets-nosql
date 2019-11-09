package jakarta.nosql.demo.producer;


import org.apache.tinkerpop.gremlin.structure.Graph;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
class GraphProducer {

    @Inject
    @ConfigProperty(name = "graph")
    private Graph graph;

    @Produces
    @ApplicationScoped
    public Graph getGraph() {
        return graph;
    }

    public void destroy(@Disposes Graph graph) throws Exception {
        graph.close();
    }
}
