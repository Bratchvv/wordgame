package com.wordgame.management.service;

import com.wordgame.management.dto.GameCategoriesDto;
import com.wordgame.management.entity.GameCategories;
import com.wordgame.management.repository.GameCategoriesRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
@RequiredArgsConstructor
public class GameCategoriesService {

    private final GameCategoriesRepository gameCategoriesRepository;
    private final ModelMapper modelMapper;

    public GameCategoriesDto saveCategories(GameCategoriesDto categoriesDto) {
       var currentActive = gameCategoriesRepository.getActiveGameCategories();
       currentActive.setActive(false);
       gameCategoriesRepository.save(currentActive);
       var newActive = gameCategoriesRepository.save(modelMapper.map(categoriesDto, GameCategories.class));
       return modelMapper.map(newActive, GameCategoriesDto.class);
    }

    public boolean checkCategoriesByDate(LocalDateTime clientCategoriesDate) {
       var currentActive = gameCategoriesRepository.findActiveCategoriesDate();
       if(currentActive == null) {
           return false;
       }
       return currentActive.equals(clientCategoriesDate);
    }

    public GameCategoriesDto getActive() {
       return modelMapper.map(gameCategoriesRepository.getActiveGameCategories(), GameCategoriesDto.class);
    }

    public GameCategoriesDto getById(Long id) {
       return modelMapper.map(gameCategoriesRepository.findById(id), GameCategoriesDto.class);
    }


    public Page<GameCategoriesDto> getPages(Pageable pageable) {
        return gameCategoriesRepository.findAll(pageable)
            .map(v -> modelMapper.map(v, GameCategoriesDto.class));
    }
}
