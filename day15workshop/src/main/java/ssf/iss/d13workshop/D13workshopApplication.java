package ssf.iss.d13workshop;

import java.io.File;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import ssf.iss.d13workshop.model.Contact;
import ssf.iss.d13workshop.repo.ContactsRedis;

@SpringBootApplication
public class D13workshopApplication implements ApplicationRunner{ 
	// ./mvnw spring-boot:run -Dspring-boot.run.arguments="--dataDir=/Users/CelineNg/Desktop/d13workshop/data"

	//testing
	@Autowired @Qualifier("ContactsRedis") // from AppConfig
	private RedisTemplate<String, Object> template;

	@Autowired
    private ContactsRedis contactsRedis;

	public static void main(String[] args) {
		SpringApplication.run(D13workshopApplication.class, args);		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.printf("redis template:>>>>>%s\\n", template);

		// if (args.containsOption("dataDir")){
		// 	final String dataDir = args.getOptionValues("dataDir").get(0); //get name of file to be created
		// 	File fileDir = new File(dataDir);

		// 	if (!fileDir.exists()){
		// 		fileDir.mkdir();
		// 		System.out.println("===" + fileDir.getAbsolutePath());
		// 		System.out.println("===" + fileDir.getPath());
		// 		System.out.println("===" + fileDir.getParent());
		// 	}
		// } else {
		// 	System.out.println("--dataDir option is not specified");
		// }
	}

}
