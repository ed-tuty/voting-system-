package com.sicredi.test.repository;

import com.sicredi.test.model.Agenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends ReactiveMongoRepository<Agenda, String> {

}
