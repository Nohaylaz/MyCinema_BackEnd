package org.glsid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj",types = {org.glsid.cinema.entities.Ticket.class})
public interface ProjectionTicket {

	public Long getId();
    public String getNomClient();
    public double getPrix();
    public int getCodePayment();
    public boolean getReservee();
    public Place getPlace();
	
}
