package be.vdab.frituur.controllers;


import be.vdab.frituur.services.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ModelAndView genres() {
        return new ModelAndView("genres", "genres", genreService.findAll());
    }

    @GetMapping("id")
    public ModelAndView genre(@PathVariable long genreId) {
        var modelAndView = new ModelAndView("genres");
        genreService.findById(genreId).ifPresent(genre -> modelAndView.addObject(genre));
        return modelAndView;
    }

}
