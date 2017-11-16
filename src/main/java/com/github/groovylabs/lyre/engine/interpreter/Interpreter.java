
/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.groovylabs.lyre.engine.interpreter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.groovylabs.lyre.config.LyreProperties;
import com.github.groovylabs.lyre.domain.Bundle;
import com.github.groovylabs.lyre.domain.Endpoint;
import com.github.groovylabs.lyre.domain.Level;
import com.github.groovylabs.lyre.engine.manager.Manager;
import com.github.groovylabs.lyre.utils.EndpointUtils;
import com.github.groovylabs.lyre.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Interpreter extends Parser {

    private LyreProperties lyreProperties;

    private Manager manager;

    private Bundle bundle;

    private boolean update = false;

    @Autowired
    public Interpreter(Validator validator, LyreProperties lyreProperties, Manager manager, Bundle bundle) {
        super(validator);
        this.lyreProperties = lyreProperties;
        this.manager = manager;
        this.bundle = bundle;
    }

    public void interpret(Map<String, ObjectNode> nodes) {

        for (Map.Entry<String, ObjectNode> object : nodes.entrySet()) {

            metadata = object.getKey().split("\\|");
            ObjectNode parentNode = object.getValue();

            parentNode.fields().forEachRemaining(entry -> {

                Endpoint endpoint = new Endpoint();

                try {

                    this.parse(endpoint, entry, Level.ENDPOINT);

                    if (validator.integrity(metadata[0], endpoint, bundle.getEndpoints(), update)) {
                        endpoint.setHash(EndpointUtils.createHash(endpoint));
                        bundle.add(endpoint);
                    }

                } catch (Exception e) {

                    LOGGER.error("Error parsing file [{}] to endpoint bundle", metadata[0]);

                    if (lyreProperties.isDebug()) {
                        LOGGER.error("Stacktrace", e);
                    } else
                        LOGGER.warn("\u21B3 " + "Enable debug mode to see stacktrace log");

                }

            });

        }

        update = true;

        manager.handle(bundle, "Interpreted " + nodes.entrySet().size() + " file resource(s)");
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
