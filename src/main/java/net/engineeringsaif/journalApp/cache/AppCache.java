package net.engineeringsaif.journalApp.cache;
import jakarta.annotation.PostConstruct;
import net.engineeringsaif.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringsaif.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
