package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soondevjomer.elementaryschoolmanagementsystem.dto.UserDto;
import soondevjomer.elementaryschoolmanagementsystem.service.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(userService.getUsers(page, size, sortField, sortOrder));
    }

    @GetMapping("/{userId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {

        return ResponseEntity.ok(userService.getUser(userId));
    }
}
