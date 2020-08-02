package jakarta.nosql.demo.producer;

import com.steelbridgelabs.oss.neo4j.structure.Neo4JElementIdProvider;
import com.steelbridgelabs.oss.neo4j.structure.Neo4JGraph;
import com.steelbridgelabs.oss.neo4j.structure.providers.Neo4JNativeElementIdProvider;
import jakarta.nosql.Settings;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.eclipse.jnosql.artemis.graph.GraphConfiguration;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;

import java.util.Objects;

import static jakarta.nosql.Configurations.HOST;
import static jakarta.nosql.Configurations.PASSWORD;
import static jakarta.nosql.Configurations.USER;
import static org.neo4j.driver.GraphDatabase.driver;

public class Neo4JRemote implements GraphConfiguration {

    @Override
    public Graph apply(Settings settings) {
        Objects.requireNonNull(settings, "settings is required");

        String url = settings.getOrDefault(HOST.get(), "bolt://localhost:7687").toString();
        String user = settings.getOrDefault(USER.get(), "neo4j").toString();
        String password = settings.getOrDefault(PASSWORD.get(), "neo4j").toString();

        AuthToken basic = AuthTokens.basic(user, password);
        Driver driver = driver(url, basic);
        Neo4JElementIdProvider<Long> vertexIdProvider = new Neo4JNativeElementIdProvider();
        Neo4JElementIdProvider<Long> edgeIdProvider = new Neo4JNativeElementIdProvider();
        return new Neo4JGraph(driver, vertexIdProvider, edgeIdProvider);
    }
}
