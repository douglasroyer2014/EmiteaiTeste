package br.com.emiteai.person.controller;

import br.com.emiteai.person.entity.PersonEntity;
import br.com.emiteai.person.model.PersonModel;
import br.com.emiteai.person.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/{cpf}")
    public ResponseEntity filter(@PathVariable String cpf) {
        PersonEntity person = personService.getPersonByCpf(cpf);
        if (person != null) {
            return ResponseEntity.ok(person);
        }
        return new ResponseEntity("CPF não encontrado!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody PersonModel personModel) {
        if (personService.isValidCPF(personModel.getCpf())) {
            personService.save(personModel);
            return ResponseEntity.ok("Cadastrado com sucesso!");
        }
        return new ResponseEntity("já possui registro cadastrado com esse CPF", HttpStatus.BAD_REQUEST);
    }

}
