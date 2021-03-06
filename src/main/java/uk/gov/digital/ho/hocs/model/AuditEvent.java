package uk.gov.digital.ho.hocs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Access(AccessType.FIELD)
@EqualsAndHashCode(of = {"uuid", "timestamp"})
@Getter
public class AuditEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "msg_uuid", nullable = false)
    private String uuid;

    @Column(name = "msg_timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "case_reference", nullable = false)
    private String caseReference;

    @Column(name = "data", nullable = false)
    private String data;

    public AuditEvent(Event event) {
        if(event.getUuid() != null) {
            this.uuid = event.getUuid();
        }
        if(event.getTimestamp() != null) {
            this.timestamp = event.getTimestamp();
        }
        if(event.getCaseReference() != null) {
            this.caseReference = event.getCaseReference();
        }
        if(event.getData() != null) {
            this.data = event.getData().toString();
        }
    }


}