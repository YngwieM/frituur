package be.vdab.frituur.controllers;

import be.vdab.frituur.domain.Snack;
import be.vdab.frituur.exceptions.SnackNietGevondenException;
import be.vdab.frituur.forms.BeginletterForm;
import be.vdab.frituur.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("snacks")
class SnackController {
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SnackService snackService;

    SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginletter(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet )
                .addObject("snacks",snackService.findByBeginNaam(String.valueOf(letter)));
    }
    @GetMapping("beginletter/form")
    public ModelAndView beginletterForm() {
        return new ModelAndView("beginletter")
                .addObject(new BeginletterForm(null));
    }

    @GetMapping("beginletter")
    public ModelAndView beginLetter(@Valid BeginletterForm form, Errors errors) {
        var modelAndView = new ModelAndView("beginletter");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("snacks",
                snackService.findByBeginNaam(form.getBeginletter()));
    }
    @GetMapping("{id}/wijzigen/form")
    public ModelAndView wijzigenForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("wijzigSnack");
        snackService.findById(id).ifPresent(snack -> modelAndView.addObject(snack));
        return modelAndView;
    }
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
