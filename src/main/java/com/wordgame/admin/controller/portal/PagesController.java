package com.wordgame.admin.controller.portal;

import com.wordgame.admin.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Основной контроллер для страниц.
 */
@Controller
public class PagesController {


    @RequestMapping(value = { "/","/management/rating" }, method = RequestMethod.GET)
    public String rating(Model model, Principal principal) {
        // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/rating";
    }

    @RequestMapping(value = { "/management/properties" }, method = RequestMethod.GET)
    public String properties(Model model, Principal principal) {
        // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/properties";
    }

    @RequestMapping(value = { "/management/backup" }, method = RequestMethod.GET)
    public String backup(Model model, Principal principal) {
        // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");

        return "/management/backup";
    }


}