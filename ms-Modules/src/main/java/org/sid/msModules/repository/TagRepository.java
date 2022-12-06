package org.sid.msModules.repository;

import org.sid.msModules.entities.Tag;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Order(1)
public interface TagRepository extends MongoRepository<Tag, Long> {
    @Override
    Page<Tag> findAll(Pageable pageable);
}
