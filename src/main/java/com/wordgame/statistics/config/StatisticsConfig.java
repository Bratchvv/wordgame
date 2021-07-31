package com.wordgame.statistics.config;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

import com.wordgame.statistics.service.RatingTablesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class StatisticsConfig {

    private final RatingTablesService ratingTablesService;

    @Bean
    public ModelMapper statisticsModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Scheduled(fixedDelay = 1000*60*60, initialDelay = 1000*60)
    public void clearRatingScheduler() {
        ratingTablesService.clearRatings();
    }
}

