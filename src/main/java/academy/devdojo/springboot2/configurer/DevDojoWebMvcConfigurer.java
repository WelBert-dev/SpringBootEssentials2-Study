package academy.devdojo.springboot2.configurer;

/* Definições sobre WebMvcConfigurer:

O WebMvcConfigurer é uma interface fornecida pelo Spring Framework no pacote
org.springframework.web.servlet.config.annotation. Essa interface permite que
você customize a configuração do Spring MVC, que é o módulo do Spring Framework
responsável pelo desenvolvimento de aplicativos da web.

O objetivo principal do WebMvcConfigurer é fornecer métodos de callback que
permitem que você personalize a configuração do Spring MVC de acordo com suas
necessidades específicas. Ele serve como um ponto de extensibilidade para a
configuração do MVC no Spring.

---> Alguns exemplos de funcionalidades que podemos configurar usando o
WebMvcConfigurer incluem:

    - Registro de conversores de dados personalizados:
        * `configureMessageConverters`: Configura conversores de mensagem
        personalizados para converter entre objetos Java e representações de
        mensagem (por exemplo, JSON, XML).

    - Registro de formatters para conversão entre tipos de dados específicos:
        * `addFormatters`: Adiciona formatters personalizados para conversão entre
        tipos de dados específicos, como formatação de datas, números, etc.

    - Configuração de validadores personalizados:
        * `getValidator`: Retorna um validador personalizado para validação de
        objetos em formulários.

        * `getValidationMessageSource`: Retorna um MessageSource personalizado
        para mensagens de validação.

    - Registro de manipuladores de recursos estáticos:
        * `addResourceHandlers`: Adiciona manipuladores para servir recursos
        estáticos, como arquivos CSS, JavaScript, imagens, etc.

    - Personalização de interceptadores de solicitações (interceptors):
        * `addInterceptors`: Adiciona interceptadores para executar lógica antes
        ou depois de uma solicitação ser manipulada pelo controlador.

    - Configuração de exibição (view) de resolução, como resolvers para resolver
    nomes de exibição em visualizações reais (view resolution):
        * `configureViewResolvers`: Configura os resolvers de exibição, que mapeiam
        nomes de exibição para exibições reais.

    - Configuração de mensagens e internacionalização:
        * `addFormatters`: Adiciona conversores personalizados para formatação de
        dados, como datas, números, etc.

        * `getMessageCodesResolver`: Retorna um resolvedor de códigos de mensagem
        para resolver códigos de mensagem em mensagens reais.

        * `getMessageSource`: Retorna um MessageSource personalizado para localização
        de mensagens internacionalizadas.

Ao implementar a interface WebMvcConfigurer e fornecer suas próprias implementações
para esses métodos de callback, você pode personalizar o comportamento do Spring MVC
de acordo com suas necessidades específicas.
Isso permite que você adicione ou substitua a configuração padrão fornecida pelo
Spring MVC.

Vale ressaltar que o WebMvcConfigurer é usado em conjunto com a classe
WebMvcConfigurerAdapter, que é uma classe adaptadora que fornece implementações
vazias (métodos vazios) para todos os métodos da interface WebMvcConfigurer.
Isso permite que você implemente apenas os métodos que são relevantes para a
sua personalização, sem a necessidade de fornecer uma implementação para todos
os métodos da interface.

Em resumo, o WebMvcConfigurer é uma interface do Spring MVC que permite a
personalização e configuração do comportamento do Spring MVC em aplicativos web,
fornecendo métodos de callback para você adicionar sua própria lógica personalizada.

*/

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));

        resolvers.add(pageHandler);
       // WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
