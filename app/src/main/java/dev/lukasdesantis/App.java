package dev.lukasdesantis;

import dev.lukasdesantis.day.one.ExerciseDayOne;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {
      int extractedValue = ExerciseDayOne.calculateTotalOfCoordinates();
      System.out.println(extractedValue);
    }
}
