package hu.gde.futlas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A név nem lehet üres.")
    private String name;

    @Positive(message = "A távnak pozitív számnak kell lennie.")
    private double distanceKm;

    @OneToMany(mappedBy = "race")
    private List<Result> results;
}