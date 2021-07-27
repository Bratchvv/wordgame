package com.wordgame.admin.controller.portal;

import com.wordgame.admin.model.PropertiesForm;
import com.wordgame.generator.service.InMemDictionaryService;
import com.wordgame.management.dto.GamePropertyDto;
import com.wordgame.management.service.GameWordsService;
import com.wordgame.management.service.GenerationPropertiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Основной контроллер для страниц.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagementUpdateController {

    private final GenerationPropertiesService generationPropertiesService;
    private final GameWordsService gameWordsService;


    @RequestMapping(value = { "/management/properties" }, method = RequestMethod.GET)
    public String properties(Model model, Principal principal) {
        model.addAttribute("propertiesData",
                new PropertiesForm(generationPropertiesService.getList(), gameWordsService.getActiveWords(0)));
        return "/management/properties";
    }

    @RequestMapping(value="/management/properties/update",method=RequestMethod.POST)
    public RedirectView updateProperty(@ModelAttribute("propertiesData") PropertiesForm propertiesForm,
                            BindingResult result, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/management/properties", true);
        if (result.hasErrors()) {
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при сохранении");
            return redirectView;
        }
        try {
            propertiesForm.getProperties()
                    .forEach(property -> generationPropertiesService.updateParam(property.getName(), property.getValue()));
            redir.addFlashAttribute("properties", generationPropertiesService.getList());
        } catch (Exception e) {
            e.printStackTrace();
            redir.addFlashAttribute("globalErrorMessage", "Ошибка при сохранении");
        }
        return redirectView;
    }


    @PostMapping("/management/properties/words")
    @Transactional
    public RedirectView uploadFromSziNvp(@RequestParam("uploadingFile") MultipartFile file, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/management/properties", true);
        if (file.isEmpty()) {
            redir.addFlashAttribute("globalErrorMessage", "Необходимо выбрать словарь");
            return redirectView;
        }
        if (!file.getOriginalFilename().endsWith(".txt")) {
            redir.addFlashAttribute("globalErrorMessage", "Поддерживаются только '.txt' файлы");
            return redirectView;
        }
        try {
            byte[] bytes = file.getBytes();
            String words = new String(bytes);
            gameWordsService.addNewWords(file.getOriginalFilename(), 0, words);
        } catch (IOException e) {
            e.printStackTrace();
            redir.addFlashAttribute("globalErrorMessage", "Не удалось загрузить словарь");
        } catch (Exception e) {
            e.printStackTrace();
            redir.addFlashAttribute("globalErrorMessage", "Ошибка сервера");
        }
        redir.addFlashAttribute("globalSuccessMessage", "Словарь загружен");
        return redirectView;
    }


}