import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.repository.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@AnnotationDriven
public class Application {

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
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
    }

    @Bean(name = "recipeRepository")
    public Repository<Recipe> recipeRepository() {
        return ((DisruptorCommandBus) commandBus()).createRepository(new GenericAggregateFactory<>(Recipe.class));
    }

    @Bean
    public EventSourcingRepository<Recipe> recipeRepository(EventBus eventBus, EventStore eventStore) {
        EventSourcingRepository<Recipe> repository = new EventSourcingRepository<>(Recipe.class, eventStore);
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
        return new DisruptorCommandBus(eventStore(), eventBus());
    }

    @Bean
    public EventTemplate eventTemplate(EventBus eventBus) {
        return new EventTemplate(eventBus);
    }

    @Bean
    public AggregateAnnotationCommandHandler<Recipe> commandHandler(Repository<Recipe> recipeRepository, CommandBus commandBus) {
        return AggregateAnnotationCommandHandler.subscribe(Recipe.class, recipeRepository, commandBus);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
