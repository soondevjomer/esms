package soondevjomer.elementaryschoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    private String username;

    private String password;
}
