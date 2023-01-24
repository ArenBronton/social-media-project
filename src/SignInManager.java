import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SignInManager {

    public static HashMap<String, String> userSignIns;

    static {
        userSignIns = new HashMap<String, String>();
        loadData();
    }


    public static void loadData() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("dataFiles/userSignIns.txt"));

            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                String[] pair = line.split("]]/");
                userSignIns.put(pair[0], pair[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String userID, String password) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("dataFiles/userSignIns.txt"));
            out.println("[USER ID], [PASSWORD]");

            // WRITE EXISTING DATA
            for (Map.Entry<String, String> entry : userSignIns.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                out.println(key + "]]/" + value);
            }

            // WRITE NEW DATA
            out.println(userID + "]]/" + password);
            userSignIns.put(userID, password);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
