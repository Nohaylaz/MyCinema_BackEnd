package org.glsid.cinema.dao;

import org.glsid.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RepositoryRestResource
@CrossOrigin("*")
public interface TicketRepository
        extends JpaRepository<Ticket, Long> {
}

