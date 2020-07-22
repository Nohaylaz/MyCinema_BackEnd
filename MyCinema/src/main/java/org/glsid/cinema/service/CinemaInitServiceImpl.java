package org.glsid.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.glsid.cinema.dao.CategoryRepository;
import org.glsid.cinema.dao.CinemaRepository;
import org.glsid.cinema.dao.FilmRepository;
import org.glsid.cinema.dao.PlaceRepository;
import org.glsid.cinema.dao.ProjectionRepository;
import org.glsid.cinema.dao.SalleRepository;
import org.glsid.cinema.dao.SeanceRepository;
import org.glsid.cinema.dao.TicketRepository;
import org.glsid.cinema.dao.VilleRepository;
import org.glsid.cinema.entities.Category;
import org.glsid.cinema.entities.Cinema;
import org.glsid.cinema.entities.Film;
import org.glsid.cinema.entities.Place;
import org.glsid.cinema.entities.Projection;
import org.glsid.cinema.entities.Salle;
import org.glsid.cinema.entities.Seance;
import org.glsid.cinema.entities.Ticket;
import org.glsid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
	
	@Autowired private VilleRepository villeRepository;
    @Autowired private CinemaRepository cinemaRepository;
    @Autowired private SalleRepository salleRepository;
    @Autowired private PlaceRepository placeRepository;
    @Autowired private SeanceRepository seanceRepository;
    @Autowired private CategoryRepository categorieRepository;
    @Autowired private FilmRepository filmRepository;
    @Autowired private ProjectionRepository projectionRepository;
    @Autowired private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
    	System.out.println("################### helloooooooooo");
        Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(name->{
            Ville ville=new Ville();
            ville.setName(name);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("MegaRama","IMAX","Founon","Chahrazad","Daouliz")
                    .forEach(name->{
                        Cinema cinema=new Cinema();
                        cinema.setName(name);
                        cinema.setNbrSalles(3+(int)(Math.random()*7));
                        cinema.setVille(v);
                        cinemaRepository.save(cinema);
                    });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(c->{
            for (int i=0;i<c.getNbrSalles();i++){
                Salle salle=new Salle();
                salle.setName("Salle "+(i+1));
                salle.setCinema(c);
                salle.setNbrPlace(15+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(s->{
            for (int i=0;i<s.getNbrPlace();i++){
                Place place=new Place();
                place.setNumPlace(i+1);
                place.setSalle(s);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat df=new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
            Seance seance=new Seance();
            try {
                seance.setHeureDebut(df.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }

    @Override
    public void initCategories() {
        Stream.of("Histoire","Actions","Fiction","Drama").forEach(cat->{
            Category categorie=new Category();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {

        double[] d=new double[]{1,1.5,2,2.5,3};
        Stream.of("12 Hommes en colere","Forrest Gump","Green Book","La Ligne Vert","Le Parrain","Seigneur des anneaux").forEach(name->{
            Film film=new Film();
            film.setTitre(name);
            film.setDuree(d[new Random().nextInt(d.length)]);
            film.setPhoto(name.replace(" ","").toLowerCase()+".jpg");
            film.setCategory(categorieRepository.findAll().get(new Random().nextInt(categorieRepository.findAll().size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prices=new double[]{30,40,50,60,70,80.100};
        List<Film> filmList= filmRepository.findAll();
        villeRepository.findAll().forEach(v->{
            v.getCinemas().forEach(c->{
                c.getSalles().forEach(s->{
                    int index=new Random().nextInt(filmList.size());
                    Film f=filmList.get(index);
                    seanceRepository.findAll().forEach(se->{
                        Projection p=new Projection();
                        p.setDateProjection(new Date());
                        p.setFilm(f);
                        p.setPrix(prices[new Random().nextInt(prices.length)]);
                        p.setSalle(s);
                        p.setSeance(se);
                        projectionRepository.save(p);
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p->{
            p.getSalle().getPlaces().forEach(place->{
                Ticket t=new Ticket();
                t.setPlace(place);
                t.setPrix(p.getPrix());
                t.setProjection(p);
                t.setReservee(false);
                ticketRepository.save(t);
            });
        });
    }


}
