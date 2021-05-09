package restTemplateTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringRestClient {
    @Autowired
    private RestTemplate restTemplate;

    final String url = "http://91.241.64.178:7081/api/users";


    HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);


    int x = 90;



}
