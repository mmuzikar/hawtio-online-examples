package io.hawt.online.example.springboot;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleCamelRoute extends EndpointRouteBuilder {

    @Override
    public void configure() {
        from(quartz("cron").cron("{{quartz.cron}}")).routeId("cron")
            .setBody().constant("Hello Camel! - cron")
            .to("stream:out")
            .to("mock:result");

        from("quartz:simple?trigger.repeatInterval={{quartz.repeatInterval}}").routeId("simple")
            .setBody().constant("Hello Camel! - simple")
            .to("stream:out")
            .to("mock:result");
    }

}
