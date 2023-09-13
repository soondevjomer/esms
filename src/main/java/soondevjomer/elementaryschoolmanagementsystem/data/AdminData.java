package soondevjomer.elementaryschoolmanagementsystem.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import soondevjomer.elementaryschoolmanagementsystem.entity.*;
import soondevjomer.elementaryschoolmanagementsystem.repository.AdminRepository;
import soondevjomer.elementaryschoolmanagementsystem.security.User;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.ADMIN;

@Component
@Order(5)
@RequiredArgsConstructor
public class AdminData implements CommandLineRunner {

    private final Faker faker;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (adminRepository.count()!=0) {
            return;
        }

        Admin admin = new Admin();
        admin.setPerson(createPerson());
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setRole(ADMIN);
        admin.setUser(adminUser);
        adminRepository.save(admin);

        Admin admin2 = new Admin();
        admin2.setPerson(createPerson());
        User adminUser2 = new User();
        adminUser2.setUsername("admin2");
        adminUser2.setPassword(passwordEncoder.encode("admin"));
        adminUser2.setRole(ADMIN);
        admin2.setUser(adminUser2);
        adminRepository.save(admin2);
    }

    private Person createPerson() {
        Name name = new Name();
        name.setFirstName(faker.name().firstName());
        name.setLastName(faker.name().lastName());
        name.setPrefix(faker.name().prefix());
        name.setSuffix(faker.name().suffix());
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
