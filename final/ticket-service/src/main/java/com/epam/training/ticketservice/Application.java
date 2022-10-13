package com.epam.training.ticketservice;

import com.epam.training.ticketservice.user.Role;
import com.epam.training.ticketservice.user.User;
import com.epam.training.ticketservice.user.UserRepository;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        System.out.println("Hello world");
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setBannerMode(Banner.Mode.OFF);

        app.run(args);


    }



    @Component
    public class DataLoader implements CommandLineRunner {

        private UserRepository userRepository;


        public DataLoader(UserRepository userRepository) {
            this.userRepository = userRepository;

        }

        @Override
        public void run(String... args) throws Exception  {

        }

        @EventListener(ContextRefreshedEvent.class)
        public void doSomethingAfterStartup() {
            userRepository.save(new User("admin","admin", Set.of(Role.ADMIN,Role.USER)));
        }
    }
}
