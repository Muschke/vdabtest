package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import be.vdab.frituurfrida.forms.ToevoegenBerichtForm;
import be.vdab.frituurfrida.services.GastenboekService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
                .addObject(new ToevoegenBerichtForm("",""));
    }
}
