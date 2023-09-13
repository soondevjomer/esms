package soondevjomer.elementaryschoolmanagementsystem.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import soondevjomer.elementaryschoolmanagementsystem.entity.*;
import soondevjomer.elementaryschoolmanagementsystem.repository.StudentRepository;
import soondevjomer.elementaryschoolmanagementsystem.security.User;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.STUDENT;

@Component
@RequiredArgsConstructor
public class StudentData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (studentRepository.count()!=0) {
            return;
        }

        Student student = new Student();
        student.setPerson(createPerson());
        User userStudent = new User();
        userStudent.setUsername("student");
        userStudent.setPassword(passwordEncoder.encode("student"));
        userStudent.setRole(STUDENT);
        student.setUser(userStudent);
        studentRepository.save(student);


    }

    private Person createPerson() {
        Name name = new Name();
        name.setFirstName(faker.name().firstName());
        name.setLastName(faker.name().lastName());
        Address address = new Address();
        address.setLine(faker.address().buildingNumber());
        address.setStreet(faker.address().streetName());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setZipcode(faker.address().zipCode());
        Contact contact = new Contact();
        contact.setMobileNumber(faker.phoneNumber().cellPhone());
        contact.setEmail(faker.internet().emailAddress());
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        person.setContact(contact);
        return person;
    }
}
