package hello;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.ibm.websphere.objectgrid.spring.ObjectGridCache;
import com.ibm.websphere.objectgrid.spring.ObjectGridCatalogServiceDomainBean;
import com.ibm.websphere.objectgrid.spring.ObjectGridClientBean;

@SpringBootApplication
@EnableCaching
public class Application {

	private static final Logger log = LoggerFactory
			.getLogger(Application.class);

	@Component
	static class Runner implements CommandLineRunner {
		@Autowired
		private BookRepository bookRepository;

		@Override
		public void run(String... args) throws Exception {
			ApplicationContext ctx = SpringApplication.run(Application.class,
					args);
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			log.info(".... Fetching books");
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
		}
	}

//	@Bean
//	public CacheManager cacheManager() {
//		ObjectGridCatalogServiceDomainBean ogcsdb = new ObjectGridCatalogServiceDomainBean();
//		ogcsdb.setCatalogServiceEndpoints("172.29.160.26:2809");
//		ObjectGridClientBean client = new ObjectGridClientBean();
//		client.setCatalogServiceDomain(ogcsdb);
//		
//		SimpleCacheManager scm = new SimpleCacheManager();
//		Set caches = new HashSet();
//		ObjectGridCache cache = new ObjectGridCache();
//		cache.setName("default");
//		cache.setObjectGridClient(client);
//		caches.add(cache);
//		cache.setName("books");
//		cache.setObjectGridClient(client);
//		caches.add(cache);
//		scm.setCaches(caches);
//		return scm;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@Configuration
@ImportResource("/spring-context.xml")
class XmlImportingConfiguration {
}
