import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        loadImages.getImages("data/digitdata/trainingimages");
        loadImages.getAnswers("data/digitdata/traininglabels");
    }
}
