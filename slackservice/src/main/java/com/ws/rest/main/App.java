package com.ws.rest.main;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;

import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.riversun.xternal.simpleslackapi.SlackUser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class App {

    private static final String SLACK_BOT_API_TOKEN = "xoxb-351724196599-pOIrz1AtVUnu82sth4Ltp6or";
    private static final Client client = Client.create();
    private static final ClientConfig clientConfig = new DefaultClientConfig();
    private static WebResource web;

    public static void main(String[] args) throws IOException {
    	web = client.resource("https://restjr.mybluemix.net/rest/services/login");
    	// web = client.resource("localhost:8080/rest/services/login");

		try {
			Desktop.getDesktop().browse(new URI(web.get(String.class)));
		} catch (UniformInterfaceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientHandlerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        final SlackletService slackService = new SlackletService(SLACK_BOT_API_TOKEN);    
        slackService.addSlacklet(new Slacklet() {
        	@Override
            public void onDirectMessagePosted(SlackletRequest req, SlackletResponse resp) {
            	
                SlackUser slackUser = req.getSender();
                String userInputText = req.getContent();
                System.out.println(new Date()+" - "+ slackUser.getUserName() + ": " + userInputText);
                if (userInputText.equals("Quiero escuchar musica")) {
                	System.out.println("entro");
                }
                clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
				try {
					
					web = client
							.resource("https://restjr.mybluemix.net/rest/services/response/"+slackUser.getId()+"/"+URLEncoder.encode(userInputText, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		       
                String botOutputText = web.get(String.class);
                //(botOutputText.equals(""))
                slackService.sendDirectMessageTo(slackUser,botOutputText);
                
            }

        });
        
        slackService.start();
    }

}
