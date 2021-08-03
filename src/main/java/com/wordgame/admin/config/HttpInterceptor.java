package com.wordgame.admin.config;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.google.common.collect.Lists;
import com.wordgame.admin.model.PageInfo;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Конфиг для перехватчика http
 *
 * @author Vladimir Bbratchikov
 */
@Component
@RequiredArgsConstructor
public class HttpInterceptor implements HandlerInterceptor {

    private final Session session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        selectedPage(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) {
        selectedPage(request, response, modelAndView);
    }

    private void selectedPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        String selectedPage = selectedPage(request, response);
        if (nonNull(modelAndView)) {
            modelAndView.addObject("pagesList", Lists.newArrayList(
                new PageInfo("", ""),
                new PageInfo("Таблица Рейтинов", "/statistics/rating"),
                new PageInfo("Параметры", "/management/properties"),
                new PageInfo("Таблица Словарей", "/management/backup-words"),
                new PageInfo("Таблица Категорий", "/management/backup-categories")
            ));
            modelAndView.addObject("selectedPage", selectedPage);
        }
    }

    private String selectedPage(HttpServletRequest request, HttpServletResponse response) {

        String selectedPage;
        if (isNull(request.getCookies())) {
            selectedPage = "/management/rating";
        } else {
            selectedPage = Arrays.stream(request.getCookies())
                .filter(c -> "selectedPage".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("/management/rating");
        }
        Cookie cookie = new Cookie("selectedPage", selectedPage.toString());
        cookie.setPath("/");
        response.addCookie(cookie);

        session.setCurrentPage(selectedPage);
        return selectedPage;
    }

}
