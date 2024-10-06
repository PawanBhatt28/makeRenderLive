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
    private boolean stopThread = false;

    public ResponseEntity<String>  pingYourself(String address) throws InterruptedException {
        return ping(address);
    }

    public ResponseEntity<String> ping(String url){
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                while (!stopThread) {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        String response = restTemplate.getForObject(url, String.class);
                        if (response != null) {
                            dao.setData(response);
                            LOGGER.info("Response from {}: {}", url, response);
                        } else {
                            LOGGER.warn("Received null response from {}", url);
                        }
                        Thread.sleep(2000);
                    } catch (Exception error) {
                        LOGGER.error("Error pinging {}: {}", url, error.getMessage());
                        break;
                    }
                }
            });
            thread.start(); // Start the thread
        }
        return new ResponseEntity<>("Ping started", HttpStatus.OK);
    }

    public void stopPinging() {
        stopThread = true;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
