package soondevjomer.elementaryschoolmanagementsystem.service;

import org.springframework.data.domain.Page;
import soondevjomer.elementaryschoolmanagementsystem.dto.TeacherDto;

public interface TeacherService {

    TeacherDto addTeacher(TeacherDto teacherDto);

    TeacherDto getTeacher(Integer teacherId);

    Page<TeacherDto> getTeachers(Integer page, Integer size, String sortField, String sortOrder);

    TeacherDto updateTeacher(Integer teacherId, TeacherDto teacherDto);

    String deleteTeacher(Integer teacherId);
}
