package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {
    /*statische variabele voor methode aantal bezoekers*/
    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;

    @GetMapping
    public ModelAndView index(@CookieValue Optional<Integer> aantalbezoeken, HttpServletResponse response) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals("1") ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView =  new ModelAndView("index", "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));

        /* toevoegen van methode om aantal bezoekers weer te geven */
        var nieuwAantalBezoeken = aantalbezoeken.orElse(0) + 1; /*Nieuwe aantal = aantalbezoeken + 1, bestaat dat niet (dwz eerste bezoek) heb je 0 + 1*/
        var cookie = new Cookie("aantalbezoeken", String.valueOf(nieuwAantalBezoeken));
        cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
        cookie.setPath("/");
        response.addCookie(cookie);
        modelAndView.addObject("aantalbezoeken", nieuwAantalBezoeken);

        return modelAndView;
    }
}
