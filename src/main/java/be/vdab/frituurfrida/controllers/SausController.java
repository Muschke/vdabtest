package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[] {"mayonaise", "ongezoete room", "ketchup", "paprikapoeder", "sherry", "cognac", "gembersiroop", "citroensap", "peper", "zout"}),
            new Saus(2, "mayonaise", new String[] {"eidooier", "mosterd", "azijn", "zonnebloemolie", "zout"}),
            new Saus(3, "mosterd", new String[] {"mosterdzaden", "azijn", "water", "zout", "suiker", "peper", "mierikswortels", "knoflook"}),
            new Saus(4, "tartare", new String[] {"eieren", "sjalot", "mayonaise", "kappertjes", "bieslook"}),
            new Saus(5, "vinaigrette", new String[] {"ciderazijn", "graanmosterd", "olijfolie"})};
    @GetMapping
    public ModelAndView sauzen() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals("1") ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("sauzen", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        modelAndView.addObject("sauzen", sauzen);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView saus(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        Arrays.stream(sauzen).filter(saus -> saus.getId() == id).findFirst().ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }
}
