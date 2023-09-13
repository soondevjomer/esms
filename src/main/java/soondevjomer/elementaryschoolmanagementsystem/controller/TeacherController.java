package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soondevjomer.elementaryschoolmanagementsystem.dto.TeacherDto;
import soondevjomer.elementaryschoolmanagementsystem.service.TeacherService;

@RestController
@RequestMapping("api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<TeacherDto> addTeacher(@RequestBody TeacherDto teacherDto) {

        return new ResponseEntity<>(teacherService.addTeacher(teacherDto), HttpStatus.CREATED);
    }

    @GetMapping("/{teacherId}")
    @RolesAllowed({"ADMIN", "TEACHER"})
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable Integer teacherId) {

        return ResponseEntity.ok(teacherService.getTeacher(teacherId));
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Page<TeacherDto>> getTeachers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(teacherService.getTeachers(page, size, sortField, sortOrder));
    }

    @PutMapping("/{teacherId}")
    @RolesAllowed({"ADMIN", "TEACHER"})
    public ResponseEntity<TeacherDto> updateTeacher(
        @PathVariable Integer teacherId,
        @RequestBody TeacherDto teacherDto) {

        return ResponseEntity.ok(teacherService.updateTeacher(teacherId, teacherDto));
    }

    @DeleteMapping("/{teacherId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer teacherId) {

        return ResponseEntity.ok(teacherService.deleteTeacher(teacherId));
    }

}
