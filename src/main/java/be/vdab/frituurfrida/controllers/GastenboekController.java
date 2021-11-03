package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.forms.GastenboekIngaveForm;
import be.vdab.frituurfrida.services.GastenboekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("gastenboek")
class GastenboekController {
    private final GastenboekService gastenboekService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    GastenboekController(GastenboekService gastenboekService) {
        this.gastenboekService = gastenboekService;
    }

    @GetMapping
    public ModelAndView gastenboek() {
        return new ModelAndView("gastenboek", "gastenboek", gastenboekService.findAll());
    }

    @GetMapping("toevoegen/form")
    public ModelAndView toevoegenForm() {
        return new ModelAndView("gastenboek", "gastenboek", gastenboekService.findAll())
                .addObject(new GastenboekIngaveForm("",""));
    }
    @PostMapping("toevoegen")
    public ModelAndView toevoegen(@Valid GastenboekIngaveForm form, Errors errors){
        if (errors.hasErrors()) {
            return new ModelAndView("gastenboek",
                    "gastenboek", gastenboekService.findAll());
        }
        gastenboekService.create(form);
        return new ModelAndView("redirect:/gastenboek");
    }
    //voor verwijdering in het form
    @PostMapping("verwijderen")
    public String delete(Optional<Long[]> id) {
        id.ifPresent(ids -> gastenboekService.delete(ids));
        return "redirect:/gastenboek";
    }
}
