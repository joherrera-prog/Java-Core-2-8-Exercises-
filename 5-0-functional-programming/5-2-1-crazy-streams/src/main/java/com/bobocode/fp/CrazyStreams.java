package com.bobocode.fp;

import com.bobocode.fp.exception.EntityNotFoundException;
import com.bobocode.model.Account;
import com.bobocode.model.Sex;
import com.bobocode.util.ExerciseNotCompletedException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link CrazyStreams} is an exercise class. Each method represent some operation with a collection of accounts that
 * should be implemented using Stream API. Every method that is not implemented yet throws
 * {@link ExerciseNotCompletedException}.
 * <p>
 * TODO: remove exception throwing and implement each method using Stream API
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com">visit our website</a></strong>
 * <p>
 *
 * @author Taras Boychuk
 */
@AllArgsConstructor
public class CrazyStreams {
    private Collection<Account> accounts;

    /**
     * Returns {@link Optional} that contains an {@link Account} with the max value of balance
     *
     * @return account with max balance wrapped with optional
     */
    public Optional<Account> findRichestPerson() {
        // Compare balances and keep the account with the highest one.
        return accounts.stream()
                .max(Comparator.comparing(Account::getBalance));
    }

    /**
     * Returns a {@link List} of {@link Account} that have a birthday month equal to provided.
     *
     * @param birthdayMonth a month of birth
     * @return a list of accounts
     */
    public List<Account> findAccountsByBirthdayMonth(Month birthdayMonth) {
        // Keep only the accounts whose birthday falls in the requested month.
        return accounts.stream()
                .filter(account -> account.getBirthday().getMonth() == birthdayMonth)
                .toList();
    }

    /**
     * Returns a map that separates all accounts into two lists - male and female. Map has two keys {@code true} indicates
     * male list, and {@code false} indicates female list.
     *
     * @return a map where key is true or false, and value is list of male, and female accounts
     */
    public Map<Boolean, List<Account>> partitionMaleAccounts() {
        // Partitioning creates both groups, even if one is empty.
        return accounts.stream()
                .collect(Collectors.partitioningBy(account -> account.getSex() == Sex.MALE));
    }

    /**
     * Returns a {@link Map} that stores accounts grouped by its email domain. A map key is {@link String} which is an
     * email domain like "gmail.com". And the value is a {@link List} of {@link Account} objects with a specific email domain.
     *
     * @return a map where key is an email domain and value is a list of all account with such email
     */
    public Map<String, List<Account>> groupAccountsByEmailDomain() {
        // Extract the domain and use it as the key for each group.
        return accounts.stream()
                .collect(Collectors.groupingBy(account -> emailDomainOf(account.getEmail())));
    }

    /**
     * Returns a number of letters in all first and last names.
     *
     * @return total number of letters of first and last names of all accounts
     */
    public int getNumOfLettersInFirstAndLastNames() {
        // Convert each account into the combined length of its two names.
        return accounts.stream()
                .mapToInt(account -> account.getFirstName().length() + account.getLastName().length())
                .sum();
    }

    /**
     * Returns a total balance of all accounts.
     *
     * @return total balance of all accounts
     */
    public BigDecimal calculateTotalBalance() {
        // Start at zero and add each balance with BigDecimal arithmetic.
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Returns a {@link List} of {@link Account} objects sorted by first and last names.
     *
     * @return list of accounts sorted by first and last names
     */
    public List<Account> sortByFirstAndLastNames() {
        // Use the last name to break ties between matching first names.
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getFirstName)
                        .thenComparing(Account::getLastName))
                .toList();
    }

    /**
     * Checks if there is at least one account with provided email domain.
     *
     * @param emailDomain
     * @return true if there is an account that has an email with provided domain
     */
    public boolean containsAccountWithEmailDomain(String emailDomain) {
        // Stop as soon as an account matches the exact domain.
        return accounts.stream()
                .anyMatch(account -> emailDomainOf(account.getEmail()).equals(emailDomain));
    }

    /**
     * Returns account balance by its email. Throws {@link EntityNotFoundException} with message
     * "Cannot find Account by email={email}" if account is not found.
     *
     * @param email account email
     * @return account balance
     */
    public BigDecimal getBalanceByEmail(String email) {
        // Take the first matching account and extract its balance.
        return accounts.stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst()
                .map(Account::getBalance)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Account by email=" + email));
    }

    /**
     * Collects all existing accounts into a {@link Map} where a key is account id, and the value is {@link Account} instance
     *
     * @return map of accounts by its ids
     */
    public Map<Long, Account> collectAccountsById() {
        // Use the ID as the key and the original account as the value.
        return accounts.stream()
                .collect(Collectors.toMap(Account::getId, Function.identity()));
    }

    /**
     * Filters accounts by the year when an account was created. Collects account balances by its emails into a {@link Map}.
     * The key is {@link Account#email} and the value is {@link Account#balance}
     *
     * @param year the year of account creation
     * @return map of account by its ids the were created in a particular year
     */
    public Map<String, BigDecimal> collectBalancesByEmailForAccountsCreatedOn(int year) {
        // Filter by creation year before building the email-to-balance map.
        return accounts.stream()
                .filter(account -> account.getCreationDate().getYear() == year)
                .collect(Collectors.toMap(Account::getEmail, Account::getBalance));
    }

    /**
     * Returns a {@link Map} where key is {@link Account#lastName} and values is a {@link Set} that contains first names
     * of all accounts with a specific last name.
     *
     * @return a map where key is a last name and value is a set of first names
     */
    public Map<String, Set<String>> groupFirstNamesByLastNames() {
        // Collect only the distinct first names within each last-name group.
        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getLastName,
                        Collectors.mapping(Account::getFirstName, Collectors.toSet())));
    }

    /**
     * Returns a {@link Map} where key is a birthday month, and value is a {@link String} that stores comma and space
     * -separated first names (e.g. "Polly, Dylan, Clark"), of all accounts that have the same birthday month.
     *
     * @return a map where a key is a birthday month and value is comma-separated first names
     */
    public Map<Month, String> groupCommaSeparatedFirstNamesByBirthdayMonth() {
        // For each birthday month, join the first names in encounter order.
        return accounts.stream()
                .collect(Collectors.groupingBy(account -> account.getBirthday().getMonth(),
                        Collectors.mapping(Account::getFirstName, Collectors.joining(", "))));
    }

    /**
     * Returns a {@link Map} where key is a {@link Month} of {@link Account#creationDate}, and value is total balance
     * of all accounts that have the same value creation month.
     *
     * @return a map where key is a creation month and value is total balance of all accounts created in that month
     */
    public Map<Month, BigDecimal> groupTotalBalanceByCreationMonth() {
        // Within each creation month, extract and add the balances.
        return accounts.stream()
                .collect(Collectors.groupingBy(account -> account.getCreationDate().getMonth(),
                        Collectors.mapping(Account::getBalance,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences in
     * {@link Account#firstName}.
     *
     * @return a map where key is a letter and value is its count in all first names
     */
    public Map<Character, Long> getCharacterFrequencyInFirstNames() {
        // Flatten the first names into characters and count each character.
        return accounts.stream()
                .map(Account::getFirstName)
                .flatMapToInt(String::chars)
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences ignoring
     * case, in all {@link Account#firstName} and {@link Account#lastName} that are equal or longer than nameLengthBound.
     * Inside the map, all letters should be stored in lower case.
     *
     * @return a map where key is a letter and value is its count ignoring case in all first and last names
     */
    public Map<Character, Long> getCharacterFrequencyIgnoreCaseInFirstAndLastNames(int nameLengthBound) {
        // Filter each name separately, then lowercase it before counting its characters.
        return accounts.stream()
                .flatMap(account -> Stream.of(account.getFirstName(), account.getLastName()))
                .filter(name -> name.length() >= nameLengthBound)
                .map(name -> name.toLowerCase(Locale.ROOT))
                .flatMapToInt(String::chars)
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static String emailDomainOf(String email) {
        // The domain is the part after the last @.
        return email.substring(email.lastIndexOf('@') + 1);
    }
}