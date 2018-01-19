package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CaseCurrentPropertiesServiceTest {

    @Mock
    private CaseCurrentPropertiesRepository mockCurrentPropertiesRepo;

    private CaseCurrentPropertiesService currentPropertiesService;

    @Before
    public void setUp() {
        currentPropertiesService = new CaseCurrentPropertiesService(mockCurrentPropertiesRepo);
    }

    @Test
    /*
     * Test that an event is saved as event and property
     */
    public void testCreateEvent() {
        Event event = getValidEvent();

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).save(any(CaseCurrentProperties.class));
    }


    @Test()
    public void testRepoDataIntegrityExceptionThrowsEntityCreationExceptionProperty() {
        Event event = getValidEvent();

        when(mockCurrentPropertiesRepo.save(any(CaseCurrentProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "current_properties_id_idempotent")));

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityExceptionProperty() {
        Event event = getValidEvent();

        when(mockCurrentPropertiesRepo.save(any(CaseCurrentProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "other error")));

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).save(any(CaseCurrentProperties.class));
    }

    private Event getValidEvent() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }

}