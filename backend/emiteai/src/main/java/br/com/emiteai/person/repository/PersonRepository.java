package br.com.emiteai.person.repository;

import br.com.emiteai.person.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    /**
     * Busca as informaçõesa da pessoa pelo CPF.
     *
     * @param cpf informação do CPF.
     * @return um {@link PersonEntity}.
     */
    PersonEntity findByCpf(String cpf);

    /**
     * Deleta a pessoa com o CPF informado.
     *
     * @param cpf informação do CPF.
     * @return quantidade de pessoas deletada.
     */
    long deleteByCpf(String cpf);
}
