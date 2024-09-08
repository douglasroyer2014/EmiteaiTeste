package br.com.emiteai.person.service;

import br.com.emiteai.person.entity.PersonEntity;
import br.com.emiteai.person.model.PersonModel;
import br.com.emiteai.person.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public boolean isValidCPF(String cpf) {
        PersonEntity person = getPersonByCpf(cpf);
        return person == null;
    }

    public PersonEntity getPersonByCpf(String cpf) {
        return personRepository.findByCpf(cpf);
    }

    public void save(PersonModel personModel) {
        personRepository.save(personModel.convertPersonEntity());
    }
}
