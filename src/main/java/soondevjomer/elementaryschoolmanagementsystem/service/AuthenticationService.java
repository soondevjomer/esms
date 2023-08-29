package soondevjomer.elementaryschoolmanagementsystem.service;

import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationRequestDto;
import soondevjomer.elementaryschoolmanagementsystem.dto.AuthenticationResponseDto;

public interface AuthenticationService {

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto);

//    AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
