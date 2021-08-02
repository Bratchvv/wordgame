package com.wordgame.admin.controller.portal;

import com.wordgame.admin.model.FilterBuilder;
import com.wordgame.admin.model.StoreFilterForm;
import com.wordgame.management.entity.GameWords;
import com.wordgame.management.repository.GameWordsRepository;
import com.wordgame.management.service.GameWordsService;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.repository.RatingTableRepository;
import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import java.security.Principal;

/**
 * Основной контроллер для страниц.
 */
@Controller
@RequiredArgsConstructor
public class RatingTablesController {

    private final RatingTableRepository ratingTableRepository;
    private final RatingTablesService ratingTablesService;
    private final FilterBuilder<StoreFilterForm, RatingTable> ratingTablesFilterBuilder;

    @RequestMapping(value = { "/statistics/rating" }, method = RequestMethod.GET)
    public String rating(Model model, Principal principal,
                         @SortDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable,
                         @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm) {
        model.addAttribute("page", ratingTableRepository.findAll(ratingTablesFilterBuilder.build(storeFilterForm), pageable));
        return "/statistics/rating";
    }

    @RequestMapping(value="/statistics/rating/reset/{id}", method = RequestMethod.POST)
    public RedirectView reset(@PathVariable Long id,
                                       Pageable pageable,
                                       @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm,
                                       BindingResult bindingResult, Model model, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/statistics/rating", true);
        try {
            ratingTablesService.clearRating(id);
        } catch (Exception e) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при очитске. " + e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при очитске");
        }
        redir.addFlashAttribute("filterForm", new StoreFilterForm());
        model.addAttribute("page", ratingTableRepository.findAll(ratingTablesFilterBuilder
                .build(new StoreFilterForm()), pageable));
        return redirectView;
    }

    @RequestMapping(value="/statistics/rating/update-hours/{id}", method = RequestMethod.POST)
    public RedirectView updateExpire(@PathVariable Long id, @RequestParam(name = "expireHoursCycle") Integer hours,
                                       Pageable pageable,
                                       @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm,
                                       BindingResult bindingResult, Model model, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/statistics/rating", true);
        try {
            ratingTablesService.updateHours(id, hours);
        } catch (Exception e) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при очитске. " + e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при очитске");
        }
        redir.addFlashAttribute("filterForm", new StoreFilterForm());
        model.addAttribute("page", ratingTableRepository.findAll(ratingTablesFilterBuilder
                .build(new StoreFilterForm()), pageable));
        return redirectView;
    }
}
