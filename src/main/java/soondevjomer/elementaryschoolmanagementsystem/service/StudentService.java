package soondevjomer.elementaryschoolmanagementsystem.service;


import org.springframework.data.domain.Page;
import soondevjomer.elementaryschoolmanagementsystem.dto.StudentDto;

public interface StudentService {

    StudentDto addStudent(StudentDto studentDto);

    StudentDto getStudent(Integer studentId);

    Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder);

    StudentDto updateStudent(Integer studentId, StudentDto studentDto);
    String deleteStudent(Integer studentId);
}
