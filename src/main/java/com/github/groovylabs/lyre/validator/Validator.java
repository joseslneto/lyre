package com.github.groovylabs.lyre.validator;

import com.github.groovylabs.lyre.domain.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    /**
     * Responsible to check the integrity of endpoint before insert it into list and
     * verify if it's already been inserted.
     */

    public boolean integrity(String fileName, Endpoint endpoint, List<Endpoint> savedEndpoints, boolean updatable) {

        if (StringUtils.isEmpty(endpoint.getMethod()) || StringUtils.isEmpty(endpoint.getPath())
            || StringUtils.isEmpty(endpoint.getResponse().getStatus())) {

            LOGGER.error("Dropping endpoint: [method:{}, path:{} found in file: [{}]]. " +
                    "Reason: This endpoint does not have minimum required information (method, path, response)",
                endpoint.getMethod(), endpoint.getPath(), fileName);

            return false;
        }

        if (updatable)
            savedEndpoints.removeIf(itEndpoint -> itEndpoint.getMethod().equals(endpoint.getMethod()) &&
                itEndpoint.getPath().equals(endpoint.getPath()));

        if (savedEndpoints.isEmpty())
            return true;

        for (Endpoint savedEndpoint : savedEndpoints) {
            if (savedEndpoint.getMethod().equals(endpoint.getMethod()) &&
                savedEndpoint.getPath().equals(endpoint.getPath())) {

                LOGGER.error("Skipping endpoint: [{} {} found in file: [{}]]. " +
                        "Reason: This endpoint already exists in file [{}]",
                    endpoint.getMethod(), endpoint.getPath(), fileName, savedEndpoint.getFileName());

                return false;
            }
        }

        return true;
    }

    public boolean check(String value, Object reference) {

        if (reference.equals("path"))
            return !StringUtils.isEmpty(value) && value.startsWith("/");
        else if (reference.equals("method"))
            return HttpMethod.resolve(value) != null;

        return false;
    }
}
