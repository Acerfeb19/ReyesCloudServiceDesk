import java.util.ArrayList;

public class Ticket {
    private String ticketID;
    private String title;
    private String description;
    private Requester requester;
    private TicketStatus status;
    private TicketPriority priority;
    private Technician assignedTechnician;
    private ArrayList<TicketAuditRecord> auditHistory; 

    public Ticket(
        String ticketID,
        String title,
        String description,
        Requester requester,
        TicketPriority priority
) {
    this.ticketID = ticketID;
    this.title = title;
    this.description = description;
    this.requester = requester;
    this.priority = priority;

    this.status = TicketStatus.PENDING;
    this.assignedTechnician = null;
    this.auditHistory = new ArrayList<>();

    auditHistory.add(
        new TicketAuditRecord(ticketID, "Ticket created")
    );
}


// Reconstructs a ticket loaded from the file
public Ticket(
        String ticketID,
        String title,
        String description,
        Requester requester,
        TicketPriority priority,
        TicketStatus status,
        Technician assignedTechnician
) {
    this.ticketID = ticketID;
    this.title = title;
    this.description = description;
    this.requester = requester;
    this.priority = priority;
    this.status = status;
    this.assignedTechnician = assignedTechnician;
    this.auditHistory = new ArrayList<>();

    auditHistory.add(
        new TicketAuditRecord(
            ticketID,
            "Ticket loaded from file"
        )
    );
}

    @Override 
    public String toString(){
        return ticketID + " | " + title + " | " + priority  + " | " + status;
    }

    public void startWork() {
        if(status != TicketStatus.PENDING) {
            throw new InvalidTicketStateException(
            "Ticket must be PENDING before work can begin."
        );
    }   
        status = TicketStatus.IN_PROGRESS;

        auditHistory.add(
            new TicketAuditRecord(ticketID, "Work started")
        );
    }

    public void resolve() {
        if(status != TicketStatus.IN_PROGRESS) {
            throw new InvalidTicketStateException("Ticket must be in Progress before it can be resolved");
        } 

        status = TicketStatus.RESOLVED;

        auditHistory.add(new TicketAuditRecord(ticketID, "Ticket Resolved"));
    }

    public void resumeWork() {
        if(status != TicketStatus.ON_HOLD) {
            throw new InvalidTicketStateException("Ticket must be on hold before it can be in Progress.");  
        }

        status = TicketStatus.IN_PROGRESS;

        auditHistory.add(new TicketAuditRecord(ticketID, "Work resumed"));
    }

    public void placeOnHold() {
        if(status != TicketStatus.IN_PROGRESS) {
            throw new InvalidTicketStateException("Ticket must be in Progress before it can be on hold");
        }

        status = TicketStatus.ON_HOLD;

        auditHistory.add(
        new TicketAuditRecord(
            ticketID,
            "Ticket placed on hold"
        )
    );
    }

    public void assignTechnician(Technician technician) {
        if( technician == null) {
            throw new IllegalArgumentException("Technician Cannot be null");
        }
        this.assignedTechnician = technician;

        auditHistory.add(
            new TicketAuditRecord(ticketID, "Technician assigned: " + technician.getName())
        );
    }

    public String getTicketID(){
        return ticketID;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Requester getRequester(){
        return requester;
    }

    public TicketStatus getStatus(){
        return status;
    }

    public TicketPriority getPriority(){
        return priority;
    }

    public Technician getAssignedTechnician() {
        return assignedTechnician;
    }

    public ArrayList<TicketAuditRecord> getAuditHistory(){
        return new ArrayList<>(auditHistory);
    }
}
