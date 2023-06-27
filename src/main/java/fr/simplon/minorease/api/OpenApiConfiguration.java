package fr.simplon.minorease.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Open API specifications",
        description = "Points d'entrée de l'API de gestion des sondages"),
        servers = @Server(url = "http://localhost:8080/api"))
class OpenApiConfiguration
{
}

