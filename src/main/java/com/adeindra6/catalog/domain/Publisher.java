package com.adeindra6.catalog.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "publisher")
public class Publisher extends AbstractBaseEntity {
    private static final long serialVersionUID = 459823187L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_generator")
    @SequenceGenerator(name = "publisher_generator", sequenceName = "publisher_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
