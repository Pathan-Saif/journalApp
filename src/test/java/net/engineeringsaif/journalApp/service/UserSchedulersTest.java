package net.engineeringsaif.journalApp.service;

import net.engineeringsaif.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSchedulersTest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void fetchUsersAndSendSaMail(){
        userScheduler.fetchUsersAndSendSaMail();
    }

}
