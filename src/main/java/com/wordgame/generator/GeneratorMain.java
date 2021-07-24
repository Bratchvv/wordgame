package com.wordgame.generator;

public class GeneratorMain {

    public static void main(String[] args) {
        var read = new ReadFileWord();
        read.readAndBuildTree();

        var generator = new GenerateSquare(read,5,5);
        var chars = generator.maxCountGenerateBox();

        var bonus = Bonus.generateBonusPosition(5, 5);

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
