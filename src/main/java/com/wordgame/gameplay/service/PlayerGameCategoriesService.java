package com.wordgame.gameplay.service;

import com.wordgame.gameplay.dto.PlayerGameCategoriesData;
import com.wordgame.gameplay.entity.PlayerGameCategories;
import com.wordgame.gameplay.repository.PlayerGameCategoriesRepository;
import com.wordgame.gameplay.repository.PlayerRepository;
import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerGameCategoriesService {

    private final ModelMapper gameplayModelMapper;
    private final PlayerGameCategoriesRepository playerGameCategoriesRepository;
    private final PlayerRepository playerRepository;

    public PlayerGameCategoriesData getData(String id) {
        return gameplayModelMapper.map(playerGameCategoriesRepository.findFirstByPlayer_Id(id).getData(),
                                       PlayerGameCategoriesData.class);
    }

    public void saveData(String id, PlayerGameCategoriesData data) {
        PlayerGameCategories current = playerGameCategoriesRepository.findFirstByPlayer_Id(id);
        if(current == null) {
            current = new PlayerGameCategories();
            current.setData(data);
            current.setPlayer(
                playerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                                      "Игрок не найден. ID=" + id)));
            current.setDate(LocalDateTime.now());
            playerGameCategoriesRepository.save(current);
        } else {
            current.setData(data);
            current.setDate(LocalDateTime.now());
            playerGameCategoriesRepository.save(current);
        }
    }
}
