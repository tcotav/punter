package punt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by james on 2/27/14.
 */
@Path("/punt")
public class Punter {
    private static final String conf1 = "/etc/punt01.properties";
    private static final String conf2 = "/etc/punt02.properties";

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String puntNow() {
        /*
        This is pretty bad as we'll be hitting hte file system and reading properties EVERY time
        we punt, but it doesn't matter for this small test.  we're testing CHEF/PUPPET/etc...
         */
        return null;
    }
}
