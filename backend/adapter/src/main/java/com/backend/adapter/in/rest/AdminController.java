package com.backend.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AdminController {

    @GetMapping("/text")
    public String findAdminName() {
        return "adminovici";
    }
}
