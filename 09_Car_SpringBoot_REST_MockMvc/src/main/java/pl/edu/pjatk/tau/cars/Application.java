package pl.edu.pjatk.tau.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Configuration
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Transactional
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }

}
