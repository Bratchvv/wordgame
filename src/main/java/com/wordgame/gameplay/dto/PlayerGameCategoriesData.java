package com.wordgame.gameplay.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Vladimir Bratchikov
 */
@Data
@Builder
public class PlayerGameCategoriesData {

    private List<Item> items;

    @Data
    @Builder
    public static class Item {
        private String nameCategory;
        private List<Level> levels;
    }

    @Data
    @Builder
    public static class Level {
       private List<Card> cards;
    }

    @Data
    @Builder
    public static class Card {
        private String nameCard;
        private Integer countWords;
        private List<String> words;
        private List<Character> letters;
    }
}
