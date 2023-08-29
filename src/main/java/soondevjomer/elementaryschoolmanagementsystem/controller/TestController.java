package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/guest")
    public String guest() {
        return "Welcome to the public page.";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public String admins() {
        return "You must be admin so you accessed it.";
    }

    @RolesAllowed({"ADMIN", "TEACHER"})
    @GetMapping("/teacher")
    public String teacher() {
        return "You must be teacher or admin so you accessed it.";
    }

    @RolesAllowed({"ADMIN", "STUDENT"})
    @GetMapping("/student")
    public String student() {
        return "You must be student or admin so you accessed it.";
    }
}
