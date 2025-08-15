package net.engineeringsaif.journalApp.service;

import net.engineeringsaif.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class userServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @BeforeEach, @BeforeAll, @AfterEach, @AfterAll
//    void setUp(){
//
//    }

//    @Disabled
//    @ParameterizedTest
//    @ArgumentsSource(userArgumentProvider.class)
//    public void testSaveNewUser(User user){
//        assertTrue(userService.saveNewUser(user));
//    }

//    @ParameterizedTest
//    @CsvSource({
//            "ram",
//            "sham",
//            "vipul"
//    })
//    public void testFindByUserName(String name){
//        assertNotNull(userRepository.findByUserName(name), "failed for : " + name);
//    }


//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "2,10,12",
//            "3,3,9"
//    })
//    public void test(int a, int b, int expected){
//        assertEquals(expected, a+b);
//    }
}
