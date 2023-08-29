package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationRequestDto;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationResponseDto;
import soondevjomer.elementaryschoolmanagementsystem.service.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        return ResponseEntity.ok(authenticationService.login(authenticationRequestDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        return null;
    }
}
