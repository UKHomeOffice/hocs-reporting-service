package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;
import uk.gov.digital.ho.hocs.model.Event;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepo) {
        this.eventRepository = eventRepo;
    }

    public void createEvent(Event event) throws EntityCreationException {
        try {
            AuditEvent auditEvent = new AuditEvent(event);
            eventRepository.save(auditEvent);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    (((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("event_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            }
            else {
                throw e;
            }
        }
    }

}