package com.wordgame.statistics.config;

import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class RatingScheduledConfig {

    private final RatingTablesService ratingTablesService;

    @Scheduled(fixedDelay = 1000*60*60, initialDelay = 1000*60)
    public void clearRatingScheduler() {
        ratingTablesService.clearRatings();
    }
}

