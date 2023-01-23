package com.itemis.codechallenge.invoice;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
class SalesTaxAppLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(SalesTaxAppLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info(" SALES TAX SERVER PROCESS ");
        LOGGER.infof("The application SALES TAX LIB is starting with profile `%s`", ProfileManager.getActiveProfile());
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("SALES TAX is stopping...");
    }
}