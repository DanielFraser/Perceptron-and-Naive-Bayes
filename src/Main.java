import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        //PerceptronEval();
        String imageTrain = "data/digitdata/trainingimages", imageAnswers = "data/digitdata/traininglabels";
        char[][][] images = loadImages.getImages(imageTrain, 1); //load digit training
        int[] answers = loadImages.getAnswers(imageAnswers, 1);
        List<Map<String, Integer>> featureList = Features.createBasicFeatures(images);
        NB nb = new NB(featureList,9);
        nb.train(featureList, answers);
    }

    /**
     * Loads images and trains the perceptron depending if we're doing face or not
     * @param face are we using face data or not?
     * @param percent how much data do we want
     * @return a trained perceptron
     * @throws IOException
     */
    private static Perceptron perceptronTrain(boolean face, double percent) throws IOException {
        char[][][] images;
        int[] answers;
        int max = 9;
        String imageTrain = "data/digitdata/trainingimages", imageAnswers = "data/digitdata/traininglabels";
        Perceptron perc;
        List<Map<String, Integer>> featureList;
        if(face)
        {
            imageTrain = "data/facedata/facedatatrain";
            imageAnswers = "data/facedata/facedatatrainlabels";
            max = 1;
        }
        images = loadImages.getImages(imageTrain, percent); //load digit training
        answers = loadImages.getAnswers(imageAnswers, percent);
        featureList = Features.createBasicFeatures(images);
        perc = new Perceptron(featureList, max, 5);
        perc.train(featureList, answers); //train the perceptron
        return perc; //return the perceptron
    }

    /**
     *
     * @param file do we want face validation/test or digit validation/test
     * @param percent percentage of data we want
     * @throws IOException
     */
    private static void PerceptronTest(String file, double percent) throws IOException {

        String filename = "", answerFile = null;
        boolean face = false;
        if (file.equalsIgnoreCase("dv")) { //digit validation
            filename = "data/digitdata/validationimages";
            answerFile = "data/digitdata/validationlabels";
        } else if (file.equalsIgnoreCase("dt")) { //digit test
            filename = "data/digitdata/testimages";
            answerFile = "data/digitdata/testlabels";
        } else if (file.equalsIgnoreCase("fv")) { //face validation
            filename = "data/facedata/facedatavalidation";
            answerFile = "data/facedata/facedatavalidationlabels";
            face = true;
        } else if (file.equalsIgnoreCase("ft")) { //face test
            filename = "data/facedata/facedatatest";
            answerFile = "data/facedata/facedatatestlabels";
            face = true;
        }

        Perceptron perc = perceptronTrain(face, percent);
        char[][][] images = loadImages.getImages(filename, percent);
        int[] answers = loadImages.getAnswers(answerFile, percent);
        List<Map<String, Integer>> featureList = Features.createBasicFeatures(images);
        int total = 0;
        for (int i = 0; i < featureList.size(); i++) {
            total += perc.predict(featureList.get(i)) == answers[i] ? 1 : 0;
        }
        String inner = String.format("%d%%", (int) Math.round(percent*100)); // creates "42%"
        System.out.printf("|%-4s%18d|%13d|%10d%%|\n", inner, featureList.size(), total, (int) (100*((double) total / featureList.size())));
        System.out.println("--------------------------------------------------");
    }

    /**
     * Used to test perceptron
     * @throws IOException
     */
    private static void PerceptronEval() throws IOException {
        String[] formats = {"dv"}; //, "fv"
        for (String format : formats) {
            System.out.printf("%s\n", format.equals("dt") ? "Digit Test" : "Face Test");
            System.out.println("--------------------------------------------------");
            System.out.printf("|%22s|%13s|%10s |\n", "Total images evaluated", "Total correct", "% correct");
            System.out.println("--------------------------------------------------");
            for (double i = 0.1; i <= 1; i += .1) { //go through all percentages
                PerceptronTest(format, i);
            }
        }
    }
}
