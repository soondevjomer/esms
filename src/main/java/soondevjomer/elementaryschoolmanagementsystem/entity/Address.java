package soondevjomer.elementaryschoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    private Integer id;

    private String line;
    private String street;
    private String city;
    private String state;
    private String zipcode;

    // RELATIONSHIPS
    @OneToOne(mappedBy = "address")
    private Person person;
}
