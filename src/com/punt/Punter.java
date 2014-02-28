package com.punt;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    static Logger logger = Logger.getLogger(Punter.class);

    private static Gson gson = new Gson();

    public Punter() {

    }

    private Properties getProperties(String filename) throws IOException, FileNotFoundException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                throw new FileNotFoundException("file not found: " + filename);
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
    public String ok(){
        return "{'status':'ok'}";
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
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response puntNow() {
        /*
        This is pretty bad as we'll be hitting hte file system and reading properties EVERY time
        we com.punt, but it doesn't matter for this small test.  we're testing CHEF/PUPPET/etc...
        */
        try {
            List<RMessage> l = new ArrayList<RMessage>();
            Properties p = getProperties(conf1);
            String v= p.getProperty("test", "placeholder");
            logger.info("conf1 - " + v);
            l.add(new RMessage("conf1", v));
            Properties p1 = getProperties(conf2);
            v=p1.getProperty("test", "placeholder");
            l.add(new RMessage("conf2", v));
            logger.info("conf2 - " + v);
            return Response.ok(gson.toJson(l)).build();
        }
        catch (FileNotFoundException fnf) {
            logger.error(fnf.getMessage());
            return Response.ok( gson.toJson(new RMessage("error", fnf.getLocalizedMessage()))  ).build();
        }
        catch (IOException ex){
            logger.error(ex.getMessage());
            return Response.ok( gson.toJson(new RMessage("error", ex.getLocalizedMessage()))  ).build();
        }

    }
}
