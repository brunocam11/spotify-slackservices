package com.ws.rest.slackservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class SlackResource {
	
	@GET
	@Path("/prueba/{nombre}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPrueba(@PathParam("nombre") String nombre, @QueryParam("id") int id ) {
		return id + " - " + nombre;
	}

}
