/*
 * Copyright © 2017 the original authors (@michaeltecourt)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.michaeltecourt.appengine.server;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

/**
 * Spring Boot "component test", launching the application in memory on a random
 * port using an embedded Jetty server. Definitely <strong>NOT</strong> the same
 * as the actual app engine runtime, can still tell you whether your Spring
 * Context is alright or not.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleSpringBootApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleSpringBootApplicationTest {

    @Value("http://localhost:${local.server.port}${server.contextPath:}/aliens")
    URI aliensUri;

    @Autowired
    private EmbeddedServletContainerFactory container;

    @Test
    public void applicationShouldStartWithEmbeddedJetty() {
        Assertions.assertThat(container).isInstanceOf(JettyEmbeddedServletContainerFactory.class);
    }

    /**
     * Test the actual {@literal /aliens} service with an HTTP GET and make
     * assertions on the JSON response.
     */
    @Test
    public void aliens() {
        // @formatter:off
        RestAssured
            .given()
            .when()
                .get(aliensUri)
            .then()
                .statusCode(200)
                .body("aliens[0].name", Matchers.is("E.T."))
                .body("aliens[0].home", Matchers.is("Home"))
                .body("aliens[1].name", Matchers.is("Marvin the Martian"))
                .body("aliens[1].home", Matchers.is("Mars"));
        // @formatter:on
    }

}
