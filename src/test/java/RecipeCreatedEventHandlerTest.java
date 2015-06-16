import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.UUID;
import static java.time.LocalDate.now;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCreatedEventHandlerTest {

    public static final String ACCOUNT_ID = UUID.randomUUID().toString();

    @Mock
    private RecipeRepository repository;

    @Test
    public void shouldSavePaymentOnAccountWhenPaymentScheduleCreatedEventIsReceived() throws Exception {
        RecipeCreatedEventHandler eventHandler = new RecipeCreatedEventHandler(repository);

        eventHandler.on(new RecipeCreatedEvent("recipe Id"));

        verify(repository).addPayment(any(RecipeView.class));
    }
}