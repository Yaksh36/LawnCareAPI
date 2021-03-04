package patel.yaksh.lawn.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String QUEUE_FORGOT_PASSWORD = "forgot-queue";
    public static final String EXCHANGE_FORGOT_PASSWORD = "forgot-exchange";
    public static final String QUEUE_SERVICE= "request-service-queue";

    @Bean
    Queue forgotPasswordQueue(){
        return new Queue(QUEUE_FORGOT_PASSWORD,true);
    }

    @Bean
    TopicExchange forgotPasswordExchange(){
        return new TopicExchange(EXCHANGE_FORGOT_PASSWORD);
    }

    @Bean
    Binding binding(){
        return BindingBuilder.bind(forgotPasswordQueue()).to(forgotPasswordExchange()).with(QUEUE_FORGOT_PASSWORD);
    }

    @Bean
    Queue serviceQueue(){
        return QueueBuilder.durable(QUEUE_SERVICE).build();
    }

    @Bean
    Binding bindingService(){
        return BindingBuilder.bind(serviceQueue()).to(forgotPasswordExchange()).with(QUEUE_SERVICE);
    }


}