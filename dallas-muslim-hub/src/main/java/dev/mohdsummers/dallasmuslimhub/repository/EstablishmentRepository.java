package dev.mohdsummers.dallasmuslimhub.repository;

import dev.mohdsummers.dallasmuslimhub.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Integer> {
    Optional<Establishment> findEstablishmentByName(String name);

}