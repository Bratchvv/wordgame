package com.wordgame.statistics.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Vladimir Bratchikov
 */
@Data
@Builder
public class RatingTopDto {

    private String name;
    private Long timeRestore;
    private List<Res> res;
    private List<Range> range;

    @Data
    @Builder
    public static class Res {
        private String id;
        private Long value;
        private Long place;
        private String name;
        private String urlAvatar;
    }

    @Data
    @Builder
    public static class Range {
        private Long start;
        private Long size;
    }
}
