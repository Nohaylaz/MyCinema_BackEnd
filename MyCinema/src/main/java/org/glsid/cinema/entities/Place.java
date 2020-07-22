package org.glsid.cinema.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Place {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int numPlace;
	private double longtitude, latitude, altitude;
	
	@ManyToOne
	private Salle salle;
	@OneToMany(mappedBy="place")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Ticket> tickets;
	

}