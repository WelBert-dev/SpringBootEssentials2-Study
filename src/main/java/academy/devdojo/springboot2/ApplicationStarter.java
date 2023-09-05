package academy.devdojo.springboot2;

import academy.devdojo.springboot2.client.SpringClient;
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
        SpringClient.execute();

        // Executando request para a prórpia API em execução HTTP GET
        // no ListAll com retorno Array nativo:
        SpringClient.executeHttpGetWithReturnsListAll_array();
        // Executando request para a prórpia API em execução HTTP GET
        // no ListAll com retorno Collections List:
        SpringClient.executeHttpGetWithReturnsListAll_ListOrOtherCollection();

        // Executando request para a própria API em execução HTTP POST
        // Criando um novo recurso:
        // Obs: Com retorno do Objeto concreto em sí Anime,
        // não é enpacotado em ResponseEntity<Anime>
        SpringClient.executeHttpPostWithReturnsObject();

        // Executando request para a própria API em execução HTTP POST
        // Criando um novo recurso:
        // Obs: Com retorno do Objeto enpacotado em um ResponseEntity<Anime>
        SpringClient.executeHttpPostWithReturnsResponseEntityWrapperOfAnime();

        // Executando request para a própria API em execução HTTP POST
        // Criando um novo recurso:
        // Obs: Utilizando exchange que possibilita maiores configurações para
        // a requisição:
        SpringClient.executeHttpPostWithExchangeBecauseMorePersonalization();

    }
}
