package com.example.demo.login;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;


import com.example.demo.login.model.*;


/**
 * @author mjedli
 *
 */
@Repository
public class ParismonRepository {

	private MongoOperations mongoOperations;

	public ParismonRepository(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public LoginPojo findByMail(String mail) {
		Query searchQuery = new Query(Criteria.where("email").is(mail));
		return mongoOperations.findOne(searchQuery, LoginPojo.class);
	}

	public User findByUser(String mail) {
		Query searchQuery = new Query(Criteria.where("email").is(mail));
		return mongoOperations.findOne(searchQuery, User.class);
	}

	public LoginPojo insert(LoginPojo parismon) {
		LoginPojo b = findByMail(parismon.getEmail());
		if ( (b!=null) && (b.getEmail() != null) && (b.getEmail().equals(parismon.getEmail()) ) ) {
			b.setEmail("exist");
			return b;
		}
		return mongoOperations.insert(parismon);
	}
	
	public LoginPojo updateParismon(LoginPojo parismon) {		
		return mongoOperations.save(parismon);
	}
	
	public LoginPojo findParismonByActiveToken(String id) {
		Query searchQuery = new Query(Criteria.where("activeMailToken").is(id));
		return mongoOperations.findOne(searchQuery, LoginPojo.class);
	}

}
