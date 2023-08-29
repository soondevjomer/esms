package soondevjomer.elementaryschoolmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationRequestDto;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationResponseDto;
import soondevjomer.elementaryschoolmanagementsystem.security.JwtService;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.AuthenticationService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;


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

//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }
}
