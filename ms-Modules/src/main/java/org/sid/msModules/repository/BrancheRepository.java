package org.sid.msModules.repository;

import org.sid.msModules.entities.Branche;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@Order(1)
public interface BrancheRepository extends MongoRepository<Branche, Long> {
    Page<Branche> findByNameContains(String name, Pageable pageable);
    Page<Branche> findByNameContainsAndModuleNameContains(String nameBranche, String nameModule, Pageable pageable);

}
