package com.backend.infra.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MockController {
    @GetMapping("/api/v1/events")
    public ResponseEntity<String> events() {
        return ResponseEntity.ok("events");
    }

    @GetMapping("/admin/text")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }
}
