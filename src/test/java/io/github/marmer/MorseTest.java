package io.github.marmer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MorseTest {
    private static MorseConverter testedClass;

    @BeforeAll
    static void setup() throws Exception {
        var characters = Files.readAllLines(Path.of(MorseTest.class.getClassLoader().getResource("morse.dict").toURI()));
        testedClass = new MorseConverter(characters);
    }

    // Exercise 1
    @Test
    void emptyStringShouldBeTranslatedToEmptyString() {
        String word = "";

        var result = testedClass.toMorse(word);

        assertThat(result).hasSize(0);
    }

    @Test
    void stringWithOneLetterShouldReturnOneMorseLetter() {
        String word = "A";

        var result = testedClass.toMorse(word);

        assertThat(result).isEqualTo(".-");
    }

    @Test
    void stringContainingWordShouldBeTranslatedToMorseWord() {
        String word = "The";

        var result = testedClass.toMorse(word);

        assertThat(result).isEqualTo("- .... .");
    }

    @Test
    void stringContainsInvalidCharactersShouldReturnInvalidMorse() {
        String word = "#";

        var result = testedClass.toMorse(word);

        assertThat(result).isEqualTo("..--..");
    }

    @Test
    void  stringContainingSentenceShouldReturnValidMorse() {
        String word = "The Pug";

        var result = testedClass.toMorse(word);

        assertThat(result).isEqualTo("- .... .   .--. ..- --.");
    }

    // Exercise 2
    @Test
    void emptyMorseStringShouldTranslatedToEmptyString() {
        String morseString = "";
        var translatedString = testedClass.fromMorse(morseString);
        assertThat(translatedString).isEmpty();
    }

    @Test
    void oneMorseCharacterShouldTranslatedToOneLetter() {
        String morseString = "-";
        String translatedString = testedClass.fromMorse(morseString);
        assertThat(translatedString)
                .describedAs("Translated String")
                .isEqualTo("T");
    }

    @Test
    void oneWordOfMorseCodeShouldTranslatedToWord() {
        String morseString = "- .... .";
        String translatedString = testedClass.fromMorse(morseString);
        assertThat(translatedString)
                .describedAs("Translated String")
                .isEqualTo("THE");
    }

    @Test
    void invalidMorseCodeShouldTranslateToInvalid() {
        String morse = "..-.-...";

        String translatedString = testedClass.fromMorse(morse);

        assertThat(translatedString)
                .describedAs("Translated String")
                .isEqualTo("?");
    }

    @Test
    void testInvalidCharacterWithValidCharactersInMorseShouldTranslateToPartlyValid() {
        String morse = ". .-..--. -";

        String translatedString = testedClass.fromMorse(morse);

        assertThat(translatedString)
                .isEqualTo("E?T");
    }

    @Test
    void testWhitespacesAtStartOfMorseWordShouldReturnValidWord() {
        String morse = "     . -";

        String translatedString = testedClass.fromMorse(morse);

        assertThat(translatedString)
                .isEqualTo("ET");
    }

    @Test
    void testMorseSentenceShouldReturnValidSentence() {
        String morse = "- .... .   .--. ..- --.";

        String translatedString = testedClass.fromMorse(morse);

        assertThat(translatedString)
                .isEqualTo("THE PUG");
    }

    // TODO: 17.11.2023 Ask PM how this should be handled
    @Test
    @Disabled
    void testMorseLettersSeperatedByTwoSpacesShould() {
        String morse = "-  .";

        String translatedString = testedClass.fromMorse(morse);

        assertThat(translatedString)
                .isEqualTo("TE");
    }
}
