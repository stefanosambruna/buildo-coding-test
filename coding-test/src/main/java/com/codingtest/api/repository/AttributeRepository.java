package com.codingtest.api.repository;

import com.codingtest.api.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByClientClientServiceIdAndName(String clientServiceId, String attrName);

    List<Attribute> findAllByClientClientServiceId(String clientServiceId);

    int deleteByClientClientServiceIdAndName(String clientServiceId, String attrName);
}
