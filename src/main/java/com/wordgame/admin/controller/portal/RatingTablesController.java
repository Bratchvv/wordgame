package com.wordgame.admin.controller.portal;

import com.wordgame.core.FilterBuilder;
import com.wordgame.admin.model.StoreFilterForm;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.filter.RatingTableFilterForm;
import com.wordgame.statistics.repository.RatingTableRepository;
import com.wordgame.statistics.service.RatingTablesService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Контроллер для работы с таблицами рейтингов
 *
 * @author vbratchikov
 */
@Controller
@RequiredArgsConstructor
public class RatingTablesController {

    private final RatingTableRepository ratingTableRepository;
    private final RatingTablesService ratingTablesService;
    private final FilterBuilder<RatingTableFilterForm, RatingTable> ratingTablesFilterBuilder;

    @RequestMapping(value = {"/statistics/rating"}, method = RequestMethod.GET)
    public String rating(Model model, Principal principal,
                         @SortDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable,
                         @ModelAttribute(value = "filterForm") RatingTableFilterForm storeFilterForm) {
        model.addAttribute("page",
                           ratingTableRepository.findAll(ratingTablesFilterBuilder.build(storeFilterForm), pageable));
        return "/statistics/rating";
    }

    @RequestMapping(value = "/statistics/rating/reset/{id}", method = RequestMethod.POST)
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
        redir.addFlashAttribute("filterForm", new RatingTableFilterForm());
        model.addAttribute("page", ratingTableRepository.findAll(ratingTablesFilterBuilder
                                                                     .build(new RatingTableFilterForm()), pageable));
        return redirectView;
    }

    @RequestMapping(value = "/statistics/rating/update-hours/{id}", method = RequestMethod.POST)
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
        redir.addFlashAttribute("filterForm", new RatingTableFilterForm());
        model.addAttribute("page", ratingTableRepository.findAll(ratingTablesFilterBuilder
                                                                     .build(new RatingTableFilterForm()), pageable));
        return redirectView;
    }
}
