package com.ws.rest.main;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.riversun.xternal.simpleslackapi.SlackUser;

public class App {
	
 	private static final String SLACK_BOT_API_TOKEN= ""; // JARDO: xoxb-351724196599-pOIrz1AtVUnu82sth4Ltp6or JABOT: xoxb-344268963408-0koYbCFQtW1AO5aDTkihGi5J
	private static final Client client = ClientBuilder.newClient();
	private static WebTarget webTarget; 
	private static boolean nombreCancion = false;//true cuando proximo input de usuario es nombre de cancion
	private final static SlackletService slackService = new SlackletService(SLACK_BOT_API_TOKEN); 

	public static void main(String[] args) throws IOException {
		
//		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//
//        Server jettyServer = new Server(8080);
//        jettyServer.setHandler(context);
//        
//        ServletHolder jerseyServlet = context.addServlet(
//             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
//        jerseyServlet.setInitOrder(0);
//
//        // Tells the Jersey Servlet which REST service/class to load.
//        jerseyServlet.setInitParameter(
//           "jersey.config.server.provider.classnames",
//           String.class.getCanonicalName());
//        
//        try {
//            jettyServer.start();
//            System.out.println("Servidor corriendo...");
//            jettyServer.join();
//        } catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//            jettyServer.destroy();
//        }
		
//		slackService.addSlacklet( new Slacklet() {
//			@Override
//			public void onDirectMessagePosted(SlackletRequest req, SlackletResponse resp) {
//
//				SlackUser slackUser = req.getSender();
//				String userInputText = req.getContent();
//				String botOutputText = "";
//
//				// clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
//				try {
//					//web = client
//					//		.resource("http://restjr.mybluemix.net/rest/services/response/"+slackUser.getId()+"/"+URLEncoder.encode(userInputText, "UTF-8"));
//					webTarget = client.target("http://restjr.mybluemix.net/rest/services/response/"+slackUser.getId()+"/"+URLEncoder.encode(userInputText, "UTF-8"));
//					Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
//					botOutputText = invocationBuilder.get(String.class);
//					
//					System.out.println(botOutputText);
//					if(botOutputText.equals("Qué canción querés escuchar?") || botOutputText.equals("Qué tema te gustaría escuchar?")) {
//						webTarget = client.target("http://restjr.mybluemix.net/rest/services/login");
//						Invocation.Builder invocationBuilder1 = webTarget.request(MediaType.TEXT_PLAIN);
//						slackService.sendDirectMessageTo(slackUser, "buenísimo! Ya me pongo con esto, pero un detalle: previamente voy a necesitar que ingreses tus credenciales entrando a <"+new URI(invocationBuilder1.get(String.class)).toString()+"|este link>");
//						Thread.sleep(new Long(15000));
//						botOutputText ="Perfecto "+slackUser.getRealName()+"! Ahora que estás loggueado."+botOutputText;
//					}
//
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				slackService.sendDirectMessageTo(slackUser,botOutputText);
//
//			}
//
//		});
//
//		slackService.start();
	}
	
	public void initMethod() {
		System.out.println("Servidor correindo...");
	}
	
	public SlackletService getService() {
		return this.slackService;
	}

}

/*
 	private static final String SLACK_BOT_API_TOKEN = "xoxb-344268963408-0koYbCFQtW1AO5aDTkihGi5J";
	private static final Client client = Client.create();
	private static final ClientConfig clientConfig = new DefaultClientConfig();
	private static WebResource web;
	private static String userSong = "";
	private static boolean nombreCancion = false;//true cuando proximo input de usuario es nombre de cancion
	private final static SlackletService slackService = new SlackletService(SLACK_BOT_API_TOKEN); 

	public static void main(String[] args) throws IOException {  

		slackService.addSlacklet(new Slacklet() {
			@Override
			public void onDirectMessagePosted(SlackletRequest req, SlackletResponse resp) {

				SlackUser slackUser = req.getSender();
				String userInputText = req.getContent();
				String botOutputText = "";

				clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
				try {
					web = client
							.resource("http://restjr.mybluemix.net/rest/services/response/"+slackUser.getId()+"/"+URLEncoder.encode(userInputText, "UTF-8"));
					botOutputText = web.get(String.class);
					System.out.println(botOutputText);
					if(botOutputText.equals("Qué canción querés escuchar?") || botOutputText.equals("Qué tema te gustaría escuchar?")) {
						web = client.resource("http://restjr.mybluemix.net/rest/services/login");
						slackService.sendDirectMessageTo(slackUser, "buenísimo! Ya me pongo con esto, pero un detalle: previamente voy a necesitar que ingreses tus credenciales entrando a <"+new URI(web.get(String.class)).toString()+"|este link>");
						Thread.sleep(new Long(15000));
						botOutputText ="Perfecto "+slackUser.getRealName()+"! Ahora que estás loggueado."+botOutputText;
					}

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UniformInterfaceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientHandlerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				slackService.sendDirectMessageTo(slackUser,botOutputText);

			}

		});

		slackService.start();
		
		public SlackletService getService() {
		return this.slackService;
	}

*/
