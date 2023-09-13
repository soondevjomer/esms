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
public class AddressDto {

    private Integer id;

    private String line;
    private String street;
    private String city;
    private String state;
    private String zipcode;

    @JsonIgnore
    private PersonDto personDto;
}
