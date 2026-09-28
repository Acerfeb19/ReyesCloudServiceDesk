import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TicketTest {

    @Test
    void newTicket_startsAsPending() {
        Requester requester = new Requester(
            "USR-101",
            "Antonio",
            "antonio@example.com"
        );

        Ticket ticket = new Ticket(
            "RC-101",
            "Cloud storage unavailable",
            "The shared drive cannot be accessed.",
            requester,
            TicketPriority.LOW
        );

        assertEquals(
            TicketStatus.PENDING,
            ticket.getStatus()
        );
    }

    @Test
void ticket_validLifecycle_endsAsResolved() {
    Requester requester = new Requester(
        "USR-101",
        "Antonio",
        "antonio@example.com"
    );

    Ticket ticket = new Ticket(
        "RC-101",
        "Cloud storage unavailable",
        "The shared drive cannot be accessed.",
        requester,
        TicketPriority.HIGH
    );

    ticket.startWork();
    ticket.placeOnHold();
    ticket.resumeWork();
    ticket.resolve();

    assertEquals(
        TicketStatus.RESOLVED,
        ticket.getStatus()
    );
}
@Test
void resolve_whenTicketIsPending_throwsException() {
    Requester requester = new Requester(
        "USR-101",
        "Antonio",
        "antonio@example.com"
    );

    Ticket ticket = new Ticket(
        "RC-101",
        "Cloud storage unavailable",
        "The shared drive cannot be accessed.",
        requester,
        TicketPriority.LOW
    );

    assertThrows(
        InvalidTicketStateException.class,
        () -> ticket.resolve()
    );
    
}
}