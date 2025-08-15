package net.engineeringsaif.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringsaif.journalApp.api.response.WeatherResponse;
import net.engineeringsaif.journalApp.cache.AppCache;
import net.engineeringsaif.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service  // or @Component used for make Beans(object banana)
@Slf4j
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    private AppCache appCache;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;
    
    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }
        else{
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
//        log.info("Requesting Weather API with URL: {}", finalAPI);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
//        System.out.println("API Response Status: " + response.getStatusCode());
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }

    }
}
