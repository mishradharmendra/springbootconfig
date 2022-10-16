package com.test.demo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Value("${con.key1}")
    String conKey1;

    @Value("${con.key2}")
    String conKey2;

    @RequestMapping("/getval")
    public ResponseEntity<Map> getVal(@RequestParam(value="key", defaultValue="World") String key) {
        Map<String, String> mapOfKeyValue = new HashMap<>();
        mapOfKeyValue.put("con.key2", conKey2);
        mapOfKeyValue.put("con.key1", conKey1);
        return ResponseEntity.ok(mapOfKeyValue);
    }
}
