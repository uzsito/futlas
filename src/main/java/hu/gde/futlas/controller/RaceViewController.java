package hu.gde.futlas.controller;

import hu.gde.futlas.model.Race;
import hu.gde.futlas.repository.RaceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/races/new")
    public String newRaceForm(Model model) {
        model.addAttribute("race", new Race());
        return "race-form";
    }

    @PostMapping("/races/new")
    public String createRace(@ModelAttribute Race race) {
        raceRepository.save(race);
        return "redirect:/races";
    }

    @GetMapping("/races/{id}")
    public String raceDetails(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        var race = raceRepository.findById(id).orElse(null);
        if (race == null) {
            return "redirect:/races";
        }

        var results = race.getResults(); // ha nincs getter, írunk egyet Race-ben

        model.addAttribute("race", race);
        model.addAttribute("results", results);

        return "race-details";
    }
}
