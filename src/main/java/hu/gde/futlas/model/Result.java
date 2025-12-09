package hu.gde.futlas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int timeMinutes;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    private Runner runner;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
}