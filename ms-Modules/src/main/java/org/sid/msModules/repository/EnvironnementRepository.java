package org.sid.msModules.repository;

import org.sid.msModules.entities.Environnement;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Order(1)
public interface EnvironnementRepository extends MongoRepository<Environnement, Long> {
    Page<Environnement> findByNameContains(String name, Pageable pageable);

}
