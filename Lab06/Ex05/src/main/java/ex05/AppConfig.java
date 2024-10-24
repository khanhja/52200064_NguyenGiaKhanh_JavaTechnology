package ex05;

import ex05.Entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
public class AppConfig {
    @Value("${person.name}")
    private String name;

    @Value("${person.gender}")
    private String gender;

    @Value("${person.age}")
    private int age;

    @Bean
    public Person createPerson() {
        return new Person(name, gender, age);
    }
}
