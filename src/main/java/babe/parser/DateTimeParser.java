package babe.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import babe.exception.BabeException;

public class DateTimeParser {
    private static final List<DateTimeFormatter> INPUT_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HHmm")
    );

    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Parses a date-time string into LocalDateTime.
     * Accepts multiple formats:
     * - yyyy-MM-dd HHmm (e.g., "2024-02-15 1430")
     * - d/M/yyyy HHmm (e.g., "15/2/2024 1430")
     * - d-M-yyyy HHmm (e.g., "15-2-2024 1430")
     */
    public static LocalDateTime parse(String dateTimeStr) throws BabeException {
        assert dateTimeStr != null : "Input date-time string cannot be null"; // Ensure input is not null
        String cleanedStr = dateTimeStr.trim();
        assert !cleanedStr.isEmpty() : "Input date-time string cannot be empty after trimming"; // Ensure input is not empty

        for (DateTimeFormatter formatter : INPUT_FORMATTERS) {
            try {
                LocalDateTime parsedDateTime = LocalDateTime.parse(cleanedStr, formatter);
                assert parsedDateTime != null : "Parsed LocalDateTime should not be null"; // Ensure parsing succeeded
                return parsedDateTime;
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        throw new BabeException(
                "Please enter date and time in one of these formats:\n" +
                        "- yyyy-MM-dd HHmm (e.g., 2024-02-15 1430)\n" +
                        "- d/M/yyyy HHmm (e.g., 15/2/2024 1430)\n" +
                        "- d-M-yyyy HHmm (e.g., 15-2-2024 1430)"
        );
    }

    /**
     * Formats a LocalDateTime for babe.storage
     */
    public static String format(LocalDateTime dateTime) {
        assert dateTime != null : "Input LocalDateTime cannot be null"; // Ensure input is not null
        String formattedDateTime = dateTime.format(STORAGE_FORMATTER);
        assert !formattedDateTime.isEmpty() : "Formatted date-time string should not be empty"; // Ensure formatting succeeded
        return formattedDateTime;
    }
}