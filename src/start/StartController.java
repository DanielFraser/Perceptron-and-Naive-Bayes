package start;

import algorithms.NB;
import algorithms.Perceptron;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.Features;
import utility.loadImages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class StartController {

    @FXML
    private ComboBox ml;

    @FXML
    private ComboBox item;

    @FXML
    private ComboBox percent;

    @FXML
    private ComboBox model;

    @FXML
    private ComboBox data;

    @FXML
    private Label report;

    @FXML
    private Label percStatus;

    @FXML
    private Label nbStatus;

    @FXML
    private TextField images;

    private Perceptron perc;

    private NB nb;

    List<Map<String, Integer>> featureList;

    /**
     * Start.
     *
     * @param mainStage the main stage
     *                  start called in main, since its the initial screen in the program
     */
    public void start(Stage mainStage) {
        ml.getItems().addAll("Perceptron", "Naive Bayes");
        ml.getSelectionModel().selectFirst();
        item.getItems().addAll("Digits", "Face");
        item.getSelectionModel().selectFirst();
        percent.getItems().addAll(percentages());
        percent.getSelectionModel().selectFirst();
        model.getItems().addAll("Perceptron", "Naive Bayes");
        model.getSelectionModel().selectFirst();
        data.getItems().addAll("Digits Validation", "Digits Test", "Face Validation", "Face Test");
        data.getSelectionModel().selectFirst();
    }

    private ArrayList<String> percentages() {
        ArrayList<String> percents = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            percents.add((i * 10) + "%");
        }
        return percents;
    }

    public void train(ActionEvent E) throws IOException {
        boolean face = item.getSelectionModel().getSelectedItem().equals("Face");
        char algo = ml.getSelectionModel().getSelectedItem().equals("Perceptron") ? 'p' : 'n';
        String per = String.valueOf(percent.getSelectionModel().getSelectedItem());
        double percents = Double.valueOf(per.substring(0, per.indexOf('%'))) / 100;
        trainAlgorithms(face, algo, percents);
    }

    private void trainAlgorithms(boolean face, char algo, double percent) throws IOException {
        char[][][] images;
        int[] answers;
        int max = face ? 1 : 9;
        String imageTrain, imageAnswers;
        if (face) {
            imageTrain = "data/facedata/facedatatrain";
            imageAnswers = "data/facedata/facedatatrainlabels";
        } else {
            imageTrain = "data/digitdata/trainingimages";
            imageAnswers = "data/digitdata/traininglabels";
        }
        images = loadImages.getImages(imageTrain, percent); //load digit training
        answers = loadImages.getAnswers(imageAnswers, percent);
        if (algo == 'p')
            featureList = Features.createBasicFeatures(images);
        else if (face)
            featureList = Features.addCols(images);
        else
            featureList = Features.allFeatures(images);
        if (algo == 'p') {
            perc = new Perceptron(featureList, max, 20);
            perc.train(featureList, answers);
            percStatus.setText(String.format("trained on %s at %.0f%%", (face ? "face" : "digits"), (percent * 100)));
        } else {
            nb = new NB(featureList, max);
            nb.train(featureList, answers); //train the perceptron
            nbStatus.setText(String.format("trained on %s at %d%%", (face ? "face" : "digits"), Math.round(percent * 100)));
        }
    }

    public void test(ActionEvent E) throws IOException {
        String dataStr = (String) data.getSelectionModel().getSelectedItem();
        char algo = model.getSelectionModel().getSelectedItem().equals("Perceptron") ? 'p' : 'n';
        if (images.getText().equals("-1"))
            testAllAlgorithm(dataStr, algo);
        else {
            int index = 0;
            if (images.getText().equals("-2"))
                index = -2;
            else
                index = Integer.parseInt(images.getText());
            predict(index, dataStr, algo);
        }
    }

    private void predict(int index, String dataStr, char algo) throws IOException {
        String imageTrain, imageAnswers;
        if (dataStr.startsWith("Face") && dataStr.endsWith("Test")) {
            imageTrain = "data/facedata/facedatatest";
            imageAnswers = "data/facedata/facedatatestlabels";
        } else if (dataStr.startsWith("Face") && dataStr.endsWith("Validation")) {
            imageTrain = "data/facedata/facedatavalidation";
            imageAnswers = "data/facedata/facedatavalidationlabels";
        } else if (dataStr.startsWith("Digit") && dataStr.endsWith("Test")) {
            imageTrain = "data/digitdata/testimages";
            imageAnswers = "data/digitdata/testlabels";
        } else {
            imageTrain = "data/digitdata/validationimages";
            imageAnswers = "data/digitdata/validationlabels";
        }

        char[][][] images = loadImages.getImages(imageTrain, 1); //load digit training
        int[] answers = loadImages.getAnswers(imageAnswers, 1);
        if (index == -2)
            index = new Random().nextInt(answers.length);
        List<Map<String, Integer>> featureList;
        if (algo == 'p')
            featureList = Features.createBasicFeatures(images);
        else if (dataStr.startsWith("Face"))
            featureList = Features.addCols(images);
        else
            featureList = Features.allFeatures(images);
        int total;
        if (algo == 'p') {
            total = perc.predict(featureList.get(index));
        } else {
            total = nb.predictClass(featureList.get(index));
        }
        report.setText(String.format("%s reports the image as %d\nand the actual answer is %d", algo == 'p' ? "Perceptron" : "Naive Bayes", total, answers[index]));
    }

    private void testAllAlgorithm(String dataStr, char algo) throws IOException {
        String imageTrain, imageAnswers;
        if (dataStr.startsWith("Face") && dataStr.endsWith("Test")) {
            imageTrain = "data/facedata/facedatatest";
            imageAnswers = "data/facedata/facedatatestlabels";
        } else if (dataStr.startsWith("Face") && dataStr.endsWith("Validation")) {
            imageTrain = "data/facedata/facedatavalidation";
            imageAnswers = "data/facedata/facedatavalidationlabels";
        } else if (dataStr.startsWith("Digit") && dataStr.endsWith("Test")) {
            imageTrain = "data/digitdata/testimages";
            imageAnswers = "data/digitdata/testlabels";
        } else {
            imageTrain = "data/digitdata/validationimages";
            imageAnswers = "data/digitdata/validationlabels";
        }

        char[][][] images = loadImages.getImages(imageTrain, 1); //load digit training
        int[] answers = loadImages.getAnswers(imageAnswers, 1);
        List<Map<String, Integer>> featureList;
        if (algo == 'p')
            featureList = Features.createBasicFeatures(images);
        else if (dataStr.startsWith("Face"))
            featureList = Features.addCols(images);
        else
            featureList = Features.allFeatures(images);
        int total = 0;
        if (algo == 'p') {
            for (int i = 0; i < featureList.size(); i++) {
                total += perc.predict(featureList.get(i)) == answers[i] ? 1 : 0;
            }
        } else {
            total = nb.predictALL(featureList, answers);
        }
        report.setText(String.format("%s reports an accuracy of %.1f%%", algo == 'p' ? "Perceptron" : "Naive Bayes", ((double) total / featureList.size()) * 100));
    }
}
