package jakarta.nosql.demo.producer;

import jakarta.nosql.keyvalue.BucketManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
class BucketManagerProducer {

    @Inject
    @ConfigProperty(name = "keyvalue")
    private BucketManager manager;

    @Produces
    public BucketManager getManager() {
        return manager;
    }

    public void destroy(@Disposes BucketManager manager) {
        manager.close();
    }

}
