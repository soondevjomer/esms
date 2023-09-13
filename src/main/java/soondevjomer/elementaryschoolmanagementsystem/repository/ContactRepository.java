package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soondevjomer.elementaryschoolmanagementsystem.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
