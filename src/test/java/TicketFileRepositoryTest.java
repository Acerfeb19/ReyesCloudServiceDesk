import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TicketFileRepositoryTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void saveAndLoadTickets_preservesTicketData()
            throws IOException {

        Requester requester = new Requester(
            "USR-101",
            "Antonio",
            "antonio@example.com"
        );

        Technician technician = new Technician(
            "TECH-101",
            "Maya",
            "maya@example.com"
        );

        Ticket originalTicket = new Ticket(
            "RC-101",
            "Storage unavailable",
            "Cannot access storage.",
            requester,
            TicketPriority.HIGH
        );

        originalTicket.assignTechnician(technician);
        originalTicket.startWork();

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(originalTicket);

        String fileName = temporaryDirectory
            .resolve("tickets.txt")
            .toString();

        TicketFileRepository repository =
            new TicketFileRepository();

        repository.saveTickets(fileName, tickets);

        ArrayList<Ticket> loadedTickets =
            repository.loadTickets(fileName);

        Ticket loadedTicket = loadedTickets.get(0);

        assertEquals(1, loadedTickets.size());
        assertEquals("RC-101", loadedTicket.getTicketID());
        assertEquals(
            TicketStatus.IN_PROGRESS,
            loadedTicket.getStatus()
        );
        assertEquals(
            "Antonio",
            loadedTicket.getRequester().getName()
        );
        assertEquals(
            "Maya",
            loadedTicket.getAssignedTechnician().getName()
        );
    }
}