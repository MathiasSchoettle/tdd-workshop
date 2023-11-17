package io.github.marmer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MorseConverter {
    private final Map<Character, String> toMorseDictionary;
    private final Map<String, String> fromMorseDictionary;

    private static final String QUESTION_MARK_MORSE = "..--..";
    private static final String WORD_SEPARATOR_MORSE = "   ";
    private static final String LETTER_SEPARATOR_MORSE = " ";

    public MorseConverter(List<String> characters) {
        this.toMorseDictionary = characters.stream()
                .map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[0].charAt(0), arr -> arr[1]));

        this.fromMorseDictionary = characters.stream()
                .map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[1], arr -> arr[0]));
    }

    public String toMorse(String text) {
        return Arrays.stream(text.split(" "))
                .map(this::wordToMorse)
                .collect(Collectors.joining(WORD_SEPARATOR_MORSE));
    }

    private String wordToMorse(String word) {
        return word.chars()
                .mapToObj(i -> (char) i)
                .map(Character::toUpperCase)
                .map(c -> toMorseDictionary.getOrDefault(c, QUESTION_MARK_MORSE))
                .collect(Collectors.joining(LETTER_SEPARATOR_MORSE));
    }

    public String fromMorse(String morseString) {
        morseString = morseString.trim();

        if (morseString.isEmpty()) return "";

        return Arrays.stream(morseString.split("   "))
                .map(this::morseToWord)
                .collect(Collectors.joining(" "));
    }

    private String morseToWord(String morseWord) {
        return Arrays.stream(morseWord.split(" "))
                .map(fromMorseDictionary::get)
                .map(letter -> letter == null ? "?" : letter)
                .collect(Collectors.joining());
    }
}
