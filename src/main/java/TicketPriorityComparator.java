import java.util.Comparator;

public class TicketPriorityComparator
        implements Comparator<Ticket> {

    @Override
    public int compare(Ticket first, Ticket second) {
        return Integer.compare(
            second.getPriority().ordinal(),
            first.getPriority().ordinal()
        );
    }
}