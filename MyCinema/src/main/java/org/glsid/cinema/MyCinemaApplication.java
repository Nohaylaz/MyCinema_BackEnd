package org.glsid.cinema;

import org.glsid.cinema.dao.CategoryRepository;
import org.glsid.cinema.entities.Film;
import org.glsid.cinema.entities.Salle;
import org.glsid.cinema.entities.Ticket;
import org.glsid.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class MyCinemaApplication implements CommandLineRunner {

	
	@Autowired private ICinemaInitService cinemaInitService;
	@Autowired private RepositoryRestConfiguration restConfiguration;
	@Autowired private CategoryRepository categoryRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(MyCinemaApplication.class, args);
	}
	
	public void run (String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
        cinemaInitService.initVilles();
        cinemaInitService.initCinemas();
        cinemaInitService.initSalles();
        cinemaInitService.initPlaces();
        cinemaInitService.initSeances();
        cinemaInitService.initCategories();
        cinemaInitService.initFilms();
        cinemaInitService.initProjections();
        cinemaInitService.initTickets();
	}

}








