package com.bobocode.se;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.regex.Pattern;

/**
 * {@link CrazyRegex} is an exercise class. Each method returns Pattern class which
 * should be created using regex expression. Every method that is not implemented yet
 * throws {@link ExerciseNotCompletedException}
 * <p>
 * TODO: remove exception and implement each method of this class using {@link Pattern}
 *
 * @author Andriy Paliychuk
 */
public class CrazyRegex {

    /**
     * A Pattern that that finds all words "Curiosity" in text
     *
     * @return a pattern that looks for the word "Curiosity"
     */
    public Pattern findSpecificWord() {
        // Word boundaries keep Curiosity from matching inside a longer word.
        return Pattern.compile("\\bCuriosity\\b");
    }

    /**
     * A Pattern that finds first word in text
     *
     * @return a pattern that looks for the first word in text
     */
    public Pattern findFirstWord() {
        // Look behind for the start of the text and any leading nonletters.
        return Pattern.compile("(?<=\\A[^\\p{L}]*)\\p{L}+\\b");
    }

    /**
     * A Pattern that finds last word in text
     *
     * @return a pattern that looks for the last word in text
     */
    public Pattern findLastWord() {
        // Match a word only if there are no more letters before the end.
        return Pattern.compile("\\b\\p{L}+\\b(?=[^\\p{L}]*\\z)");
    }

    /**
     * A Pattern that finds all numbers in text. When we have "555-555", "(555)555" and "30th" in text
     * our pattern must grab all that numbers:
     * "555" - four times, and one "30"
     *
     * @return a pattern that looks for numbers
     */
    public Pattern findAllNumbers() {
        // Match each run of digits, including digits attached to letters.
        return Pattern.compile("\\d+");
    }

    /**
     * A Pattern that finds all dates. For instance: "1971-11-23"
     *
     * @return a pattern that looks for dates
     */
    public Pattern findDates() {
        // Match the yyyy-MM-dd shape with four, two, and two digits.
        return Pattern.compile("(?<!\\d)\\d{4}-\\d{2}-\\d{2}(?!\\d)");
    }

    /**
     * A Pattern that finds different variations of word "color".
     * We are looking for: "color", "colour", "colors", "colours"
     *
     * @return a pattern that looks for different variations of word "color"
     */
    public Pattern findDifferentSpellingsOfColor() {
        // The u and the plural s are both optional.
        return Pattern.compile("\\bcolou?rs?\\b");
    }

    /**
     * A Pattern that finds all zip codes in text.
     * Zip code is a 5-digit number without any characters or special symbols.
     * For example: 72300
     *
     * @return a pattern that looks for zip codes
     */
    public Pattern findZipCodes() {
        // Include the surrounding spaces because the test checks the full match.
        return Pattern.compile(" \\d{5} ");
    }

    /**
     * A Pattern that finds different variations of word "link".
     * We are looking for: "lynk", "link", "l nk", "l(nk"
     *
     * @return a pattern that looks for different variations of word "link"
     */
    public Pattern findDifferentSpellingsOfLink() {
        // The second character can be i, y, a space, or an opening parenthesis.
        return Pattern.compile("\\bl[iy (]nk\\b");
    }

    /**
     * A Pattern that finds phone numbers.
     * For example: "555-555-5555"
     *
     * @return a pattern that looks for phone numbers
     */
    public Pattern findSimplePhoneNumber() {
        // Match three digits, three digits, and four digits separated by hyphens.
        return Pattern.compile("(?<!\\d)\\d{3}-\\d{3}-\\d{4}(?!\\d)");
    }

    /**
     * A Pattern that finds numbers with following requirements:
     * - inside the number can be only digits from 0 to 5
     * - length 3
     *
     * @return a pattern that looks for numbers with length 3 and digits from 0 to 5 in the middle
     */
    public Pattern findNumbersFromZeroToFiveWithLengthThree() {
        // Match triples of digits from 0 to 5, including triples inside longer numbers.
        return Pattern.compile("[0-5]{3}");
    }

    /**
     * A Pattern that finds all words in text that have length 5
     *
     * @return a pattern that looks for the words that have length 5
     */
    public Pattern findAllWordsWithFiveLength() {
        // Require a whole word with exactly five letters.
        return Pattern.compile("\\b\\p{L}{5}\\b");
    }

    /**
     * A Pattern that finds words and numbers with following constraints:
     * - not shorter than two symbols
     * - not longer than three symbols
     *
     * @return a pattern that looks for words and numbers that not shorter 2 and not longer 3
     */
    public Pattern findAllLettersAndDigitsWithLengthThree() {
        // Require a whole token with two or three letters or digits.
        return Pattern.compile("\\b[\\p{L}\\p{Nd}]{2,3}\\b");
    }

    /**
     * A Pattern that finds all words that begin with capital letter
     *
     * @return a pattern that looks for the words that begin with capital letter
     */
    public Pattern findAllWordsWhichBeginWithCapitalLetter() {
        // Match a capitalized word with lowercase letters after its initial.
        return Pattern.compile("\\b\\p{Lu}\\p{Ll}+\\b");
    }

    /**
     * A Pattern that finds only the following abbreviation:
     * - AK, AL, AR, AZ, CA, CO, CT, PR, PA, PD
     *
     * @return a pattern that looks for the abbreviations above
     */
    public Pattern findAbbreviation() {
        // Match one of the listed abbreviations as a whole word.
        return Pattern.compile("\\b(?:AK|AL|AR|AZ|CA|CO|CT|PR|PA|PD)\\b");
    }

    /**
     * A Pattern that finds all open braces
     *
     * @return a pattern that looks for all open braces
     */
    public Pattern findAllOpenBraces() {
        // Group consecutive opening braces into one match.
        return Pattern.compile("\\{+");
    }

    /**
     * A Pattern that finds everything inside []
     *
     * @return a pattern that looks for everything inside []
     */
    public Pattern findOnlyResources() {
        // Check for square brackets around the resource without including them.
        return Pattern.compile("(?<=\\[)[^\\[\\]]+(?=\\])");
    }

    /**
     * A Pattern that finds all https links in note.txt
     *
     * @return a pattern that looks for all https links in note.txt
     */
    public Pattern findOnlyLinksInNote() {
        // Stop the HTTPS match before whitespace or a closing delimiter.
        return Pattern.compile("https://[^\\s<>\"\\])]+");
    }

    /**
     * A Pattern that finds all http links in nasa.json
     *
     * @return a pattern that looks for all http links in nasa.json
     */
    public Pattern findOnlyLinksInJson() {
        // Stop the HTTP match before the closing JSON quote or whitespace.
        return Pattern.compile("http://[^\"\\s]+");
    }

    /**
     * A Pattern that finds all .com, .net and .edu emails
     *
     * @return a pattern that looks for all .com, .net and .edu emails
     */
    public Pattern findAllEmails() {
        // Match supported email endings and avoid partial matches in longer domains.
        return Pattern.compile("[\\w.%+\\-]+@[A-Za-z0-9.-]+\\.(?:com|net|edu)(?![\\w-]|\\.[\\w-])");
    }

    /**
     * A Pattern that finds the following examples of phone numbers:
     * -  555-555-5555
     * -  555.555.5555
     * -  (555)555-5555
     *
     * @return a pattern that looks for phone numbers patterns above
     */
    public Pattern findAllPatternsForPhoneNumbers() {
        // Use a backreference so a plain phone number repeats the same separator.
        return Pattern.compile("(?<!\\w)(?:\\(\\d{3}\\)\\d{3}-\\d{4}|\\d{3}([.-])\\d{3}\\1\\d{4})(?!\\w)");
    }

    /**
     * A Pattern that finds only duplicates
     *
     * @return a pattern that looks for duplicates
     */
    public Pattern findOnlyDuplicates() {
        // Capture the first word and use a backreference to match it again.
        return Pattern.compile("\\b(\\w+)\\s+\\1\\b");
    }

    /**
     * You have a text where all names recorded as first name, last name.
     * Create matcher and use method replaceAll to record that names as:
     * - last name first name
     *
     * @return String where all names recorded as last name first name
     */
    public String replaceFirstAndLastNames(String names) {
        // Capture the last name and first name, then reverse their order.
        return Pattern.compile("\\b(\\p{L}+),\\h*(\\p{L}+)\\b")
                .matcher(names)
                .replaceAll("$2 $1");
    }

    /**
     * You have a text with phone numbers.
     * Create matcher and use method replaceAll to replace last digits:
     * -  555-XXX-XXXX
     *
     * @return String where in all phone numbers last 7 digits replaced to X
     */
    public String replaceLastSevenDigitsOfPhoneNumberToX(String phones) {
        // Capture the area code in either format and replace the other digits.
        return Pattern.compile("(?<!\\w)(?:\\((\\d{3,4})\\)\\d{3}-\\d{4}|"
                        + "(\\d{3,4})([.-])\\d{3}\\3\\d{4})(?!\\w)")
                .matcher(phones)
                .replaceAll("$1$2-XXX-XXXX");
    }

    /**
     * You have a text with resources and links to those resources:
     * - [Bobocode](https://www.bobocode.com)
     * Create matcher and use method replaceAll to get the following result:
     * - <a href="https://www.bobocode.com">Bobocode</a>
     *
     * @return String where all resources embraced in href
     */
    public String insertLinksAndResourcesIntoHref(String links) {
        // Group 1 is the label and group 2 is the URL.
        return Pattern.compile("\\[([^\\[\\]\\r\\n]+)\\]\\(([^\\s()]+)\\)")
                .matcher(links)
                .replaceAll("<a href=\"$2\">$1</a>");
    }
}