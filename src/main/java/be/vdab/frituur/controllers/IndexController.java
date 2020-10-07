package be.vdab.frituur.controllers;
import be.vdab.frituur.domain.Adres;
import be.vdab.frituur.domain.Gemeente;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
@RestController
@RequestMapping("/")
 class IndexController {
    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;

     @GetMapping
     public ModelAndView index(@CookieValue(name = "reedsBezocht", required = false)
                                       String reedsBezocht, HttpServletResponse response) {
         var openGesloten = LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY ?
                 "gesloten" : "open";
         var modelAndView = new ModelAndView("index", "openGesloten", openGesloten);
         modelAndView.addObject(new Adres("Grote markt", "7", new Gemeente("Brussel", 1000)));

         var cookie = new Cookie("reedsBezocht", "ja");
         cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
         cookie.setPath("/");
         response.addCookie(cookie);
         if (reedsBezocht != null) {
             modelAndView.addObject("reedsBezocht", true);
         }
             return modelAndView;
         }


    }
