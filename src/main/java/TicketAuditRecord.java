import  java.time.LocalDateTime;

public class TicketAuditRecord {
    private String ticketID;
    private String action;
    private LocalDateTime occuredAt;


    public TicketAuditRecord (String ticketID, String action) {
        this.ticketID = ticketID;
        this.action = action;
        this.occuredAt = LocalDateTime.now();
    }

    @Override 
    public String toString() {
        return occuredAt + " | " + ticketID + " | " + action;
    }
}
