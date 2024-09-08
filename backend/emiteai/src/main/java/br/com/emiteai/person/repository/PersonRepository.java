package br.com.emiteai.person.repository;

import br.com.emiteai.person.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    PersonEntity findByCpf(String cpf);
}
