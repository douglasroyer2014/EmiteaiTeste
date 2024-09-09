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
@CrossOrigin(origins = "*")
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

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody PersonModel personModel) {
        if (!personService.isValidCPF(personModel.getCpf())) {
            personService.update(personModel);
            return ResponseEntity.ok("Alterado com sucesso!");
        }
        return new ResponseEntity("Não possui registro cadastrado com esse CPF", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity delete(@PathVariable String cpf) {
        personService.deleteByCPF(cpf);
        return ResponseEntity.ok("Registro excluído com sucesso!");
    }

    @PostMapping("/gerarCSV")
    public ResponseEntity generateCSV() {
        personService.generateCSV();
        return ResponseEntity.ok("Arquivo sendo gerado e será gravado na pasta C:/temp");
    }

}
