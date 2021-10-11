package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public ModelAndView index() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals("1") ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("index", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }
}
