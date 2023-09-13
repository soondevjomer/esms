package soondevjomer.elementaryschoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    private Integer id;

    @JsonProperty(value = "name")
    private NameDto nameDto;

    @JsonProperty(value = "address")
    private AddressDto addressDto;

    @JsonProperty(value = "contact")
    private ContactDto contactDto;

    @JsonIgnore
    private StudentDto studentDto;

    @JsonIgnore
    private TeacherDto teacherDto;

    @JsonIgnore
    private AdminDto adminDto;
}
