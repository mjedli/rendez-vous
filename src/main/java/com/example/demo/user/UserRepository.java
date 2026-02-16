package com.example.demo.user;

import com.example.demo.admin.model.RendezVous;
import com.example.demo.login.model.LoginPojo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

        // 1. Récupérer l'utilisateur
        Query searchQuery = new Query(Criteria.where("email").is(username));
        LoginPojo loginPojo = mongoOperations.findOne(searchQuery, LoginPojo.class);

        // 2. Date du jour au format String YYYY-MM-DD
        String today = LocalDate.now().toString(); // ex: "2026-02-16"

        // 3. Requête : reservedId = userId ET date >= today
        Query query = new Query();
        query.addCriteria(
                Criteria.where("reservedId").is(loginPojo.getId())
                        .and("date").gte(today)
        );

        return mongoOperations.find(query, RendezVous.class);
    }

    public List<RendezVous> getListRendezVousByDate(String date) {
        Query searchQuery = new Query(Criteria.where("date").is(date));
        return mongoOperations.find(searchQuery, RendezVous.class);
    }
}
