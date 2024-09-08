package br.com.emiteai.person.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Person")
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;
    String phone;
    String cpf;
    String address;
    String number;
    String complementary;
    String cep;
    String neighborhood;
    String city;
    String state;
    LocalDateTime lastDateUpdate;
    String lastUser;

}
