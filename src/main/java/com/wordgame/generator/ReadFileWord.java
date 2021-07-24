package com.wordgame.generator;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class ReadFileWord {

    // список букв доступных для рендомного выбора, что бы заполнять пустые клетки в поле
    public char[] letters = new char[] { 'а', 'о', 'у', 'ы', 'э', 'я', 'е', 'ё', 'ю', 'и', 'а', 'о', 'у', 'ы', 'э', 'я', 'е', 'ё', 'ю', 'и', 'б', 'в', 'г', 'д', 'й', 'ж', 'з', 'к', 'л', 'м', 'н', 'п', 'р', 'с', 'т', 'ф', 'х', 'ц', 'ч', 'ш', 'щ' };

    // список всех слов
    public List<char[]> charStrings = new ArrayList<>();

    // спловарь деревьев в которых хранятся слова в древовидной структуре
    public Map<Character, Letter> treeWord = new LinkedHashMap<>();

    // путь до файла где лежит список слов
    private String path;

    @SneakyThrows
    public void readAndBuildTree()
    {
        long time = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(Paths.get("words.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(word -> {

                // слова из одной буквы в базу не добавляются
                if (word.length() == 1) return;

                // добавляем слово в лист
                var charFromStr = word.toCharArray();
                charStrings.add(charFromStr);

                Letter currentLetter = new Letter();

                for (int i = 0; i < charFromStr.length; i++)
                {
                    // если первая буква то сначала мы работаем не с текущим элементом, а со словарем
                    if (i == 0)
                    {
                        if (!treeContainsLetter(charFromStr[i], treeWord))
                            treeWord.put(charFromStr[i], new Letter(charFromStr[i]));

                        currentLetter = treeWord.get(charFromStr[i]);
                    }

                    // идем по дереву, если ветви со следующей буквой нет, то создаем ее
                    if (i > 0)
                    {
                        if (!treeContainsLetter(charFromStr[i], currentLetter.nextLetter))
                            currentLetter.nextLetter.put(charFromStr[i], new Letter(charFromStr[i]));

                        currentLetter = currentLetter.nextLetter.get(charFromStr[i]);
                    }

                    // на последней букве слово закончено, значит можем сохранить его
                    if (i == charFromStr.length - 1)
                        currentLetter.word = charFromStr;

                }
            });
        }

        log.info("Чтение заняло - " + (System.currentTimeMillis() - time));
        log.info("Кол-во слов - " + charStrings.size());

    }


    private boolean treeContainsLetter(char v, Map<Character, Letter> dict)
    {
        return dict.containsKey(v);
    }

}
