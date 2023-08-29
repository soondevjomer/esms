package soondevjomer.elementaryschoolmanagementsystem.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;

import java.util.List;

import static soondevjomer.elementaryschoolmanagementsystem.constant.Role.*;

@Component
@RequiredArgsConstructor
public class UserData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count()!=0) {
            return;
        }

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ADMIN)
                .build();

        User teacher = User.builder()
                .username("teacher")
                .password(passwordEncoder.encode("teacher"))
                .role(TEACHER)
                .build();

        User student = User.builder()
                .username("student")
                .password(passwordEncoder.encode("student"))
                .role(STUDENT)
                .build();

        userRepository.saveAll(List.of(admin, teacher, student));

    }
}
