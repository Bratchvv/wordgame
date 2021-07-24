package com.wordgame.generator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Letter {

    // буква
    char symbol;

    // слово последняя буква в котором == letters
    char[] word;

    // словарь букв которые следуют после этой в словах
    // словарь сделан для удобства можно было и листом обойтись
    public Map<Character, Letter> nextLetter;

    public Letter()
    {
        symbol = '0';
        nextLetter = new LinkedHashMap<>();
        word = new char[0];
    }

    public Letter(char letter, HashMap<Character, Letter> nextLetter, char[] word)
    {
        symbol = letter;
        this.nextLetter = nextLetter;
        this.word = word;
    }

    public Letter(char letter)
    {
        symbol = letter;
        nextLetter = new LinkedHashMap<>();
        word = null;
    }
}
