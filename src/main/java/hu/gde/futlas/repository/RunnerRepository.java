package hu.gde.futlas.repository;

import hu.gde.futlas.model.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunnerRepository extends JpaRepository<Runner, Long> {
}