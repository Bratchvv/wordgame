package com.wordgame.gameplay.service;

import com.wordgame.gameplay.dto.AdvancedPlayerDto;
import com.wordgame.gameplay.dto.PlayerDto;
import com.wordgame.gameplay.entity.Health;
import com.wordgame.gameplay.entity.Player;
import com.wordgame.gameplay.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final ModelMapper gameplayModelMapper;
    private final PlayerRepository playerRepository;

    public PlayerDto createPlayer(PlayerDto inputDto) {
        Player player = gameplayModelMapper.map(inputDto, Player.class);
        if(player.getHealth() == null) {
            player.setHealth(new Health());
        }
        playerRepository.save(player);
        return gameplayModelMapper.map(player, PlayerDto.class);
    }

    public AdvancedPlayerDto savePlayerData(AdvancedPlayerDto inputDto) {
        Player player = gameplayModelMapper.map(inputDto, Player.class);
        playerRepository.save(player);
        return gameplayModelMapper.map(player, AdvancedPlayerDto.class);
    }

    public AdvancedPlayerDto getPlayerData(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return gameplayModelMapper.map(player, AdvancedPlayerDto.class);
    }
}
