package online.fadai.opengptproject;

import online.fadai.opengptproject.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Import(RestConfig.class)
@EnableScheduling
public class OpenGptProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenGptProjectApplication.class, args);
    }

}
