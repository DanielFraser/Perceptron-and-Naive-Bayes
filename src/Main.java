import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        loadImages.getImages("data/digitdata/trainingimages");
        System.out.println(Arrays.toString(loadImages.getAnswers("data/digitdata/traininglabels")));
    }
}
