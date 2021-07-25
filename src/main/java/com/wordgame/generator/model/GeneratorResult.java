package com.wordgame.generator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratorResult {

    private List<Character> letters;
    private List<String> words;
    private List<Bonus> bonus;

}
