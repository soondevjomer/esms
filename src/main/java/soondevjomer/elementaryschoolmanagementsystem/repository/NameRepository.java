package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soondevjomer.elementaryschoolmanagementsystem.entity.Name;

public interface NameRepository extends JpaRepository<Name, Integer> {
}
