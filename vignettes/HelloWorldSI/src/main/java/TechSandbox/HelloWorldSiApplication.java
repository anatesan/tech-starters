package TechSandbox;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldSiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Hello world\n");
	}
}
