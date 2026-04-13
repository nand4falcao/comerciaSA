package com.muralis.agenda.repository;

import com.muralis.agenda.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    List<Contato> findByClienteIdOrderByIdAsc(int clienteId);

    Optional<Contato> findByIdAndClienteId(int id, int clienteId);
}