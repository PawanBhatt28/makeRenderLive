package dev.pawanbhatt.ownown;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class Service {

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
                    System.out.println("PONG");
                    Thread.sleep(2000);
                } catch (Exception error) {
                    System.out.println(error.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
//        };
//        thread.start();
//        return new ResponseEntity<>( "Cannot send request", HttpStatus.BAD_REQUEST);
    }
}
