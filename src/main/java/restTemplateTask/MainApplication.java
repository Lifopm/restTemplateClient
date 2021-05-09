package restTemplateTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import java.util.List;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) throws JsonProcessingException {
        //SpringApplication.run(MainApplication.class, args);

        RestTemplate restTemplate = getRestTemplate();

        final String url = "http://91.241.64.178:7081/api/users";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        User[] user = objectMapper.readValue(response.getBody(), User[].class);

        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(headers.SET_COOKIE);

        System.out.println("coockie: " + set_cookie);

        System.out.println("response body: " + "\n" + "\n");
        for (int i = 0; i < user.length; i++) {
            System.out.println(user[i].userInfo());
        }

        User userId3 = new User();
        userId3.setId(new Long(3));
        userId3.setName("James");
        userId3.setLastName("Brown");
        userId3.setAge((byte)30);


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", set_cookie);
        HttpEntity requestEntity = new HttpEntity(userId3, requestHeaders);
        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String part1 = response2.getBody();

        userId3.setName("Thomas");
        userId3.setLastName("Shelby");

        requestEntity = new HttpEntity(userId3, requestHeaders);
        ResponseEntity<String> response3 = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        String part2 = response3.getBody();

        requestEntity = new HttpEntity(userId3, requestHeaders);
        final String urlDelete = "http://91.241.64.178:7081/api/users/3";
        ResponseEntity<String> response4 = restTemplate.exchange(urlDelete, HttpMethod.DELETE, requestEntity, String.class);
        String part3 = response4.getBody();

        System.out.println(part1 + part2 + part3);
    }

    @Bean
    public static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
