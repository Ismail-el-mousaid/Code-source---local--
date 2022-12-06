package org.sid.msModules.repository;

import org.sid.msModules.entities.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@Order(1)
public interface ModuleRepository extends MongoRepository<Module, Long> {
    Page<Module> findByNameContains(String name, Pageable pageable);

}
