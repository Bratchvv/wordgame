package com.wordgame.generator.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorResult {

    private List<Character> letters;
    private List<String> words;
    private List<Bonus> bonus;

}
