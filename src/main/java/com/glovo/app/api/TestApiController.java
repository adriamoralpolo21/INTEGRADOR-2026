package com.glovo.app.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@SecurityRequirement(name = "BearerAuth") // 👈 Esto le dice a Swagger que necesita token
public class TestApiController {

    @GetMapping("/hola")
    public String hola() {
        return "Hola desde API protegida con JWT 🔐";
    }
}
