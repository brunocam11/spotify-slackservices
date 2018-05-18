package slack.slack;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletService;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.xternal.simpleslackapi.SlackUser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import junit.framework.Test;

public class Slack {

    private static final String SLACK_BOT_API_TOKEN = "xoxb-344268963408-geW1L0sAsEmlCmF9gEf2b0ON";
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
    }
    
    public SlackletService getService() {
    return this.slackService;
    }
}

