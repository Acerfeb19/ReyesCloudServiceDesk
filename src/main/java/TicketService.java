import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TicketService {
    private ArrayList<Ticket> tickets;
    private HashMap<String, Ticket> ticketById;
    private PriorityQueue<Ticket> processingQueue;

    public TicketService() {
        tickets = new ArrayList<>();
        ticketById = new HashMap<>();

        processingQueue = new PriorityQueue<>(
            new TicketPriorityComparator()
        );
    }

    public void addTicket(Ticket ticket) {
        if(ticketById.containsKey(ticket.getTicketID())) {
            throw new DuplicateTicketException("Ticket ID already Exists: " + ticket.getTicketID());

        }

        tickets.add(ticket);
        ticketById.put(ticket.getTicketID(), ticket);
    }

    public void queueTicket(String ticketID) {
        Ticket ticket = findTicketById(ticketID);

        if(ticket.getStatus() != TicketStatus.PENDING) {
            throw new InvalidTicketStateException("Only pending tickets can enter the processing queue: " + ticketID);
        }

        processingQueue.offer(ticket);
    }

    public Ticket findTicketById(String ticketID) {
        Ticket foundTicket = ticketById.get(ticketID);

        if(foundTicket == null){
            throw new TicketNotFoundException("Ticket Not found: " + ticketID);
        }
        return foundTicket;
    }

    public Ticket processNextTicket() {
        Ticket nextTicket = processingQueue.poll();

        if(nextTicket == null){
            throw new NoTicketsAvailableException("No tickets are waiting for processing.");
        }
        nextTicket.startWork();
        return nextTicket;
    }

    public ArrayList<Ticket> getTicketsSortedByPriority() {
        ArrayList<Ticket> sortedTickets = new ArrayList<>(tickets);

        for(int i = 1; i < sortedTickets.size(); i++ ){
            Ticket currentTicket = sortedTickets.get(i);
            int j = i - 1;

            while (
                j >= 0 
                && sortedTickets.get(j).getPriority().ordinal()
                < currentTicket.getPriority().ordinal()
            ) {
                sortedTickets.set(j + 1, sortedTickets.get(j));
                j--;
            }

            sortedTickets.set(j + 1, currentTicket);
        }
        return sortedTickets;
    }

    public ArrayList<Ticket> searchTickets(String keyword) {
    ArrayList<Ticket> matches = new ArrayList<>();

        for (Ticket currTicket : tickets) {
            if (
                currTicket.getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())
        ) {
                matches.add(currTicket);
        }
    }

    return matches;
    }

    public ArrayList<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }


    public int getTicketCount() {
        return tickets.size();
    }
}
