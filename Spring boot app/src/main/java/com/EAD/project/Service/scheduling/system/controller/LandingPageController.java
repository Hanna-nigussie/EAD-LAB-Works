package com.EAD.project.Service.scheduling.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    @GetMapping("/")
    public String showLandingPage() {
        return "index";  // This will render the index.html from /src/main/resources/templates
    }
}
