package com.example.demo.user;

import com.example.demo.admin.model.RendezVous;
import com.example.demo.login.model.LoginPojo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;

@Repository
public class UserRepository {

    MongoOperations mongoOperations;

    public UserRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public List<RendezVous> getListRendezVous() {
        return mongoOperations.findAll(RendezVous.class);
    }

    public void select(RendezVous rendezVous, String username) {

        Query searchQuery = new Query(Criteria.where("email").is(username));
        LoginPojo loginPojo = mongoOperations.findOne(searchQuery, LoginPojo.class);
        rendezVous.setReservedId(loginPojo.getId());
        mongoOperations.save(rendezVous);

    }

    public void annulerRendezVous(RendezVous rendezVous) {
        rendezVous.setReservedId("");
        mongoOperations.save(rendezVous);
    }

    public List<RendezVous> getListRendezVousByUser(String username) {
        Query searchQuery = new Query(Criteria.where("email").is(username));
        LoginPojo loginPojo = mongoOperations.findOne(searchQuery, LoginPojo.class);
        searchQuery = new Query(Criteria.where("reservedId").is(loginPojo.getId()));
        return mongoOperations.find(searchQuery, RendezVous.class);
    }
}
