package com.penguineering.calmixer;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Calendar Mixer",
                version = "0.1",
                description = "REST API of the Calendar Mixer",
                license = @License(name = "MIT", url = "https://github.com/penguineer/CalMixer/blob/main/LICENSE.txt"),
                contact = @Contact(name = "Stefan Haun", email = "tux@netz39.de")
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
