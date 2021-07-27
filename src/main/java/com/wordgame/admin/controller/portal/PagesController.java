package com.wordgame.admin.controller.portal;

import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.service.GenerationPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * Основной контроллер для страниц.
 */
@Controller
@RequiredArgsConstructor
public class PagesController {

    private final GenerationPropertiesService generationPropertiesService;

    @RequestMapping(value = { "/","/management/rating" }, method = RequestMethod.GET)
    public String rating(Model model, Principal principal) {
        // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/rating";
    }

    @RequestMapping(value = { "/management/backup" }, method = RequestMethod.GET)
    public String backup(Model model, Principal principal) {
        // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/backup";
    }


}