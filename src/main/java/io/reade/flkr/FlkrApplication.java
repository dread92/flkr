package io.reade.flkr;

import io.reade.flkr.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.reade.flkr.storage.StorageProperties;
import io.reade.flkr.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FlkrApplication {

    public static final Logger log = LoggerFactory.getLogger(FlkrApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FlkrApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return args -> {
			repository.save(new User("Keturah", "Bauer"));
			repository.save(new User("Bob", "Bauer"));
			repository.save(new User("April", "O'Brian"));
			repository.save(new User("Rachel", "Palmer"));
			repository.save(new User("Shiloh", "Dessler"));

			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (User user : repository.findAll()) {
			    log.info(user.toString());
            }
            log.info("");

			repository.findById(1L)
                    .ifPresent(user -> {
                        log.info("User found with findById(1L):");
                        log.info("-----------------------------");
                        log.info(user.toString());
                        log.info("");

                    });
			log.info("Users found with findByLastName('Bauer'):");
            log.info("-----------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
			log.info("");
		};
	}

	@Bean
    CommandLineRunner init(StorageService storageService) {
	    return (args) -> {
	        storageService.deleteAll();
	        storageService.init();
        };
    }
}
