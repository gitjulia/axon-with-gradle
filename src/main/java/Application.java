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
    public CommandGatewayFactoryBean commandGateway() {
        CommandGatewayFactoryBean factory = new CommandGatewayFactoryBean();
        factory.setCommandBus(commandBus());
        return factory;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus());
        return handler;
    }

    @Bean(name = "recipeRepository")
    public Repository<Recipe> paymentRepository() {
        return new EventSourcingRepository<>(Recipe.class, eventStore());
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public EventStore eventStore() {
        return new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor listener = new AnnotationEventListenerBeanPostProcessor();
        listener.setEventBus(eventBus());
        return listener;
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
