package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
@RequestMapping("snacks")
class SnackController {
    /*voor alfabet */
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SnackService snackService;
    SnackController(SnackService snackService) {
        this.snackService = snackService;
    }
    //METHODS: @Mapping
    /*Eerst moet je het alfabet doorgeven aan de pagina alfabet*/
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("snacks", "alfabet", alfabet);
        modelAndView.addObject("dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView snacks (@PathVariable char letter) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("snacks", "alfabet", alfabet);
        modelAndView.addObject( "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        modelAndView.addObject("snacks", snackService.findByBeginNaam(String.valueOf(letter)));
        return modelAndView;
    }
    //controller voor DTO
    @GetMapping("totaalVerkoopSnack")
    public ModelAndView totaleVerkopenPerSnack() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("totaalVerkoopSnack", "totaleVerkopenPerSnack", snackService.findTotaleVerkopenPerSnack());
        modelAndView.addObject( "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }
}
