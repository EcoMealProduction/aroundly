package com.backend.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    @GetMapping
    public Map<String, List<String>> findAllInGivenRange() {
        return Map.of("incidents", List.of("incident1", "incident2"));
    }
}
