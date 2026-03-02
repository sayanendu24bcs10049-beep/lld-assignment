import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;
import java.util.List;

/**
 * Starter demo that shows why mutability is risky.
 *
 * After refactor:
 * - direct mutation should not compile (no setters)
 * - external modifications to tags should not affect the ticket
 * - service "updates" should return a NEW ticket instance
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // Demonstrate service updates now return new instances
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter service updates (original unchanged): " + t);
        System.out.println("Assigned: " + assigned);
        System.out.println("Escalated: " + escalated);

        // external list modification cannot affect ticket
        List<String> tags = t.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException ignored) {
            System.out.println("\nCannot modify tags list; it's immutable");
        }
        System.out.println("\nFinal ticket state: " + t);
    }
}
