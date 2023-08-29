package soondevjomer.elementaryschoolmanagementsystem.service.impl;

import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationRequestDto;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationResponseDto;
import soondevjomer.elementaryschoolmanagementsystem.exception.NotFoundException;
import soondevjomer.elementaryschoolmanagementsystem.security.JwtService;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.getUsername(),
                            authenticationRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException bc) {
            throw new BadCredentialsException("User not found.");
        } catch (DisabledException disabledException) {
            throw new DisabledException("User access is disabled.");
        }

        User user = userRepository.findByUsername(authenticationRequestDto.getUsername())
                .orElseThrow();

//        Map<String, Object> extraClaim = createExtraClaimBasedOnRole(user);

//        String jwtToken = jwtService.generateToken(extraClaim, user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        String username = "";
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);

        try {
            username = jwtService.extractUsername(refreshToken);
        } catch (JwtException e) {
            logger.info("JwtException from refresh: {}", e.getMessage());
        }

        if (username != null) {
            final String usernameNotNull = username;
            User user = userRepository.findByUsername(usernameNotNull)
                    .orElseThrow(()->new NotFoundException("User", "username", usernameNotNull));

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);

                return AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
            }
        }
        return null;
    }
}
