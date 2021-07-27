package com.wordgame.generator.algorithm;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Vladimir Bratchikov
 */
public class GeneratorMain {

    public static void main(String[] args) {

        List<String> words = new LinkedList<>();
        try (Stream<String> lines = Files.lines(Paths.get("words.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(words::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var read = new ReadFileWord(words);
        var generator = new GenerateSquare(read,5,5, 10, 100);
        var chars = generator.maxCountGenerateBox();
        var bonus = BonusAlgorithm.generateBonusPosition(5, 5);

        for (int i = 0; i < generator.countRow; i++)
        {
            for (int j = 0; j < generator.countColumn; j++)
            {
                System.out.print(chars[i * generator.countRow + j] + " ");
            }
            System.out.println(" ");
        }

        generator.betterAllWord.forEach(System.out::println);
        bonus.forEach((k,v) -> System.out.printf("%s - %s \n", k,v));

    }
}
