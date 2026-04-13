package com.muralis.agenda.repository;

import com.muralis.agenda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    //o Spring já cria o Salvar, deletar e buscar sozinho
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByCpf(String cpf);

    @Query(value = "SELECT * FROM clientes WHERE REPLACE(REPLACE(REPLACE(TRIM(cpf), '.', ''), '-', ''), ' ', '') = :digitos",
            nativeQuery = true)
    List<Cliente> findByCpfSomenteDigitos(@Param("digitos") String digitos);
}