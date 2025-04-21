package by.yayauheny.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Evgeny Leshok",
            email = "work.evles@gmail.com"
        ),
        description =
            "The Art Market Service is a microservice that enables creative individuals to buy "
                + "and sell art online, managing listings, transactions.",
        title = "Art Market Service",
        version = "1.0"
    )
)
public class OpenApiConfig {

}