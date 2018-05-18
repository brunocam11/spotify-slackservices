package spotify.spotify;


import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;

import com.google.gson.JsonParser;

import com.wrapper.spotify.SpotifyApi;

import com.wrapper.spotify.SpotifyHttpManager;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import com.wrapper.spotify.model_objects.specification.Paging;

import com.wrapper.spotify.model_objects.specification.Track;

import com.wrapper.spotify.requests.data.player.PauseUsersPlaybackRequest;

import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;


public class Principal {

private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback");

final static String clientId = "bb958d9a732349ac882cb86eec2153a8";

final static String clientSecret = "7f46cce8d89b4d119f07383046a4444b";

static SpotifyApi spotifyApi = new SpotifyApi.Builder()

        .setClientId(clientId)

        .setClientSecret(clientSecret)

        .setRedirectUri(redirectUri)

        .build();

public static String respuestaASlack = "";

public static ArrayList<String> idsCancionesEnviadas = new ArrayList <String>();


public static void main (String [] args) throws SpotifyWebApiException, IOException{

System.out.println(retornoSpotify("Gente la"));

//reproducirCancion(2);

pausarReproduccion();

}


public static String retornoSpotify(String track){

SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(track).build();
respuestaASlack = "";
idsCancionesEnviadas.clear();

try {

Paging <Track> trackPaging = searchTrackRequest.execute();

Track[] tracks = trackPaging.getItems();

int i=1;

for (Track t : tracks){ 

if(i==4){

break;

}

respuestaASlack += i+" - "+t.getName()+" - "+t.getAlbum().getArtists()[0].getName()+"\n";

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


public static void reproducirCancion (int numeroRecibido) throws SpotifyWebApiException, IOException{

String idCancion = idsCancionesEnviadas.get(numeroRecibido-1);

StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi

          .startResumeUsersPlayback()

          .uris(new JsonParser().parse("[\"spotify:track:"+idCancion+"\"]").getAsJsonArray())

          .build();

startResumeUsersPlaybackRequest.execute();

}


public static void pausarReproduccion () throws SpotifyWebApiException, IOException {

PauseUsersPlaybackRequest pauseUserPlaybackRequest = spotifyApi.pauseUsersPlayback().build();

pauseUserPlaybackRequest.execute();

}


public static SpotifyApi getSpotifyApi() {
	return spotifyApi;
}


public static void setSpotifyApi(SpotifyApi spotifyApi) {
	Principal.spotifyApi = spotifyApi;
}



}

