package hu.gde.futlas.config;

import hu.gde.futlas.model.*;
import hu.gde.futlas.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RunnerRepository runnerRepo,
                               RaceRepository raceRepo,
                               ResultRepository resultRepo) {
        return args -> {

            Runner r1 = runnerRepo.save(Runner.builder()
                    .name("Éowyn").age(25).gender("F").build());
            Runner r2 = runnerRepo.save(Runner.builder()
                    .name("Aragorn").age(87).gender("M").build());
            Runner r3 = runnerRepo.save(Runner.builder()
                    .name("Arwen").age(2778).gender("F").build());
            Runner r4 = runnerRepo.save(Runner.builder()
                    .name("Legolas").age(2931).gender("M").build());

            Race race1 = raceRepo.save(Race.builder()
                    .name("Városi 5K").distanceKm(5.0).build());
            Race race2 = raceRepo.save(Race.builder()
                    .name("Hegyi 10K").distanceKm(10.0).build());

            resultRepo.save(Result.builder().runner(r1).race(race1).timeMinutes(24).build());
            resultRepo.save(Result.builder().runner(r2).race(race1).timeMinutes(22).build());
            resultRepo.save(Result.builder().runner(r3).race(race1).timeMinutes(27).build());

            resultRepo.save(Result.builder().runner(r1).race(race2).timeMinutes(55).build());
            resultRepo.save(Result.builder().runner(r2).race(race2).timeMinutes(52).build());
            resultRepo.save(Result.builder().runner(r4).race(race2).timeMinutes(49).build());
        };
    }
}