package uk.gov.digital.ho.hocs.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditEventTest {

@Test
public void createWithEntities() throws Exception {
    String uuid = "uuid";
    LocalDateTime dateTime = LocalDateTime.now();
    String caseRef = "CaseRef";
    Map<String, String> data = new HashMap<>();
    Event event = new Event(uuid, dateTime, caseRef, data);

    AuditEvent auditEvent = new AuditEvent(event);

    assertThat(auditEvent.getUuid()).isEqualTo(uuid);
    assertThat(auditEvent.getTimestamp()).isEqualTo(dateTime);
    assertThat(auditEvent.getCaseReference()).isEqualTo(caseRef);
    assertThat(auditEvent.getData()).isEqualTo(data.toString());
}

@Test
public void createWithoutEntities() throws Exception {
    Event event = new Event(null, null, null, null);

    AuditEvent auditEvent = new AuditEvent(event);

    assertThat(auditEvent.getUuid()).isNull();
    assertThat(auditEvent.getTimestamp()).isNull();
    assertThat(auditEvent.getCaseReference()).isNull();
    assertThat(auditEvent.getData()).isNull();
}

    @Test
    public void EqualsOnlyByIdentity() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();

        Event eventOne = new Event("uuid", dateTime, "CaseRef", new HashMap<>());
        AuditEvent auditEventOne = new AuditEvent(eventOne);

        Event eventTwo = new Event("uuid", dateTime, "otherCaseRef", new HashMap<>());
        AuditEvent auditEventTwo = new AuditEvent(eventTwo);

        assertThat(auditEventOne).isEqualTo(auditEventTwo);
    }

    @Test
    public void NotsEqualsOnlyByIdentityUUID() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();

        Event eventOne = new Event("uuid", dateTime, "CaseRef", new HashMap<>());
        AuditEvent auditEventOne = new AuditEvent(eventOne);

        Event eventTwo = new Event("otheruuid", dateTime, "CaseRef", new HashMap<>());
        AuditEvent auditEventTwo = new AuditEvent(eventTwo);

        assertThat(auditEventOne).isNotEqualTo(auditEventTwo);
    }

    @Test
    public void NotsEqualsOnlyByIdentityTimestamp() throws Exception {
        LocalDateTime dateTimeOne = LocalDateTime.now();
        LocalDateTime dateTimeTwo = LocalDateTime.now().minusSeconds(1L);

        Event eventOne = new Event("uuid", dateTimeOne, "CaseRef", new HashMap<>());
        AuditEvent auditEventOne = new AuditEvent(eventOne);

        Event eventTwo = new Event("uuid", dateTimeTwo, "CaseRef", new HashMap<>());
        AuditEvent auditEventTwo = new AuditEvent(eventTwo);

        assertThat(auditEventOne).isNotEqualTo(auditEventTwo);
    }

}