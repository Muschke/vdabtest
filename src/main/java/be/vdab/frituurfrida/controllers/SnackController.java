package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import be.vdab.frituurfrida.forms.FindByBeginNaamForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    //controller voor formulier
    @GetMapping("vindbeginsnack/form")
    public ModelAndView findByBeginSnackForm() {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("vindbeginsnack").addObject(new FindByBeginNaamForm(" "));
        modelAndView.addObject( "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        return modelAndView;
    }

    //controller om formulier te verwerken
    @GetMapping("vindbeginsnack")
    public ModelAndView findByBeginSnack(@Valid FindByBeginNaamForm form, Errors errors) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("vindbeginsnack");
        modelAndView.addObject( "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        if(errors.hasErrors()) {
            return modelAndView;
        }
        modelAndView.addObject("snacks", snackService.findByBeginNaam(form.getBeginSnack()));
        return modelAndView;
    }
    //controller om post request om snack te wijzigen, om formulier te plaatsen
    @GetMapping("{id}/wijzigen/form")
    public ModelAndView wijzigenForm(@PathVariable long id) {
        var maandagOfAndereDag = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "Vandaag zijn wij gesloten" : "Wij zijn open, u bent welkom";
        var modelAndView = new ModelAndView("wijzigSnack");
        modelAndView.addObject( "dagVdWeek", maandagOfAndereDag);
        modelAndView.addObject("locatie", new Adres("Krieltjesweg", 101, new Gemeente("Bruhhe", 4880)));
        snackService.findById(id).ifPresent(snack -> modelAndView.addObject(snack));
        return modelAndView;
    }
    //controller om POST request te verwerken
    @PostMapping("wijzigen")
    public String wijzigen(@Valid Snack snack, Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return "wijzigSnack";
        }
        try {
            snackService.update(snack);
            return "redirect:/";
        } catch (SnackNietGevondenException ex) {
            redirect.addAttribute("snackNietGevonden", snack.getId());
            return "redirect:/";
        }
    }
}
