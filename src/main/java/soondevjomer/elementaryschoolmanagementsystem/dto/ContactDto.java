package soondevjomer.elementaryschoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Integer id;
    private String mobileNumber;
    private String email;

    @JsonIgnore
    private PersonDto personDto;
}
