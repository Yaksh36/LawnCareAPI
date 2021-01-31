package patel.yaksh.lawn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"patel.yaksh.lawn.Controller","patel.yaksh.lawn.Service","patel.yaksh.lawn.Config"})
public class LawnCareApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LawnCareApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LawnCareApplication.class);
    }

}
