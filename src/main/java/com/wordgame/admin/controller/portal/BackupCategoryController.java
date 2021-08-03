package com.wordgame.admin.controller.portal;

import com.wordgame.core.FilterBuilder;
import com.wordgame.admin.model.StoreFilterForm;
import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.repository.GameCategoriesRepository;
import com.wordgame.management.service.GameCategoriesService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Контроллер для бекапов категорий
 *
 * @author vbratchikov
 */
@Controller
@RequiredArgsConstructor
public class BackupCategoryController {

    private final GameCategoriesRepository gameCategoriesRepository;
    private final GameCategoriesService gameCategoriesService;
    private final FilterBuilder<StoreFilterForm, GameCategories> gameCategoriesFilterBuilder;

    @RequestMapping(value = {"/management/backup-categories"}, method = RequestMethod.GET)
    public String backup(Model model, Principal principal,
                         @SortDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                         @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm) {
        model.addAttribute("page", gameCategoriesRepository
            .findAll(gameCategoriesFilterBuilder.build(storeFilterForm), pageable));

        return "/management/backup-categories";
    }


    @RequestMapping(value = "/management/backup-categories/activate/{id}", method = RequestMethod.POST)
    public RedirectView activate(@PathVariable Long id,
                                 Pageable pageable,
                                 @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm,
                                 BindingResult bindingResult, Model model, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/management/backup-words", true);
        try {
            gameCategoriesService.activate(id);
        } catch (Exception e) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при активации. " + e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при активации");
        }
        redir.addFlashAttribute("filterForm", new StoreFilterForm());
        redir.addFlashAttribute("page", gameCategoriesRepository
            .findAll(gameCategoriesFilterBuilder.build(new StoreFilterForm()), pageable));
        return redirectView;
    }
}
