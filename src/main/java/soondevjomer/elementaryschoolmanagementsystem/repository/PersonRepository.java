package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soondevjomer.elementaryschoolmanagementsystem.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
