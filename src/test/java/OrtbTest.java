import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class OrtbTest {
    private static String requestBody = "";
    private static StringBuilder result;
    private static int responseCode;

    @Test
    public void ortbApiTest() {

        try (BufferedReader fileReader = new BufferedReader(new FileReader(new File
                ("src/test/java/requestBody.txt")))) {
            while (fileReader.ready()) {
                requestBody += fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            String baseUrl = "https://loopme.me/api/ortb/ads";
            URL url = new URL(baseUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(requestBody);
            osw.flush();
            osw.close();
            os.close();
            con.connect();

            result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            while (reader.ready()) {
                result.append(reader.readLine());
            }
            reader.close();

            responseCode = con.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseCode);
        System.out.println(result.toString());
        Assert.assertEquals(responseCode, 200, "Server does not work");
        Assert.assertEquals(result.toString(), result.toString());
    }
}
