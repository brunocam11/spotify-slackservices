package com.ws.rest.spotifyservice;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.player.PauseUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

@Path("/")
public class SpotifyResource {

	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback");

	private final static String clientId = "bb958d9a732349ac882cb86eec2153a8";

	private final static String clientSecret = "7f46cce8d89b4d119f07383046a4444b";

	private static SpotifyApi spotifyApi = new SpotifyApi.Builder()

			.setClientId(clientId)

			.setClientSecret(clientSecret)

			.setRedirectUri(redirectUri)

			.build();

	private static String respuestaASlack = "";

	private static ArrayList<String> idsCancionesEnviadas = new ArrayList <String>();

	@GET
	@Path("prueba/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNombre(@PathParam("nombre") String nombre, @QueryParam("id") int id) {

		return id+" - "+nombre;
	}
	
	@GET
	@Path("buscarCancion")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarCancion(@QueryParam("track") String track, @QueryParam("accessToken") String accessToken) {
				
		spotifyApi.setAccessToken(accessToken);
		
		SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(track).build();
		respuestaASlack = "";

		try {
			Paging <Track> trackPaging = searchTrackRequest.execute();
			Track[] tracks = trackPaging.getItems();
			
			int i=1;
			for (Track t : tracks) { 
				if (i == 4) {
					break;
				}
				respuestaASlack += i + " - " + t.getName() + " - " + t.getAlbum().getArtists()[0].getName() + "\n";
				idsCancionesEnviadas.add(t.getId());
				i+=1;
			}

		} catch (SpotifyWebApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return respuestaASlack;
	}
	
	// Los de abajo no estan hechos 
	
	@PUT
	@Path("reproducir")
	public void reproducirCancion (int numeroRecibido, @QueryParam("accessToken") String accessToken) throws SpotifyWebApiException, IOException{

		String idCancion = idsCancionesEnviadas.get(numeroRecibido-1);

		StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi

				.startResumeUsersPlayback()

				.uris(new JsonParser().parse("[\"spotify:track:"+idCancion+"\"]").getAsJsonArray())

				.build();

		startResumeUsersPlaybackRequest.execute();

	}

	@PUT
	@Path("/pausar")
	public void pausarReproduccion () throws SpotifyWebApiException, IOException {

		PauseUsersPlaybackRequest pauseUserPlaybackRequest = spotifyApi.pauseUsersPlayback().build();

		pauseUserPlaybackRequest.execute();

	}

}
