package com.wordgame.admin.rfc;

import com.wordgame.gameplay.dto.PlayerDto;
import com.wordgame.gameplay.service.PlayerService;
import com.wordgame.generator.service.GeneratorService;
import com.wordgame.statistics.dto.EditableRatingTableDto;
import com.wordgame.statistics.repository.RatingTableDataRepository;
import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Специальный сервис для стрес теста, БЕЗ БУФЕРА, включается параметром rfc.raw.generator=true,
 * кол-во итераций - rfc.raw.count=999
 *
 * @author Vladimir Bratchikov
 */
@RequiredArgsConstructor
@Service
@Slf4j
@Order(3)
public class GeneratorPlayerData {

    private final PlayerService playerService;
    private final RatingTablesService ratingTablesService;

    @Value("${rfc.player.count}")
    private int count;

    @PostConstruct
    public void afterInit() {
        if (count>0) {
            log.info("Run rfc test for player generation ");
            EditableRatingTableDto ratingTable = ratingTablesService.createRatingTable("test_table", null);
            List<PlayerDto> players = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                players.add(playerService.createPlayer(PlayerDto.builder()
                        .id(UUID.randomUUID().toString())
                        .name("player_" + i)
                        .urlAvatar("http://player_logo_" + i)
                        .build()));
            }
            for (int i = 0; i < players.size(); i++) {
                PlayerDto p = players.get(i);
                ratingTablesService.insertDataToTable(ratingTable.getName(), p.getId(), i*10);
            }
        }
    }
}
