package punt;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by james on 2/27/14.
 */
@Path("/punt")
public class Punter {
    private static final String conf1 = "/etc/punt01.properties";
    private static final String conf2 = "/etc/punt02.properties";

    private static Gson gson = new Gson();

    public Punter() {

    }

    private Properties getProperties(String filename) throws IOException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = Punter.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return null;
            }
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop;
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class RMessage {
        private String key = null;
        private String val = null;
        public RMessage(String  key, String val) {
            this.key = key;
            this.val = val;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String puntNow() {
        /*
        This is pretty bad as we'll be hitting hte file system and reading properties EVERY time
        we punt, but it doesn't matter for this small test.  we're testing CHEF/PUPPET/etc...
        */
        try {
            List l = new ArrayList();
            Properties p = getProperties(conf1);
            String v= p.getProperty("test", "placeholder");
            l.add(new RMessage("conf1", v));
            Properties p1 = getProperties(conf2);
            v=p.getProperty("test", "placeholder");
            l.add(new RMessage("conf2", v));
            return gson.toJson(l);
        }
        catch (IOException ex){
            return gson.toJson(new RMessage("error", ex.getLocalizedMessage()));
        }

    }
}
