package org.glsid.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.glsid.cinema.dao.CinemaRepository;
import org.glsid.cinema.dao.FilmRepository;
import org.glsid.cinema.dao.TicketRepository;
import org.glsid.cinema.entities.Film;
import org.glsid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private  FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping(value = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable (name ="id")Long id)throws Exception{
        Film film=filmRepository.findById(id).get();
        String photoName=film.getPhoto();
        File file=new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path= Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketFrom ticketFrom){
        List<Ticket> tickets=new ArrayList<>();
        ticketFrom.getTickets().forEach(id->{
            Ticket ticket=ticketRepository.findById(id).get();
            ticket.setNomClient(ticketFrom.getNomClient());
            ticket.setReservee(true);
            ticket.setCodePayment(ticketFrom.getCodePayment());
            ticketRepository.save(ticket);
            tickets.add(ticket);
        });
        return tickets;
    }
}


@Data
class TicketFrom{
    private String nomClient;
    private int codePayment;
    private List<Long> tickets=new ArrayList<>();
}