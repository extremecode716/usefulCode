import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

public class SendingTheHTTPrequest {
    public static void main(String[] args) {
//        JSONObject json = new JSONObject();
//        json.put("city","Mumbai");
//        json.put("country", "India");
//        String output = json.toString();

        try {
            URL my_url = new URL("http://URL");
            BufferedReader br = newBufferedReader(new InputStreamReader(my_url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                System.out.println(strTemp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
