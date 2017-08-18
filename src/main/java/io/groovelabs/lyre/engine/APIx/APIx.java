package io.groovelabs.lyre.engine.APIx;

import io.groovelabs.lyre.domain.Bundle;
import io.groovelabs.lyre.domain.Endpoint;
import io.groovelabs.lyre.engine.interpreter.Interpreter;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@Immediate
@Component
public class APIx extends ResourceConfig {

    static Container container;

    Interpreter interpreter;

    public APIx() {
        config(this);
        interpreter = new Interpreter(this);
    }


    public void boot(Bundle bundle) {
        if (APIx.container != null) {
            final ResourceConfig resourceConfig = this.createResources(bundle, null);
            APIx.container.reload(resourceConfig);

        } else {
            this.createResources(bundle, this);
        }
    }

    private void config(final ResourceConfig resourceConfig) {
        resourceConfig.registerInstances(new ContainerLifecycleListener() {
            @Override
            public void onStartup(final Container container) {
                System.out.println("Application has been started!");
                APIx.container = container;
            }

            @Override
            public void onReload(final Container container) {
                System.out.println("Application has been reloaded!");
            }

            @Override
            public void onShutdown(final Container container) {
                // ignore
            }
        });
    }

    private ResourceConfig createResources(Bundle bundle, ResourceConfig resourceConfig) {

        if (resourceConfig == null)
            resourceConfig = new ResourceConfig();

        for (Endpoint endpoint : bundle.getList()) {

            Resource.Builder resourceBuilder =
                Resource.builder().path(endpoint.getPath());

            resourceBuilder.addMethod(endpoint.getMethod().name())
                .produces("text/plain")
                .handledBy(new Inflector<ContainerRequestContext, Object>() {
                    @Override
                    public Response apply(ContainerRequestContext containerRequestContext) {
                        return Response.status(endpoint.getResponse().getStatus().value()).entity(endpoint.getData()).build();
                    }
                });

            resourceConfig.registerResources(resourceBuilder.build());
        }

        return resourceConfig;
    }
}
