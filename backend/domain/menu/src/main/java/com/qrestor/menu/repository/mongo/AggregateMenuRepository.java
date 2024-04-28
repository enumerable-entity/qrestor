package com.qrestor.menu.repository.mongo;

import com.qrestor.menu.entity.AggregatedMenuDocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AggregateMenuRepository extends MongoRepository<AggregatedMenuDocumentEntity, UUID> {
}
