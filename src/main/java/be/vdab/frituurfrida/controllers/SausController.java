package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("index", "sauzen", sauzen);

    }
}
