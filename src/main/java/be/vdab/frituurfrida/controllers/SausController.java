package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[] {"mayonaise", "ongezoete room", "ketchup", "paprikapoeder", "sherry", "cognac", "gembersiroop", "citroensap", "peper", "zout"}),
            new Saus(2, "mayonaise", new String[] {"eidooier", "mosterd", "azijn", "zonnebloemolie", "zout"}),
            new Saus(3, "mosterd", new String[] {"mosterdzaden", "azijn", "water", "zout", "suiker", "peper", "mierikswortels", "knoflook"}),
            new Saus(4, "tartare", new String[] {"eieren", "sjalot", "mayonaise", "kappertjes", "bieslook"}),
            new Saus(5, "vinaigrette", new String[] {"ciderazijn", "graanmosterd", "olijfolie"})
    };
    /*voor de alfabetpagina */
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray(); /* weer gloednieuwe code.. */
    /*private variabele om lijst te maken van de unieke sauzen adhv gekozen karakter*/
    private List<Saus> sausMetGekozenLetter(Character letter) {
        return Arrays.stream(sauzen)
                .filter(saus -> saus.getNaam().charAt(0) == letter)
                .sorted()
                .collect(Collectors.toList());
    };

    @GetMapping
    public ModelAndView sauzen() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("sauzen", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        modelAndView.addObject("sauzen", sauzen);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView saus(@PathVariable long id) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("saus", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        Arrays.stream(sauzen).filter(saus -> saus.getId() == id).findFirst().ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }
    /*Eerst moet je het alfabet doorgeven aan de pagina alfabet*/
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
    /*Dan moeten we een map maken die unieke sauzen weergeeft obv gekozen karakter. Hiervoor hebben we een extra variabele gemaakt
    * boven de mappins private list <sauzen>*/
    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenMetGekozenLetter(@PathVariable Character letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", sausMetGekozenLetter(letter));
    }
}
