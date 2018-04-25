import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        char[][][] images = loadImages.getImages("data/digitdata/trainingimages");
        int[] answers = loadImages.getAnswers("data/digitdata/traininglabels");
        List<Map<String, Integer>> featureList = Features.createBasicFeatures(images);
        NB nb = new NB(featureList, 9);
        nb.train(featureList, answers);
        //System.out.println(featureList.get(0));
        //perc.train(featureList, answers);

//        images = loadImages.getImages("data/digitdata/validationimages");
//        answers = loadImages.getAnswers("data/digitdata/validationlabels");
//        featureList = Features.createBasicFeatures(images);
//        int total = 0;
//        for(int i = 0; i < featureList.size(); i++)
//        {
//            total += perc.predict(featureList.get(i)) == answers[i] ? 1:0;
//        }
//        System.out.println(total);
//        System.out.println("Done!");
    }
}
