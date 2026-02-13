package com.example.demo.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

/**
 * @author mjedli
 *
 */
@Document
public class RendezVous {
	
	@Id
	@NonNull
	private String id;
	@NonNull
	@Indexed(unique=true)
	private String date = "";

	@NonNull
	@Indexed(unique=true)
	private String heure = "";

	@NonNull
	@Indexed(unique=true)
	private String reservedId = "";


	@NonNull
	public String getReservedId() {
		return reservedId;
	}

	public void setReservedId(@NonNull String reservedId) {
		this.reservedId = reservedId;
	}

	@NonNull
	public String getId() {
		return id;
	}

	public void setId(@NonNull String id) {
		this.id = id;
	}

	@NonNull
	public String getDate() {
		return date;
	}

	public void setDate(@NonNull String date) {
		this.date = date;
	}

	@NonNull
	public String getHeure() {
		return heure;
	}

	public void setHeure(@NonNull String heure) {
		this.heure = heure;
	}
}
