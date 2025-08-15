package net.engineeringsaif.journalApp.scheduler;

import net.engineeringsaif.journalApp.cache.AppCache;
import net.engineeringsaif.journalApp.entity.JournalEntry;
import net.engineeringsaif.journalApp.entity.User;
import net.engineeringsaif.journalApp.enums.Sentiment;
import net.engineeringsaif.journalApp.model.SentimentData;
import net.engineeringsaif.journalApp.repository.UserRepositoryImpl;
import net.engineeringsaif.journalApp.service.EmailService;
import net.engineeringsaif.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired(required = false)
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

//    @Scheduled(cron = "0 9 * * SUN *")
    @Scheduled(cron = "0 * * ? * *")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
//            if(mostFrequentSentiment != null)
//                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());

            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                if(kafkaTemplate != null){
                    try {
                        kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData).get();
                    }
                    catch (Exception e){
                        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous weeks", sentimentData.getSentiment());
                    }
                }
                else{
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous weeks", sentimentData.getSentiment());
                }

            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
