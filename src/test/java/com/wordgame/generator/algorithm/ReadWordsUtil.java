package com.wordgame.generator.algorithm;

import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class ReadWordsUtil {

    public static List<String> readWordsFile(ClassLoader classLoader) {
        List<String> words = new LinkedList<>();
        try (Stream<String> lines = Files.lines(
                Paths.get(classLoader.getResource("words.txt").toURI()), Charset.defaultCharset())) {
            lines.forEachOrdered(words::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
