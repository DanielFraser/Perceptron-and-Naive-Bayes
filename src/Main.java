import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        PerceptronEval();
    }

    private static NB naiveTrain(boolean face, double percent) throws IOException {
        char[][][] images;
        int[] answers;
        int max = 9;
        String imageTrain = "data/digitdata/trainingimages", imageAnswers = "data/digitdata/traininglabels";
        NB nb;
        List<Map<String, Integer>> featureList;
        if (face) {
            imageTrain = "data/facedata/facedatatrain";
            imageAnswers = "data/facedata/facedatatrainlabels";
            max = 1;
        }
        images = loadImages.getImages(imageTrain, percent); //load digit training
        answers = loadImages.getAnswers(imageAnswers, percent);
        if (face)
            featureList = Features.addCols(images);
        else
            featureList = Features.allFeatures(images);
        nb = new NB(featureList, max);
        nb.train(featureList, answers); //train the perceptron
        return nb; //return the perceptron
    }

    /**
     * Loads images and trains the perceptron depending if they're doing face or not
     *
     * @param face    are we using face data or not?
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
        if (face) {
            imageTrain = "data/facedata/facedatatrain";
            imageAnswers = "data/facedata/facedatatrainlabels";
            max = 1;
        }
        images = loadImages.getImages(imageTrain, percent); //load digit training
        answers = loadImages.getAnswers(imageAnswers, percent);
        featureList = Features.createBasicFeatures(images);
        perc = new Perceptron(featureList, max, 200);
        perc.train(featureList, answers); //train the perceptron
        return perc; //return the perceptron
    }

    /**
     * @param file    do we want face validation/test or digit validation/test
     * @param percent percentage of data we want
     * @throws IOException
     */
    private static void NaiveTest(String file, double percent) throws IOException {

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

        NB nb = naiveTrain(face, percent);
        char[][][] images = loadImages.getImages(filename, 1);
        int[] answers = loadImages.getAnswers(answerFile, 1);
        List<Map<String, Integer>> featureList = Features.allFeatures(images); //Features.quadrants() createBasicFeatures allFeatures
        if (face)
            featureList = Features.addCols(images); //Features.quadrants() createBasicFeatures allFeatures
        int total = nb.predictALL(featureList, answers);
        String inner = String.format("%d%%", (int) Math.round(percent * 100)); // creates "42%"
        System.out.printf("|%-4s%18d|%13d|%10d%%|\n", inner, featureList.size(), total, (int) (100 * ((double) total / featureList.size())));
        System.out.println("--------------------------------------------------");
        //nb.printProbs();
    }

    /**
     * @param file    do we want face validation/test or digit validation/test
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
        char[][][] images = loadImages.getImages(filename, 1);
        int[] answers = loadImages.getAnswers(answerFile, 1);
        List<Map<String, Integer>> featureList = Features.createBasicFeatures(images); //Features.quadrants() createBasicFeatures allFeatures
        int total = 0;
        for (int i = 0; i < featureList.size(); i++) {
            total += perc.predict(featureList.get(i)) == answers[i] ? 1 : 0;
        }
        String inner = String.format("%d%%", (int) Math.round(percent * 100)); // creates "42%"
        System.out.printf("|%-4s%18d|%13d|%10d%%|\n", inner, featureList.size(), total, (int) (100 * ((double) total / featureList.size())));
        System.out.println("--------------------------------------------------");
    }

    /**
     * Used to test perceptron
     *
     * @throws IOException
     */
    private static void PerceptronEval() throws IOException {
        String[] formats = {"dt", "ft"};
        String[] ml = {"perc", "nb"};
        for (String m : ml) {
            for (String format : formats) {
                if (m.equals("perc"))
                    System.out.println("Perceptron:");
                else
                    System.out.println("Naive Bayes:");
                System.out.println("--------------------------------------------------");
                System.out.printf("%s\n", format.equals("dt") || format.equals("dv") ? "Digit Test" : "Face Test");
                System.out.println("--------------------------------------------------");
                System.out.printf("|%22s|%13s|%10s |\n", "Total images tested", "Total correct", "% correct");
                System.out.println("--------------------------------------------------");
                for (double i = 1; i <= 1; i += .1) { //go through all percentages
                    if (m.equals("perc"))
                        PerceptronTest(format, i);
                    else
                        NaiveTest(format, i);
                }
            }
        }
    }
}
