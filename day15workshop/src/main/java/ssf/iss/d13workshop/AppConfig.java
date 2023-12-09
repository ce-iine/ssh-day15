package ssf.iss.d13workshop;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ssf.iss.d13workshop.model.Contact;

@Configuration
public class AppConfig {
    
    private Logger logger = Logger.getLogger(AppConfig.class.getName());

    //inject the properties from application.properties into the configuration 
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.username}")
    private String redisUser;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;


    @Bean("ContactsRedis")
    public RedisTemplate<String, Object> contactsRedisTemplate() {
        
        //create redis configuration
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);

        //only set the username and passord if they are set - currently "NOT_SET"
        if (!"NOT_SET".equals(redisUser.trim())){
            config.setUsername(redisUser);
            config.setPassword(redisPassword);
        }

        logger.log(Level.INFO, "Using Redis database %d".formatted(redisPort)); 
        //dont log username and password if want to check if password set, use boolean
        logger.log(Level.INFO, "Redis password is set:%b".formatted(redisPassword != "NOT_SET"));

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);

        template.setKeySerializer(new StringRedisSerializer()); //serialiser transforms the data into the byte array - redis only stores byte array 
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();


        return template;
    }


    
}
