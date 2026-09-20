package dev.lukasdesantis.day.one;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Class containing implementation of advent-of-code-2023 exercises day one.
 */
public class ExerciseDayOne {
  private static final Pattern NUMERIC_VALUE_PATTERN = Pattern.compile("\\d+");

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
    try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource("exercise-one-actual-input.txt").toURI()))) {
      AtomicInteger totalOfCoordinates = new AtomicInteger(0);
    
      lines.forEach(line -> {
        String numericLiteralsFromLine = parseNumericLiteralsFromInput(line);
        int coordinate = getFirstAndLastNumericLiteralFromInput(numericLiteralsFromLine.split(""));
        totalOfCoordinates.addAndGet(coordinate);
      });

      return totalOfCoordinates.get();
    }
  }
}
