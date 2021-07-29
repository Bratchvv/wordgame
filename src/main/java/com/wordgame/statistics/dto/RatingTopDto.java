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
    private List<Res> res;
    private List<Range> range;

    @Data
    @Builder
    public static class Res {
        private String id;
        private Integer value;
        private Integer place;
        private String name;
        private String urlAvatar;
    }

    @Data
    @Builder
    public static class Range {
        private Integer start;
        private Integer size;
    }
}
