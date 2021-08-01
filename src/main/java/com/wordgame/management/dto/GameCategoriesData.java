package com.wordgame.management.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vladimir Bratchikov
 */
@Data
@NoArgsConstructor
public class GameCategoriesData implements Serializable {

    private List<Item> items;

    @Data
    @NoArgsConstructor
    public static class Item implements Serializable  {
        private String nameCategory;
        private String urlImage;
        private List<Level> levels;
    }

    @Data
    @NoArgsConstructor
    public static class Level implements Serializable  {
       private List<Card> cards;
    }

    @Data
    @NoArgsConstructor
    public static class Card  implements Serializable {
        private String nameCard;
        private Integer countWords;
       private List<String> words;
    }
}
