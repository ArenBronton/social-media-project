import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PostManager {

    public static ArrayList<String[]> posts = new ArrayList<>();

    static {
        loadPosts();
    }

    public static void loadPosts() {
        try {

            BufferedReader in = new BufferedReader(new FileReader("dataFiles/postData.txt"));

            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                posts.add(line.split("]]/"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPost(String content, String poster, String time, String id) {
        posts.add(new String[]{content, poster, time, id});
    }

    public static void savePosts() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("dataFiles/postData.txt"));
            out.println("[CONTENT], [POSTER], [TIME], [ID]");
            for (String[] post : posts) {
                out.println(post[0] + "]]/" + post[1] + "]]/" + post[2] + "]]/" + post[3]);
            }

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removePost(String id) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i)[3].equals(id)) {
                posts.remove(i);
                System.out.println("Successfully removed post");
            }
        }
    }


}
