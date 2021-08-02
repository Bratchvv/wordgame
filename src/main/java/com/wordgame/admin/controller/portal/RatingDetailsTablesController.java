package com.wordgame.admin.controller.portal;

import com.wordgame.admin.model.FilterBuilder;
import com.wordgame.admin.model.StoreFilterForm;
import com.wordgame.statistics.entity.RatingTable;
import com.wordgame.statistics.entity.RatingTableData;
import com.wordgame.statistics.repository.RatingTableDataRepository;
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
public class RatingDetailsTablesController {

    private final RatingTableDataRepository ratingTableDataRepository;
    private final RatingTableRepository ratingTableRepository;
    private final RatingTablesService ratingTablesService;
    private final FilterBuilder<StoreFilterForm, RatingTableData> ratingTablesDataFilterBuilder;

    @RequestMapping(value = { "/statistics/rating-details/{id}" }, method = RequestMethod.GET)
    public String rating( @PathVariable Long id,  Model model, Principal principal,
                         @SortDefault(sort = "value", direction = Sort.Direction.DESC) Pageable pageable,
                         @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm) {
        String name = ratingTableRepository.findById(id).orElseThrow(EntityExistsException::new).getName();
        model.addAttribute("pageId", id);
        model.addAttribute("tableName", name);
        storeFilterForm.setName(name);
        model.addAttribute("page", ratingTableDataRepository.findAll(ratingTablesDataFilterBuilder.build(storeFilterForm), pageable));
        return "/statistics/rating-details";
    }

    @RequestMapping(value="/statistics/rating-details/update-value/{pageId}/{id}", method = RequestMethod.POST)
    public RedirectView updateExpire(@PathVariable String pageId, @PathVariable String id,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "value") Integer value,
                                       Pageable pageable,
                                       @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm,
                                       BindingResult bindingResult, Model model, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/statistics/rating-details/"+pageId, true);
        try {
            ratingTablesService.insertDataToTable(name, id, value);
        } catch (Exception e) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при очитске. " + e.getMessage());
        }
        redir.addFlashAttribute("filterForm", new StoreFilterForm());
        model.addAttribute("page", ratingTableDataRepository.findAll(ratingTablesDataFilterBuilder.build(storeFilterForm), pageable));
        return redirectView;
    }
}
