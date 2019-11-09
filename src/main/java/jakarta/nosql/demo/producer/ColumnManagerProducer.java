package jakarta.nosql.demo.producer;

import jakarta.nosql.column.ColumnFamilyManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
class ColumnManagerProducer {


    @Inject
    @ConfigProperty(defaultValue = "column")
    private ColumnFamilyManager manager;

    @Produces
    public ColumnFamilyManager getManagerCassandra() {
        return manager;
    }

    public void destroy(@Disposes ColumnFamilyManager manager) {
        manager.close();
    }

}
