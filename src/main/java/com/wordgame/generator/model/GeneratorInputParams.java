package com.wordgame.generator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratorInputParams {

    private List<String> dictionaryWords;
    private int countColumn;
    private int countRow;

}
