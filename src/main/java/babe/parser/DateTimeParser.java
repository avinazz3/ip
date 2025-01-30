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
        String cleanedStr = dateTimeStr.trim();

        for (DateTimeFormatter formatter : INPUT_FORMATTERS) {
            try {
                return LocalDateTime.parse(cleanedStr, formatter);
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
        return dateTime.format(STORAGE_FORMATTER);
    }
}