package dev.lukasdesantis.day.one;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Class containing implementation of advent-of-code-2023 exercises day one.
 */
public class ExerciseDayOne {
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile("\\d+");
  private static final Map<String, String> NUMERIC_LOOKUP_TABLE = new LinkedHashMap<>(){{
    put("one", "1");
    put("two", "2");
    put("three", "3");
    put("four", "4");
    put("five", "5");
    put("six", "6");
    put("seven", "7");
    put("eight", "8");
    put("nine", "9");
  }};

  private ExerciseDayOne() {
    throw new UnsupportedOperationException("Utility Class");
  }

  /**
   * Replace occurences of numeric strings with numeric literals. e.g. one becomes 1 etc.
   * This method also handles string overlaps. e.g. eightwo becomes 82.
   *
   * @param input Input string containing numeric string representations.
   * @return String containing numeric literals based on string representations of those numbers.
   */
  private static String replaceNumericStringsWithNumericLiterals(String input) {
    
    Map<Integer, String> indexMap = new TreeMap<>();

    String newInput = input;
    for (Map.Entry<String, String> entry : NUMERIC_LOOKUP_TABLE.entrySet()) {
      int indexOfKey = 0;

      while(indexOfKey != -1) {
        indexOfKey = newInput.indexOf(entry.getKey(), indexOfKey);
        if (indexOfKey != -1) {
          indexMap.put(indexOfKey, entry.getKey());
          indexOfKey += 1;
        }
      }
    }

    int indexOffset = 0;
    for (Map.Entry<Integer, String> entry : indexMap.entrySet()) {
      newInput = new StringBuilder(newInput).insert(entry.getKey() + indexOffset, NUMERIC_LOOKUP_TABLE.get(entry.getValue())).toString();
      indexOffset += 1;
    }

    return newInput;
  }

  /**
   * Parse numeric literals from input string.
   * e.g. adb3skfj4kad9 parses out the following string 349.
   *
   * @param input Input string possibly containing numeric literals.
   * @return Parsed input string containing only numeric literals.
   */
  private static String parseNumericLiteralsFromInput(String input) {
   Matcher matcher = NUMERIC_VALUE_PATTERN.matcher(input);
   String numericValueString = "";

   while (matcher.find()) {
     numericValueString += matcher.group();
   }

   return numericValueString;
  }

  /**
   * Returns string containing the first and last occurence of numeric literals in input string.
   * If only one numeric literal is present in input string it gets repeated once. e.g. 1 becomes 11.
   *
   * @param input Input string array containing only numeric literals.
   * @return String containing first and last numeric literal from input.
   */
  private static int getFirstAndLastNumericLiteralFromInput(String[] input) {
    if (input.length == 0) {
      return 0;
    }

    if (input.length == 1) {
      if (input[0].isBlank()) {
        return 0;
      }
      return Integer.parseInt(input[0] + input[0]);
    }

    return Integer.parseInt(input[0] + input[input.length - 1]);
  }

  /**
   * Based on text file input calculate total of coordinates.
   *
   * @return Total of coordinates extracted from text file.
   */
  public static int calculateTotalOfCoordinates() throws IOException, URISyntaxException {
    try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource("exercise-two-actual-input.txt").toURI()))) {
      AtomicInteger totalOfCoordinates = new AtomicInteger(0);
    
      lines.forEach(line -> {
        String parsedNumericLiterals = replaceNumericStringsWithNumericLiterals(line);
        String numericLiteralsFromLine = parseNumericLiteralsFromInput(parsedNumericLiterals);
        int coordinate = getFirstAndLastNumericLiteralFromInput(numericLiteralsFromLine.split(""));
        totalOfCoordinates.addAndGet(coordinate);
      });

      return totalOfCoordinates.get();
    }
  }
}
