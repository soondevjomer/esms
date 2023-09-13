package soondevjomer.elementaryschoolmanagementsystem.service.impl;

import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import soondevjomer.elementaryschoolmanagementsystem.dto.*;
import soondevjomer.elementaryschoolmanagementsystem.entity.Admin;
import soondevjomer.elementaryschoolmanagementsystem.entity.Student;
import soondevjomer.elementaryschoolmanagementsystem.exception.NotFoundException;
import soondevjomer.elementaryschoolmanagementsystem.repository.AdminRepository;
import soondevjomer.elementaryschoolmanagementsystem.repository.StudentRepository;
import soondevjomer.elementaryschoolmanagementsystem.security.JwtService;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.AuthenticationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.ADMIN;
import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.STUDENT;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
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

        String jwtToken = jwtService.generateToken(createExtraClaimBasedOnRole(user), user);
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
            return null;
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

    private Map<String, Object> createExtraClaimBasedOnRole(User user) {

        Map<String, Object> extraClaim = new HashMap<>();


        if (user.getRole().equals(ADMIN)) {
            Admin admin = adminRepository.findByUserId(user.getId())
                    .orElseThrow((()->new NotFoundException("User", "id", user.getId().toString())));
            extraClaim.put("id", admin.getId());
            PersonDto personDto = new PersonDto();
            personDto.setId(admin.getPerson().getId());

            NameDto nameDto = modelMapper.map(admin.getPerson().getName(), NameDto.class);
            AddressDto addressDto = modelMapper.map(admin.getPerson().getAddress(), AddressDto.class);
            ContactDto contactDto = modelMapper.map(admin.getPerson().getContact(), ContactDto.class);
            personDto.setNameDto(nameDto);
            personDto.setAddressDto(addressDto);
            personDto.setContactDto(contactDto);
            extraClaim.put("person", personDto);
            extraClaim.put("user", modelMapper.map(admin.getUser(), UserDto.class));

            return extraClaim;
        }

        if (user.getRole().equals(STUDENT)) {
            Student student = studentRepository.findByUserId(user.getId())
                    .orElseThrow((()->new NotFoundException("User", "id", user.getId().toString())));
            extraClaim.put("id", student.getId());
            extraClaim.put("name", modelMapper.map(student.getPerson().getName(), NameDto.class));
            extraClaim.put("address", modelMapper.map(student.getPerson().getAddress(), AddressDto.class));
            extraClaim.put("contact", modelMapper.map(student.getPerson().getContact(), ContactDto.class));
            extraClaim.put("user", modelMapper.map(student.getUser(), UserDto.class));
            return extraClaim;
        }

        return extraClaim;
    }
}
