import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Create system users
        Requester requester = new Requester(
            "USR-101",
            "Antonio",
            "antonio@example.com"
        );

        Requester vanessaRequester = new Requester(
            "USR-102",
            "Vanessa",
            "vanessa@example.com"
        );

        Technician technician = new Technician(
            "TECH-101",
            "Maya",
            "maya@example.com"
        );

        // Demonstrate polymorphism
        User[] users = {
            requester,
            vanessaRequester,
            technician
        };

        System.out.println("=== System Users ===");

        for (User currentUser : users) {
            System.out.println(
                currentUser.getName()
                    + " | "
                    + currentUser.getRole()
            );
        }

        // Create tickets
        Ticket storageTicket = new Ticket(
            "RC-101",
            "Cannot access cloud storage",
            "The shared drive returns access denied.",
            requester,
            TicketPriority.LOW
        );

        Ticket emailTicket = new Ticket(
            "RC-102",
            "Email Unavailable",
            "The user cannot open their mailbox.",
            vanessaRequester,
            TicketPriority.MEDIUM
        );

        Ticket urgentEmailTicket = new Ticket(
            "RC-103",
            "Email Unavailable",
            "The executive mailbox is unavailable.",
            vanessaRequester,
            TicketPriority.HIGH
        );

        // Create service and register tickets
        TicketService ticketService = new TicketService();

        ticketService.addTicket(storageTicket);
        ticketService.addTicket(emailTicket);
        ticketService.addTicket(urgentEmailTicket);

        // Test a valid ticket lifecycle
        urgentEmailTicket.startWork();
        urgentEmailTicket.placeOnHold();
        urgentEmailTicket.resumeWork();
        urgentEmailTicket.resolve();

        // Test technician assignment
        storageTicket.assignTechnician(technician);

        System.out.println(
            storageTicket.getAssignedTechnician().getName()
        );

        try {
            storageTicket.assignTechnician(null);
        } catch (IllegalArgumentException exception) {
            System.out.println(
                "Error: " + exception.getMessage()
            );
        }

        System.out.println(
            storageTicket.getAssignedTechnician().getName()
        );

        // Display RC-101 audit history
        System.out.println("\n=== RC-101 Audit History ===");

        for (TicketAuditRecord record
                : storageTicket.getAuditHistory()) {
            System.out.println(record);
        }

        // Display all tickets
        System.out.println("\n=== All Tickets ===");

        for (Ticket currentTicket
                : ticketService.getAllTickets()) {
            System.out.println(currentTicket);
        }

        System.out.println(
            "Ticket count: "
                + ticketService.getTicketCount()
        );

        // Find one ticket by ID
        System.out.println("\n=== ID Lookup ===");

        Ticket foundTicket =
            ticketService.findTicketById("RC-103");

        System.out.println(foundTicket);

        // Search by title keyword
        System.out.println("\n=== Search Results ===");

        for (Ticket matchingTicket
                : ticketService.searchTickets("email")) {
            System.out.println(matchingTicket);
        }

        // Display tickets by priority
        System.out.println(
            "\n=== Tickets Sorted by Priority ==="
        );

        for (Ticket sortedTicket
                : ticketService.getTicketsSortedByPriority()) {
            System.out.println(sortedTicket);
        }

        // Display RC-103 audit history
        System.out.println("\n=== RC-103 Audit History ===");

        for (TicketAuditRecord record
                : urgentEmailTicket.getAuditHistory()) {
            System.out.println(record);
        }

        // Test priority-processing queue
        System.out.println("\n=== Processing Queue ===");

        ticketService.queueTicket("RC-101");
        ticketService.queueTicket("RC-102");

        Ticket nextTicket =
            ticketService.processNextTicket();

        System.out.println(nextTicket);

        Ticket secondTicket =
            ticketService.processNextTicket();

        System.out.println(secondTicket);

        try {
            ticketService.processNextTicket();
        } catch (NoTicketsAvailableException exception) {
            System.out.println(
                "Error: " + exception.getMessage()
            );
        }

        // Test invalid operations
        System.out.println("\n=== Error Handling ===");

        try {
            ticketService.findTicketById("RC-999");
        } catch (TicketNotFoundException exception) {
            System.out.println(
                "Error: " + exception.getMessage()
            );
        }

        try {
            ticketService.addTicket(storageTicket);
        } catch (DuplicateTicketException exception) {
            System.out.println(
                "Error: " + exception.getMessage()
            );
        }

        try {
            urgentEmailTicket.startWork();
        } catch (InvalidTicketStateException exception) {
            System.out.println(
                "Error: " + exception.getMessage()
            );
        }

        // Save and reload tickets
        System.out.println("\n=== File Persistence ===");

        TicketFileRepository repository =
            new TicketFileRepository();

        try {
            repository.saveTickets(
                "tickets.txt",
                ticketService.getAllTickets()
            );

            System.out.println(
                "Tickets saved successfully."
            );

            ArrayList<Ticket> loadedTickets =
                repository.loadTickets("tickets.txt");

            System.out.println("\n=== Loaded Tickets ===");

            for (Ticket loadedTicket : loadedTickets) {
    System.out.println(loadedTicket);

    System.out.println(
        "Requester: "
        + loadedTicket.getRequester().getName()
    );

    if (loadedTicket.getAssignedTechnician() != null) {
        System.out.println(
            "Technician: "
            + loadedTicket.getAssignedTechnician().getName()
        );
    } else {
        System.out.println("Technician: Unassigned");
    }

    System.out.println();
}

        } catch (IOException exception) {
            System.out.println(
                "File operation failed: "
                    + exception.getMessage()
            );
        }
    }
}