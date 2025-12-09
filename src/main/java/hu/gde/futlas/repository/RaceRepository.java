package hu.gde.futlas.repository;

import hu.gde.futlas.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}