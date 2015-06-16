import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventTemplate;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;

@Configuration
public class AxonConfiguration {

    @Bean
    public CommandGatewayFactoryBean commandGateway(CommandBus commandBus) {
        CommandGatewayFactoryBean factory = new CommandGatewayFactoryBean();
        factory.setCommandBus(commandBus);
        return factory;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(CommandBus commandBus) {
        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus);
        return handler;
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("/events")));
    }

    @Bean
    public EventSourcingRepository<RecipeSchedule> accountRepository(EventBus eventBus, EventStore eventStore) {
        EventSourcingRepository<RecipeSchedule> repository = new EventSourcingRepository<>(RecipeSchedule.class, eventStore);
        repository.setEventBus(eventBus);
        return repository;
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(EventBus eventBus) {
        AnnotationEventListenerBeanPostProcessor result = new AnnotationEventListenerBeanPostProcessor();
        result.setEventBus(eventBus);
        return result;
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public EventTemplate eventTemplate(EventBus eventBus) {
        return new EventTemplate(eventBus);
    }

    @Bean
    public AggregateAnnotationCommandHandler<Recipe> commandHandler(Repository<Recipe> recipeRepository,
                                                                             CommandBus commandBus) {
        return AggregateAnnotationCommandHandler.subscribe(Recipe.class, recipeRepository, commandBus);
    }

}
