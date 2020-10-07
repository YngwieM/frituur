package be.vdab.frituur.controllers;


import be.vdab.frituur.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SausService sausService;

    SausController(SausService sausService) {
        this.sausService = sausService;
    }

    // constructor met parameter
    @GetMapping
    public ModelAndView sauzen() {
        return new ModelAndView("sauzen", "sauzen", sausService.findAll());
    }
    @GetMapping("{id}")
    public ModelAndView saus(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        sausService.findById(id).ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", sausService.findByNaamBegintMet(letter));
    }
}


//@Controller
//@RequestMapping("sauzen")
//public class SausController {
//    private final Saus[] sauzen = { new Saus(1,"cocktail",new String[]{"mayonaise", "ketchup", "cognac"}),
//    new Saus(2,"mayonaise", new String[] {"ei", "mosterd"}),
//            new Saus(3,"mosterd", new String[] {"mosterd", "azijn", "witte wijn"}),
//            new Saus(4,"tartare", new String[] {"mayonaise", "augurk", "tabasco"}),
//            new Saus(5,"vinaigrette", new String[] {"olijfolie","mosterd","azijn"})
//    };
//    @GetMapping
//    public ModelAndView sauzen() {
//        return new ModelAndView("sauzen", "sauzen", sauzen);
//    }
//
//    @GetMapping("{id}")
//    public ModelAndView saus(@PathVariable long id) {
//        var modelAndView = new ModelAndView("saus");
//        Arrays.stream(sauzen).filter(saus -> saus.getNummer() == id).findFirst()
//                .ifPresent(saus -> modelAndView.addObject(saus));
//        return modelAndView;
//    }
//    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//    @GetMapping("alfabet")
//    public ModelAndView alfabet() {
//        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
//    }
//    private List<Saus> sauzenDieBeginnenMet(char letter) {
//        return Arrays.stream(sauzen).filter(saus->saus.getNaam().charAt(0)==letter)
//                .collect(Collectors.toList());
//    }
//    @GetMapping("alfabet/{letter}")
//    public ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
//        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
//                .addObject("sauzen", sauzenDieBeginnenMet(letter));
//    }
//}
