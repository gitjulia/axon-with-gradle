import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@EnableAutoConfiguration
@ComponentScan
@AnnotationDriven
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }
}