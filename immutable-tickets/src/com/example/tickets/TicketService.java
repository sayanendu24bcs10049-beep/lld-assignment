package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // use builder and centralized validation
        IncidentTicket.Builder b = new IncidentTicket.Builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(List.of("NEW"));
        return b.build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        // return a new instance with updated priority and tag
        return t.toBuilder()
                .priority("CRITICAL")
                .tags(appendTag(t.getTags(), "ESCALATED"))
                .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        // rely on builder validation
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }

    private List<String> appendTag(List<String> existing, String tag) {
        List<String> copy = new ArrayList<>(existing);
        copy.add(tag);
        return copy;
    }
}
