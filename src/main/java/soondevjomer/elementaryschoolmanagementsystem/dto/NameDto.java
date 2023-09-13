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
public class NameDto {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String prefix;
    private String suffix;

    @JsonIgnore
    private PersonDto personDto;
}
