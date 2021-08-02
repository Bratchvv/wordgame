package com.wordgame.admin.controller.portal;

import com.wordgame.admin.model.FilterBuilder;
import com.wordgame.admin.model.PropertiesForm;
import com.wordgame.admin.model.StoreFilterForm;
import com.wordgame.management.entity.GameWords;
import com.wordgame.management.repository.GameWordsRepository;
import com.wordgame.management.service.GameWordsService;
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

import java.security.Principal;

/**
 * Основной контроллер для страниц.
 */
@Controller
@RequiredArgsConstructor
public class BackupWordsController {

    private final GameWordsRepository gameWordsRepository;
    private final GameWordsService gameWordsService;
    private final FilterBuilder<StoreFilterForm, GameWords> gameWordsFilterBuilder;

    @RequestMapping(value = { "/management/backup-words" }, method = RequestMethod.GET)
    public String backup(Model model, Principal principal,
                         @SortDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                         @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm) {
        model.addAttribute("page", gameWordsRepository.findAll(gameWordsFilterBuilder.build(storeFilterForm), pageable));

        return "/management/backup-words";
    }


    @RequestMapping(value="/management/backup-words/activate/{id}", method = RequestMethod.POST)
    public RedirectView activateWords(@PathVariable Long id,
                                       Pageable pageable,
                                       @ModelAttribute(value = "filterForm") StoreFilterForm storeFilterForm,
                                       BindingResult bindingResult, Model model, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/management/backup-words", true);
        try {
            gameWordsService.activateWords(id);
        } catch (Exception e) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при активации. " + e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при активации");
        }
        redir.addFlashAttribute("filterForm", new StoreFilterForm());
        redir.addFlashAttribute("page", gameWordsRepository.findAll(gameWordsFilterBuilder.build(new StoreFilterForm()), pageable));
        return redirectView;
    }
}
