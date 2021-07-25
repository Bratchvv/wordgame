package com.wordgame.generator.model;

import com.wordgame.generator.algorithm.ReadFileWord;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeneratorInputParams {

    private List<String> dictionaryWords;
    private ReadFileWord readFileWord;
    private int countColumn;
    private int countRow;

}
