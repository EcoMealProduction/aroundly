package com.backend.adapter.inbound.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@Tag(name = "Welcome", description = "Welcome endpoint to test the deploy")
public class WelcomeController {

  @GetMapping("/healthz")
  public String welcome() {
    return "Welcome";
  }
}
