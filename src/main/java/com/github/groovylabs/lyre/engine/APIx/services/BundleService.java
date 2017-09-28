package com.github.groovylabs.lyre.engine.APIx.services;

import com.github.groovylabs.lyre.domain.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/bundle")
public class BundleService {

    @Autowired
    private Bundle bundle;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response message() {
        return Response.ok().entity(bundle).build();
    }

}