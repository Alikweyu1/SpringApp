package apharm.co.ke.fskcb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class SpringBootRestApiApplication {
    @Bean
    public DateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApiApplication.class,args);
    }
}
