package com.wordgame.statistics.config;

import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class RatingScheduledConfig {

    private final RatingTablesService ratingTablesService;

    /**
     * Раз в 5 мин, сканируем таблицы для возможной очистки
     */
    @Scheduled(fixedDelay = 1000*60*5, initialDelay = 1000*60)
    public void clearRatingScheduler() {
        ratingTablesService.clearRatings();
    }
}

