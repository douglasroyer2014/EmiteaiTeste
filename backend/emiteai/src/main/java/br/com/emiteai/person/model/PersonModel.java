package br.com.emiteai.person.model;

import br.com.emiteai.person.entity.PersonEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonModel {

    String name;
    String phone;
    @NotBlank(message = "O campo CPF é obrigatório")
    String cpf;
    String address;
    String number;
    String complementary;
    String cep;
    String neighborhood;
    String city;
    String state;

    public PersonEntity convertPersonEntity() {
        PersonEntity person = new PersonEntity();
        person.setPhone(getPhone());
        person.setCpf(getCpf());
        person.setAddress(getAddress());
        person.setNumber(getNumber());
        person.setComplementary(getComplementary());
        person.setCep(getCep());
        person.setNeighborhood(getNeighborhood());
        person.setCity(getCity());
        person.setState(getState());
        person.setLastDateUpdate(LocalDateTime.now());
        person.setLastUser("Admin");

        return person;
    }
}
