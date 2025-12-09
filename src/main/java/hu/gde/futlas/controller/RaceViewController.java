package hu.gde.futlas.controller;

import hu.gde.futlas.repository.RaceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceViewController {

    private final RaceRepository raceRepository;

    public RaceViewController(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @GetMapping("/races")
    public String listRaces(Model model) {
        model.addAttribute("races", raceRepository.findAll());
        return "races";
    }
}
