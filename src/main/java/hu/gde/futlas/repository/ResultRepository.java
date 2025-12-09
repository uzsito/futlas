package hu.gde.futlas.repository;

import hu.gde.futlas.model.Race;
import hu.gde.futlas.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByRaceOrderByTimeMinutesAsc(Race race);
}