package com.example.demo.admin;

import com.example.demo.admin.model.RendezVous;
import com.example.demo.login.model.LoginPojo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public class AdminRepository {

    private MongoOperations mongoOperations;

    public AdminRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public RendezVous findByDateAndHeure(String date, String heure) {
        Query searchQuery = new Query(Criteria.where("date").is(date).and("heure").is(heure));
        return mongoOperations.findOne(searchQuery, RendezVous.class);
    }

    public RendezVous addRendezvous(RendezVous rendezvous) {
        RendezVous b = findByDateAndHeure(rendezvous.getDate(), rendezvous.getHeure());
        if ( (b!=null) && (b.getDate() != null) && (b.getDate().equals(rendezvous.getDate()) )
                && (b.getHeure() != null) && (b.getHeure().equals(rendezvous.getHeure()) ) ) {
            b.setHeure("exist");
            return b;
        }
        return mongoOperations.insert(rendezvous);
    }

    public List<RendezVous> getListRendezVous() {
        return mongoOperations.findAll(RendezVous.class);
    }

    public void deleteRendezVous(RendezVous rendezvous) {
        mongoOperations.remove(rendezvous);
    }
}
