package academy.devdojo.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


// Todos essas em uma unica abaixo:
//@EnableAutoConfiguration
//@ComponentScan
//@Configuration

// Aqui une as três anotações anteriores que formam a base de um APP Spring
@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
