package soondevjomer.elementaryschoolmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soondevjomer.elementaryschoolmanagementsystem.dto.*;
import soondevjomer.elementaryschoolmanagementsystem.entity.*;
import soondevjomer.elementaryschoolmanagementsystem.exception.NotFoundException;
import soondevjomer.elementaryschoolmanagementsystem.repository.PersonRepository;
import soondevjomer.elementaryschoolmanagementsystem.repository.TeacherRepository;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.TeacherService;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.STUDENT;
import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.TEACHER;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public TeacherDto addTeacher(TeacherDto teacherDto) {

        Person person = new Person();
        person.setName(modelMapper.map(teacherDto.getPersonDto().getNameDto(), Name.class));
        person.setAddress(modelMapper.map(teacherDto.getPersonDto().getAddressDto(), Address.class));
        person.setContact(modelMapper.map(teacherDto.getPersonDto().getContactDto(), Contact.class));
        Person savedPerson = personRepository.save(person);

        Teacher teacher = new Teacher();
        teacher.setPerson(savedPerson);

        if (teacherDto.getUserDto()!=null) {
            User savedUser = userRepository.save(createUser(teacherDto.getUserDto()));
            teacher.setUser(savedUser);
        }

        Teacher savedTeacher = teacherRepository.save(teacher);

        return createTeacherDtoResponse(savedTeacher);
    }

    @Override
    public TeacherDto getTeacher(Integer teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()->new NotFoundException("Teacher", "id", teacherId.toString()));

        return createTeacherDtoResponse(teacher);
    }

    @Override
    public Page<TeacherDto> getTeachers(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return teacherRepository.findAll(pageable)
                .map(this::createTeacherDtoResponse);
    }

    @Transactional
    @Override
    public TeacherDto updateTeacher(Integer teacherId, TeacherDto teacherDto) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()->new NotFoundException("Teacher", "id", teacherId.toString()));

        teacher.getPerson().setName(modelMapper.map(teacherDto.getPersonDto().getNameDto(), Name.class));
        teacher.getPerson().setAddress(modelMapper.map(teacherDto.getPersonDto().getAddressDto(), Address.class));
        teacher.getPerson().setContact(modelMapper.map(teacherDto.getPersonDto().getContactDto(), Contact.class));
        Teacher updatedTeacher = teacherRepository.save(teacher);

        return createTeacherDtoResponse(updatedTeacher);
    }

    @Override
    public String deleteTeacher(Integer teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()->new NotFoundException("Teacher", "id", teacherId.toString()));

        teacherRepository.delete(teacher);

        return "Teacher deleted successfully.";
    }

    private TeacherDto createTeacherDtoResponse(Teacher teacher) {

        PersonDto personDto = new PersonDto();
        personDto.setId(teacher.getPerson().getId());
        personDto.setNameDto(modelMapper.map(teacher.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(teacher.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(teacher.getPerson().getContact(), ContactDto.class));
        TeacherDto teacherDtoResponse = new TeacherDto();
        teacherDtoResponse.setId(teacher.getId());
        teacherDtoResponse.setPersonDto(personDto);

        if (teacher.getUser()!=null) {
            UserDto userDto = modelMapper.map(teacher.getUser(), UserDto.class);
            teacherDtoResponse.setUserDto(userDto);
        }

        return teacherDtoResponse;
    }

    private User createUser(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(TEACHER);

        return user;
    }
}
