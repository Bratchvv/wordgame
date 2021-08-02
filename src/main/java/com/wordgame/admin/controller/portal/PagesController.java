package com.wordgame.admin.controller.portal;

import com.wordgame.management.service.GenerationPropertiesService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Основной контроллер для страниц.
 */
@Controller
@RequiredArgsConstructor
public class PagesController {

    private final GenerationPropertiesService generationPropertiesService;

    @RequestMapping(value = { "/","/management/rating" }, method = RequestMethod.GET)
    public String rating(Model model, Principal principal) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/rating";
    }
}
