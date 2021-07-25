package com.wordgame.generator.algorithm;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Vladimir Bratchikov
 */
@Slf4j
public class GenerateSquare {

    // Параметры отвечающие за генерацию лучшего результата
    // количество попыток генерации
    int maxIteration = 10;
    // минимальное количество слов при котором генерация считается успешной
    int minCountWord = 100;

    // здесь хранится список слов полученный при лучшей генерации
    List<String> betterAllWord = new ArrayList<>();

    // Size Cell
    int countColumn;
    int countRow;

    private final ReadFileWord read;

    // все найденые слова
    private final List<String> allWord = new ArrayList<>();

    // сгенерированный квадрат из букв
    private char[][] cellLetter;

    // список из лучших сгенерированных букв
    private char[] betterArrayLetter;

    public GenerateSquare(ReadFileWord readFileWord, int countColumn, int countRow)
    {
        read = readFileWord;
        this.countColumn = countColumn;
        this.countRow = countRow;
    }

    public char[] maxCountGenerateBox()
    {
        betterAllWord.clear();
        long time = System.currentTimeMillis();
        
        // начало генерации вариантов
        for (int i = 0; i < maxIteration; i++)
        {
            generateBox();
            if (betterAllWord.size() >= minCountWord)
                break;
        }

        betterAllWord = betterAllWord.stream().sorted().collect(Collectors.toList());

        log.info("Count word = {}", betterAllWord.size());
        log.info("Time = {}", (System.currentTimeMillis()-time));

        return betterArrayLetter;
    }

    public void generateBox()
    {
        var countLetter = countColumn * countRow;
        var arrayRandomLetters = new char[countLetter];
        // заполнение квадрата алгоритмом схожим с генерацией кросворда
        fillCrosswordLettersArray(arrayRandomLetters);
        // преобразуем из одномерного в двумерный массив
        cellLetter = getCell(arrayRandomLetters);

        // поиск слов в сгенерированном поле
        findAllWord();

        if (allWord.size() > betterAllWord.size())
        {
            var s = new ArrayList<String>(allWord.size());
            s.addAll(allWord);
            betterAllWord = s;

            betterArrayLetter = new char[arrayRandomLetters.length];
            System.arraycopy(arrayRandomLetters,0, betterArrayLetter,0, arrayRandomLetters.length);
        }
    }

    char[][] getCell(char[] arrayLetters)
    {
        var cellChar = new char[countRow][countColumn];

        for (int i = 0; i < countRow; i++)
        {
            if (countColumn >= 0) {
                System.arraycopy(arrayLetters, i * countRow, cellChar[i], 0, countColumn);
            }
        }
        return cellChar;
    }

    private void fillCrosswordLettersArray(char[] arrayRandomLetters)
    {
        var Row = countRow;
        var Column = countColumn;
        var cell = new char[Row][Column];
        var iteration = 0;
        // пытаемся разместить рендомные слова в квадрате 100 раз
        do
        {
            var x = getRandomNumber(0, Row);
            var y = getRandomNumber(0, Column);

            fillWord(cell, new Vector2Int(x, y));

            iteration++;

        } while (iteration < 100);

        // заполняем пустые места
        for (int x = 0; x < Row; x++)
        {
            for (int y = 0; y < Column; y++)
            {
                if (cell[x][y]==('\0'))
                {
                    arrayRandomLetters[x * Row + y] = read.letters[getRandomNumber(0, read.letters.length)];
                }
                    else
                {
                    arrayRandomLetters[x * Row + y] = cell[x][y];
                }
            }
        }
    }

    // алгоритм очень простой и мало походит на алгоритм построения кроссворда
    // но работает достаточно эффективно
    private void fillWord(char[][] cell, Vector2Int position)
    {
        List<Vector2Int> posiiblePos = new ArrayList<>();
        Vector2Int currentPos = position;
        // выбираем слуучайное слово
        var str = read.charStrings.get(getRandomNumber(0, read.charStrings.size()));
        // попытка размещения слово в рендомном направлении
        for (int i = 0; i < str.length; i++)
        {
            if (cell[currentPos.x][currentPos.y]==('\0'))
            {
                posiiblePos.add(currentPos);
                currentPos = getPossiblePos(cell, currentPos);
                if (currentPos.equals(Vector2Int.Zero))
                {
                    return;
                }
            }
        }
        var j = 0;
        for(var pos : posiiblePos) {
            cell[pos.x][pos.y] = str[j];
            j++;
        };
    }

    // получаем возможное место расположения следующей буквы
    private Vector2Int getPossiblePos(char[][] cell, Vector2Int currentPos)
    {
        List<Vector2Int> direction = Lists.newArrayList(Vector2Int.Up, Vector2Int.Down, Vector2Int.Left, Vector2Int.Right);
        for (var dir : direction)
        {
            var posDir = Vector2Int.add(currentPos, dir);
            if (posDir.x >= 0 && posDir.x < cell.length)
            {
                if (posDir.y >= 0 && posDir.y < cell[0].length)
                {
                    if (cell[posDir.x][posDir.x]==('\0'))
                    {
                        return posDir;
                    }
                }
            }
        }
        return Vector2Int.Zero;
    }

    private void findAllWord()
    {
        allWord.clear();
        Map<Vector2Int, List<Vector2Int>> dict = new LinkedHashMap<>();

        for (int i = 0; i < countColumn; i++)
        {
            for (int j = 0; j < countRow; j++)
            {
                var point = new Vector2Int(i, j);
                dict.put(point, getNextStep(point, new Vector2Int(countColumn, countRow)));
            }
        }

        // запускаем алгоритм поиска всех путей из одной точки в другую
        var time = System.currentTimeMillis();
        dict.forEach((k,v) -> findPath(dict, k));
        log.info("Проход по дереву занял - {}",(System.currentTimeMillis() - time));
    }

    public void findPath(Map<Vector2Int, List<Vector2Int>> dict, Vector2Int start)
    {
        // помечаем пройденные клетки
        var marks = new LinkedHashMap<Vector2Int, Boolean>();
        // тут содержится текущий путь
        var path = new Stack<Vector2Int>();

        dict.forEach((k,v) -> marks.put(k, Boolean.FALSE));

        var count = new AtomicInteger(0);

        dict.forEach((k,v) ->
        {
            if (!k.equals(start))
            {
                dfs(dict, marks, start, k, count, path);
            }
        });
    }

    // алгоритм поиска в глубину направлен на поиск всевозможных путей из одной точки в другую
    public void dfs(Map<Vector2Int, List<Vector2Int>> dict, Map<Vector2Int, Boolean> marks,
                    Vector2Int currentPoint, Vector2Int target, AtomicInteger count, Stack<Vector2Int> path)
    {
        path.push(currentPoint);
        // цель достигнута
        if (currentPoint.equals(target))
        {
            count.incrementAndGet();
            path.pop();
            return;
        }

        Letter currentLetter = null;
        var currentWord = new ArrayList<Character>();

        for(var i : path)
        {
            // прекращаем поиск если видим что слов начинающихся с найденной последовательности символов не существует
            if (currentLetter == null)
            {
                if (read.treeWord.containsKey(cellLetter[i.x][i.y]))
                {
                    currentLetter = read.treeWord.get(cellLetter[i.x] [i.y]);
                    currentWord.add(currentLetter.symbol);
                }
                    else
                {
                    path.pop();
                    return;
                }
            }
            else
            {
                if (currentLetter.nextLetter.containsKey(cellLetter[i.x][i.y]))
                {
                    currentLetter = currentLetter.nextLetter.get(cellLetter[i.x][i.y]);
                    currentWord.add(currentLetter.symbol);
                }
                    else
                {
                    path.pop();
                    return;
                }
            }

            // если слово найдено то запоминаем его
            if (currentLetter != null && currentLetter.word != null)
            {
                // проверка на совпадение найденной последовательности со словом которе содержится в букве
                if (sequenceEqual(currentLetter.word, currentWord))
                {
                    var str = new String(currentLetter.word);
                    if (!allWord.contains(str))
                        allWord.add(str);
                }
            }
        }

        marks.put(currentPoint,true);
        for(var point : dict.get(currentPoint))
        {
            if (!marks.get(point))
            {
                dfs(dict, marks, point, target, count, path);
            }
        }
        marks.put(currentPoint, false);
        path.pop();
    }

    private static boolean sequenceEqual(char[] word, ArrayList<Character> aray) {
        if(word.length != aray.size()){
            return false;
        }
        for (int i = 0; i < word.length; i++) {
            if(word[i] != aray.get(i)){
                return false;
            }
        }
        return true;
    }

    // получаем возможные переходы из данной точки
    private List<Vector2Int> getNextStep(Vector2Int currentPos, Vector2Int sizeCell, List<Vector2Int> ignorPoints)
    {
        var result = new ArrayList<Vector2Int>();

        int[] xArray = new int[] { 1, 0, -1 };
        int[] yArray = new int[] { 1, 0, -1 };

        for(var x : xArray)
        {
            for(var y : yArray)
            {
                if (currentPos.x + x >= 0 && currentPos.x + x < sizeCell.x)
                {
                    if (currentPos.y + y >= 0 && currentPos.y + y < sizeCell.y)
                    {
                        if (!(x == 0 && y == 0))
                        {
                            var v = new Vector2Int(currentPos.x + x, currentPos.y + y);
                            if (!ignorPoints.contains(v))
                                result.add(v);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<Vector2Int> getNextStep(Vector2Int currentPos, Vector2Int sizeCell)
    {
        return getNextStep(currentPos, sizeCell, new ArrayList<>());
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
