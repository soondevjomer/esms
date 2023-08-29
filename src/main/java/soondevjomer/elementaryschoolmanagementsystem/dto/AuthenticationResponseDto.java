package soondevjomer.elementaryschoolmanagementsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {

    private String accessToken;
    private String refreshToken;
}
