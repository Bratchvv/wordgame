package com.wordgame.gameplay.service;

import com.wordgame.gameplay.dto.AdvancedInputPlayerDto;
import com.wordgame.gameplay.dto.AdvancedPlayerDto;
import com.wordgame.gameplay.dto.PlayerDto;
import com.wordgame.gameplay.entity.Health;
import com.wordgame.gameplay.entity.Player;
import com.wordgame.gameplay.repository.PlayerRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final ModelMapper gameplayModelMapper;
    private final PlayerRepository playerRepository;

    public PlayerDto createPlayer(PlayerDto inputDto) {
        Player player = gameplayModelMapper.map(inputDto, Player.class);
        if (player.getHealth() == null) {
            player.setHealth(new Health());
        }
        playerRepository.save(player);
        return gameplayModelMapper.map(player, PlayerDto.class);
    }

    public AdvancedPlayerDto savePlayerData(AdvancedInputPlayerDto inputDto) {
        Player player = playerRepository.findById(inputDto.getId()).orElseThrow(EntityNotFoundException::new);
        Player playerRequest = gameplayModelMapper.map(inputDto, Player.class);
        player.setName(playerRequest.getName());
        player.setUrlAvatar(playerRequest.getUrlAvatar());
        var millis = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        player.getHealth().setTimeUTCSaving(millis);
        player.getHealth().setSecondsRestoreLife(inputDto.getHealth().getSecondsRestoreLife());
        player.getHealth().setLifes(inputDto.getHealth().getLifes());
        playerRepository.save(player);
        var res = gameplayModelMapper.map(player, AdvancedPlayerDto.class);
        res.getHealth().setTimeUTCNow(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
        return res;
    }

    public AdvancedPlayerDto getPlayerData(String id) {
        Player player = playerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var millis = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        var result = gameplayModelMapper.map(player, AdvancedPlayerDto.class);
        result.getHealth().setTimeUTCNow(millis);
        return result;
    }
}
