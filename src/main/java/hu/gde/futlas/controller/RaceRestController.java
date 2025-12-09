package hu.gde.futlas.controller;

import hu.gde.futlas.model.Race;
import hu.gde.futlas.model.Result;
import hu.gde.futlas.model.Runner;
import hu.gde.futlas.repository.RaceRepository;
import hu.gde.futlas.repository.ResultRepository;
import hu.gde.futlas.repository.RunnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RaceRestController {

    private final RunnerRepository runnerRepository;
    private final RaceRepository raceRepository;
    private final ResultRepository resultRepository;

    public RaceRestController(RunnerRepository runnerRepository,
                              RaceRepository raceRepository,
                              ResultRepository resultRepository) {
        this.runnerRepository = runnerRepository;
        this.raceRepository = raceRepository;
        this.resultRepository = resultRepository;
    }

    @GetMapping("/getRunners")
    public List<Runner> getRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/addRunner")
    public ResponseEntity<Runner> addRunner(@RequestBody Runner runner) {
        if (runner.getName() == null || runner.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Runner saved = runnerRepository.save(runner);
        return ResponseEntity.ok(saved);
    }

    public static class RaceRunnerDto {
        public String runnerName;
        public int timeMinutes;

        public RaceRunnerDto(String runnerName, int timeMinutes) {
            this.runnerName = runnerName;
            this.timeMinutes = timeMinutes;
        }
    }

    @GetMapping("/getRaceRunners/{id}")
    public ResponseEntity<List<RaceRunnerDto>> getRaceRunners(@PathVariable("id") Long raceId) {
        return raceRepository.findById(raceId)
                .map(race -> {
                    List<Result> results = resultRepository.findByRaceOrderByTimeMinutesAsc(race);
                    List<RaceRunnerDto> dtos = results.stream()
                            .map(r -> new RaceRunnerDto(
                                    r.getRunner().getName(),
                                    r.getTimeMinutes()
                            ))
                            .toList();
                    return ResponseEntity.ok(dtos);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/updateRace")
    public ResponseEntity<Race> updateRace(@RequestBody Race updated) {
        if (updated.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return raceRepository.findById(updated.getId())
                .map(race -> {
                    race.setName(updated.getName());
                    race.setDistanceKm(updated.getDistanceKm());
                    Race saved = raceRepository.save(race);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public static class AddResultRequest {
        public Long runnerId;
        public Long raceId;
        public int timeMinutes;
    }

    @PostMapping("/addResult")
    public ResponseEntity<Result> addResult(@RequestBody AddResultRequest req) {
        if (req.runnerId == null || req.raceId == null) {
            return ResponseEntity.badRequest().build();
        }

        Runner runner = runnerRepository.findById(req.runnerId).orElse(null);
        Race race = raceRepository.findById(req.raceId).orElse(null);

        if (runner == null || race == null) {
            return ResponseEntity.badRequest().build();
        }

        Result result = Result.builder()
                .runner(runner)
                .race(race)
                .timeMinutes(req.timeMinutes)
                .build();

        Result saved = resultRepository.save(result);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getAverageTime/{raceId}")
    public ResponseEntity<Double> getAverageTime(@PathVariable Long raceId) {
        return raceRepository.findById(raceId)
                .map(race -> {
                    List<Result> results = resultRepository.findByRaceOrderByTimeMinutesAsc(race);
                    if (results.isEmpty()) {
                        return ResponseEntity.ok(0.0);
                    }
                    double avg = results.stream()
                            .mapToInt(Result::getTimeMinutes)
                            .average()
                            .orElse(0.0);
                    return ResponseEntity.ok(avg);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
