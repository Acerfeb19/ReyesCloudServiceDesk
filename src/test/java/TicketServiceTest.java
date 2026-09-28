import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketServiceTest {

    @Test
    void addTicket_whenIdAlreadyExists_throwsException() {
        Requester requester = new Requester(
            "USR-101",
            "Antonio",
            "antonio@example.com"
        );

        Ticket firstTicket = new Ticket(
            "RC-101",
            "Storage unavailable",
            "Cannot access storage.",
            requester,
            TicketPriority.LOW
        );

        Ticket duplicateTicket = new Ticket(
            "RC-101",
            "Different problem",
            "This ticket uses the same ID.",
            requester,
            TicketPriority.HIGH
        );

        TicketService ticketService = new TicketService();

        ticketService.addTicket(firstTicket);

        assertThrows(
            DuplicateTicketException.class,
            () -> ticketService.addTicket(duplicateTicket)
        );
    }
    @Test
void processNextTicket_returnsHighestPriorityFirst() {
    Requester requester = new Requester(
        "USR-101",
        "Antonio",
        "antonio@example.com"
    );

    Ticket lowPriorityTicket = new Ticket(
        "RC-101",
        "Minor issue",
        "Low-priority problem.",
        requester,
        TicketPriority.LOW
    );

    Ticket highPriorityTicket = new Ticket(
        "RC-102",
        "Major outage",
        "High-priority problem.",
        requester,
        TicketPriority.HIGH
    );

    TicketService ticketService = new TicketService();

    ticketService.addTicket(lowPriorityTicket);
    ticketService.addTicket(highPriorityTicket);

    // LOW enters first, but HIGH should still leave first.
    ticketService.queueTicket("RC-101");
    ticketService.queueTicket("RC-102");

    Ticket processedTicket =
        ticketService.processNextTicket();

    assertEquals(
        "RC-102",
        processedTicket.getTicketID()
    );
}
}