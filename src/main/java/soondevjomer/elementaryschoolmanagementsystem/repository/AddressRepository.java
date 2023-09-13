package soondevjomer.elementaryschoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soondevjomer.elementaryschoolmanagementsystem.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
