package com.example.shoponlinepsw;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = "org.springframework.security.oauth2.jwt")
@SecurityScheme(
        name = "keycloak",
        openIdConnectUrl = "http://localhost:8081/realms/onlineShop/.well-known/openid-configuration",
        scheme = "bearer",
        type = SecuritySchemeType.OPENIDCONNECT,
        in = SecuritySchemeIn.HEADER
)
public class ShopOnlinePswApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOnlinePswApplication.class, args);
    }

}
