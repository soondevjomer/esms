package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soondevjomer.elementaryschoolmanagementsystem.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("SELECT t FROM Teacher t " +
            "WHERE t.user.id = :userId")
    Optional<Teacher> findByUserId(Integer userId);
}
