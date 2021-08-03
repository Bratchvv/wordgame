package com.wordgame.generator.model;

import com.wordgame.generator.algorithm.ReadFileWord;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorInputParams {

    private List<String> dictionaryWords;
    private ReadFileWord readFileWord;
    private int countColumn;
    private int countRow;
    private int maxIteration;
    private int minCountWord;

}
