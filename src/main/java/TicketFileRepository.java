import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TicketFileRepository {

    public void saveTickets(
            String fileName,
            ArrayList<Ticket> tickets
    ) throws IOException {

        try (BufferedWriter writer =
                new BufferedWriter(
                    new FileWriter(fileName)
                )) {

            for (Ticket ticket : tickets) {

                String technicianID = "NONE";
                String technicianName = "NONE";
                String technicianEmail = "NONE";

                if (ticket.getAssignedTechnician() != null) {
                    technicianID =
                        ticket.getAssignedTechnician()
                            .getUserID();

                    technicianName =
                        ticket.getAssignedTechnician()
                            .getName();

                    technicianEmail =
                        ticket.getAssignedTechnician()
                            .getEmail();
                }

                String ticketData =
                    ticket.getTicketID() + "|"
                    + ticket.getTitle() + "|"
                    + ticket.getDescription() + "|"
                    + ticket.getRequester().getUserID() + "|"
                    + ticket.getRequester().getName() + "|"
                    + ticket.getRequester().getEmail() + "|"
                    + ticket.getPriority() + "|"
                    + ticket.getStatus() + "|"
                    + technicianID + "|"
                    + technicianName + "|"
                    + technicianEmail;

                writer.write(ticketData);
                writer.newLine();
            }
        }
    }

    public ArrayList<Ticket> loadTickets(
        String fileName
        ) throws IOException {

    ArrayList<Ticket> loadedTickets =
        new ArrayList<>();

    try (BufferedReader reader =
            new BufferedReader(
                new FileReader(fileName)
            )) {

        String line;

        while ((line = reader.readLine()) != null) {
    String[] parts = line.split("\\|", -1);

    if (parts.length != 11) {
        throw new IOException(
            "Invalid ticket data: expected 11 fields but found "
            + parts.length
        );
    }

    String ticketID = parts[0];
    String title = parts[1];
    String description = parts[2];

    Requester requester = new Requester(
        parts[3],
        parts[4],
        parts[5]
    );

    TicketPriority priority =
        TicketPriority.valueOf(parts[6]);

    TicketStatus status =
        TicketStatus.valueOf(parts[7]);

    Technician technician = null;

    if (!parts[8].equals("NONE")) {
        technician = new Technician(
            parts[8],
            parts[9],
            parts[10]
        );
    }

    Ticket loadedTicket = new Ticket(
        ticketID,
        title,
        description,
        requester,
        priority,
        status,
        technician
    );

    loadedTickets.add(loadedTicket);
}
    }

    return loadedTickets;
    }
}