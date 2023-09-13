package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soondevjomer.elementaryschoolmanagementsystem.dto.StudentDto;
import soondevjomer.elementaryschoolmanagementsystem.service.StudentService;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {

        return new ResponseEntity<>(studentService.addStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    @RolesAllowed({"ADMIN", "STUDENT"})
    public ResponseEntity<StudentDto> getStudent(@PathVariable Integer studentId) {

        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "TEACHER"})
    public ResponseEntity<Page<StudentDto>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(studentService.getStudents(page, size, sortField, sortOrder));
    }

    @PutMapping("/{studentId}")
    @RolesAllowed({"ADMIN", "TEACHER", "STUDENT"})
    public ResponseEntity<StudentDto> updateStudent(
        @PathVariable Integer studentId,
        @RequestBody StudentDto studentDto) {

        return ResponseEntity.ok(studentService.updateStudent(studentId, studentDto));
    }

    @DeleteMapping("/{studentId}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId) {

        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

}
