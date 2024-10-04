package dev.pawanbhatt.ownown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend")
public class Controller {

    @Autowired
    Service service;

    @Autowired
    Dao dao;

    @GetMapping("server-health")
    public ResponseEntity<String> serverHealth(){
        return new ResponseEntity<String>("Up and running.", HttpStatus.OK);
    }

    @GetMapping("getData")
    public ResponseEntity<String> getData(){
        return new ResponseEntity<>(dao.getData(), HttpStatus.OK);
    }

    @GetMapping("start-ping")
    public ResponseEntity<String> startPing(@RequestParam String serverAddress) throws InterruptedException {
        return service.pingYourself(serverAddress);
    }

}
