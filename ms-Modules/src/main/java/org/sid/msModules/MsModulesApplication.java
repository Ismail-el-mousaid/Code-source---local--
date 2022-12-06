package org.sid.msModules;

import org.sid.msModules.service.IModuleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.ParseException;
import java.util.*;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.sid.msModules.repository")
public class MsModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsModulesApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IModuleService moduleService) {
     //   moduleService.getDatesDeploiements();
        return args -> {
            int MINUTES = 5; // The delay in minutes
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() { // La fonction s'exécute toutes les MINUTES minutes.
                    try {
                        moduleService.getInfosFromAPI();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000 * 60 * MINUTES);
      };
    }


}
