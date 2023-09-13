package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soondevjomer.elementaryschoolmanagementsystem.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a " +
            "WHERE a.user.id = :userId ")
    Optional<Admin> findByUserId(Integer userId);
}
