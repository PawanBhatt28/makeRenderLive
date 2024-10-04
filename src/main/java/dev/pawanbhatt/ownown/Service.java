package dev.pawanbhatt.ownown;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class Service {

    public static final Logger LOGGER= LoggerFactory.getLogger(Service.class);

    @Autowired
    Dao dao;

    public Thread thread;

    public ResponseEntity<String>  pingYourself(String address) throws InterruptedException {
        return ping(address);
    }

    public ResponseEntity<String> ping(String url){
//        thread = new Thread(() -> {
            while (true) {
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    dao.setData(Objects.requireNonNull(restTemplate.getForObject(url, String.class)).toString());
                    LOGGER.info(url);
                    Thread.sleep(2000);
                } catch (Exception error) {
                    LOGGER.error(error.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        //        };
//        thread.start();
//        return new ResponseEntity<>( "Cannot send request", HttpStatus.BAD_REQUEST);
    }
}
