package com.wordgame.admin.controller.portal;

import com.wordgame.admin.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Контроллер для страниц c ошибкой
 */
@Controller
public class ErrorController {

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String employeeInfo = WebUtils.toString(loginedUser);

            // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
            model.addAttribute("employeeInfo", employeeInfo);

            // добавляем новую переменную в общую модель данных страницы (можно использовать эту переменную в html)
            String message = "Hi " + principal.getName() //
                + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }

        return "error/403Page";
    }

}