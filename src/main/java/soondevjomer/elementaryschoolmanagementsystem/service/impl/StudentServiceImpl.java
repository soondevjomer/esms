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
import soondevjomer.elementaryschoolmanagementsystem.repository.StudentRepository;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.StudentService;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.STUDENT;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public StudentDto addStudent(StudentDto studentDto) {

        Person person = new Person();
        person.setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        person.setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        person.setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));
        Person savedPerson = personRepository.save(person);
        Student student = new Student();
        student.setPerson(savedPerson);

        if (studentDto.getUserDto()!=null) {
            User savedUser = userRepository.save(createUser(studentDto.getUserDto()));
            student.setUser(savedUser);
        }

        Student savedStudent = studentRepository.save(student);

        return createStudentDtoResponse(savedStudent);
    }

    @Override
    public StudentDto getStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NotFoundException("student", "id", studentId.toString()));

        return createStudentDtoResponse(student);
    }

    @Override
    public Page<StudentDto> getStudents(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return studentRepository.findAll(pageable)
                .map(this::createStudentDtoResponse);
    }

    @Transactional
    @Override
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NotFoundException("student", "id", studentId.toString()));

        student.getPerson().setName(modelMapper.map(studentDto.getPersonDto().getNameDto(), Name.class));
        student.getPerson().setAddress(modelMapper.map(studentDto.getPersonDto().getAddressDto(), Address.class));
        student.getPerson().setContact(modelMapper.map(studentDto.getPersonDto().getContactDto(), Contact.class));
        Student updatedStudent = studentRepository.save(student);

        return createStudentDtoResponse(updatedStudent);
    }

    @Transactional
    @Override
    public String deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new NotFoundException("student", "id", studentId.toString()));

        studentRepository.delete(student);

        return "Student deleted successfully.";
    }

    private StudentDto createStudentDtoResponse(Student student) {

        PersonDto personDto = new PersonDto();
        personDto.setId(student.getPerson().getId());
        personDto.setNameDto(modelMapper.map(student.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(student.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(student.getPerson().getContact(), ContactDto.class));
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setPersonDto(personDto);

        if (student.getUser()!=null) {
            UserDto userDto = modelMapper.map(student.getUser(), UserDto.class);
            studentDto.setUserDto(userDto);
        }

        return studentDto;
    }

    private User createUser(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(STUDENT);

        return user;
    }
}
