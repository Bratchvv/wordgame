package com.wordgame.management.service;

import com.wordgame.management.dto.GameCategoriesData;
import com.wordgame.management.dto.GameCategoriesDto;
import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.repository.GameCategoriesRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GameCategoriesService {

    private final GameCategoriesRepository gameCategoriesRepository;
    private final ModelMapper modelMapper;

    public GameCategoriesDto saveCategories(GameCategoriesData categories) {
       var currentActive = gameCategoriesRepository.getActiveGameCategories();
       if(currentActive != null) {
           currentActive.setActive(false);
           gameCategoriesRepository.save(currentActive);
       }
        GameCategories gameCategories = new GameCategories();
        gameCategories.setActive(true);
        gameCategories.setDate(LocalDateTime.now());
        gameCategories.setData(categories);
        var newActive = gameCategoriesRepository.save(gameCategories);
       return modelMapper.map(newActive, GameCategoriesDto.class);
    }

    public boolean checkCategoriesByDate(Long id) {
       var currentActive = gameCategoriesRepository.findActiveCategoriesId();
       if(currentActive == null) {
           return false;
       }
       return currentActive.equals(id);
    }

    public GameCategoriesDto getActive() {
       return modelMapper.map(gameCategoriesRepository.getActiveGameCategories(), GameCategoriesDto.class);
    }

    public GameCategoriesDto getById(Long id) {
       return modelMapper.map(gameCategoriesRepository.findById(id)
               .orElseThrow(EntityNotFoundException::new), GameCategoriesDto.class);
    }


    public Page<GameCategoriesDto> getPages(Pageable pageable) {
        return gameCategoriesRepository.findAll(pageable)
            .map(v -> modelMapper.map(v, GameCategoriesDto.class));
    }
}
