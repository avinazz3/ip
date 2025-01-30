package babe.parser;

import babe.task.Deadline;
import babe.exception.BabeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void createDeadline_validInput_success() throws BabeException {
        // Test valid deadline creation
        String input = "deadline submit report /by 2024-02-01 1430";
        Deadline deadline = (Deadline) Parser.createDeadline(input);

        assertEquals("submit report", deadline.getDescription());
        // Test the datetime was parsed correctly
        assertNotNull(deadline.getBy());
    }

    @Test
    public void createDeadline_emptyDescription_throwsBabeException() {
        // Test empty description with proper deadline format
        String input = "deadline  /by 2024-02-01 1430"; // Notice the space after deadline

        BabeException thrown = assertThrows(
                BabeException.class,
                () -> Parser.createDeadline(input)
        );

        assertTrue(thrown.getMessage().contains("description"));
    }

    @Test
    public void createDeadline_noDeadlineTime_throwsBabeException() {
        // Test missing /by section
        String input = "deadline submit report";

        BabeException thrown = assertThrows(
                BabeException.class,
                () -> Parser.createDeadline(input)
        );

        assertTrue(thrown.getMessage().contains("deadline"));
    }
}