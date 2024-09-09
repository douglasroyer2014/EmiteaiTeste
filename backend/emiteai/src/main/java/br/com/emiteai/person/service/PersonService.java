package br.com.emiteai.person.service;

import br.com.emiteai.person.entity.PersonEntity;
import br.com.emiteai.person.model.PersonModel;
import br.com.emiteai.person.repository.PersonRepository;
import com.opencsv.CSVWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

    public void update(PersonModel personModel) {
        PersonEntity person = personRepository.findByCpf(personModel.getCpf());
        PersonEntity newPerson = personModel.convertPersonEntity();
        newPerson.setId(person.getId());
        personRepository.save(newPerson);
    }

    @Transactional
    public void deleteByCPF(String cpf) {
        personRepository.deleteByCpf(cpf);
    }

    @Async
    public void generateCSV() {
        Iterable<PersonEntity> personList = personRepository.findAll();
        String nameFile = "C:/temp/Pessoa" + System.currentTimeMillis();
        try (CSVWriter writer = new CSVWriter(new FileWriter(nameFile), ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            String[] header = {"nome", "telegone", "cpf", "cep", "endereço", "número", "complemento", "bairro", "cidade", "estado", "último ao alterar", "última data de alteração"};
            writer.writeNext(header);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            for (PersonEntity person : personList) {
                String[] data = {person.getName(), person.getPhone(), person.getCpf(), person.getCep(), person.getAddress(), person.getNumber(), person.getComplementary(),
                person.getNeighborhood(), person.getCity(), person.getState(), person.getLastUser(), person.getLastDateUpdate().format(formatter)};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
