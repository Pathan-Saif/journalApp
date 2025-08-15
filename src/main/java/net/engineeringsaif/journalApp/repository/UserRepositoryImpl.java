package net.engineeringsaif.journalApp.repository;

import net.engineeringsaif.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserForSA(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("vipul"));

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

//        query.addCriteria(Criteria.where("roles").in("USER","ADMIN")); // check list type

//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.andOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnalysis").is(true)));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
