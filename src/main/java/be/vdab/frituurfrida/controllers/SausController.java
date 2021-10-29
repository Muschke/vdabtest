package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final SausService sausService;
    /*voor de alfabetpagina */
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    SausController(SausService sausService) {
        this.sausService = sausService;
    }


    @GetMapping
    public ModelAndView sauzen() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("sauzen", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        modelAndView.addObject("sauzen", sausService.findAll());
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView saus(@PathVariable long id) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("saus", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        sausService.findById(id).ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }
    /*Eerst moet je het alfabet doorgeven aan de pagina alfabet*/
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("alfabet", "alfabet", alfabet);
        modelAndView.addObject("dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }
    /*Dan moeten we een map maken die unieke sauzen weergeeft obv gekozen karakter. Hiervoor hebben we een extra variabele gemaakt
    * boven de mappins private list <sauzen>*/
    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenMetGekozenLetter(@PathVariable Character letter) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("alfabet", "alfabet", alfabet);
        modelAndView.addObject("sauzen", sausService.findByNaamBegintMet(letter));
        modelAndView.addObject("dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }

}
