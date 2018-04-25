import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        char[][][] images = loadImages.getImages("data/digitdata/trainingimages");
        int[] answers = loadImages.getAnswers("data/digitdata/traininglabels");
        List<Map<String, Integer>> featureList = Features.createBasicFeatures(images);
        Perceptron perc = new Perceptron(featureList, 9, 100);
        //System.out.println(featureList.get(0));
        perc.train(featureList, answers);

        images = loadImages.getImages("data/digitdata/validationimages");
        answers = loadImages.getAnswers("data/digitdata/validationlabels");
        featureList = Features.createBasicFeatures(images);
        System.out.println(perc.predict(featureList.get(0)));
        System.out.println(answers[0]);
        System.out.println("Done!");
    }
}
