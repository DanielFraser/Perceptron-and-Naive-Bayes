import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Perceptron implements Serializable {

    private Map<Integer, Map<String, Double>> weights = new HashMap<>();
    private ArrayList<String> features;
    private int[] w0;
    private int epoch, max;

    public Perceptron(List<Map<String, Integer>> featuresList, int max, int epoch) {
        this.epoch = epoch;
        this.w0 = new int[max + 1]; //initial weight
        this.max = max;
        this.features = new ArrayList<>();
        this.features.addAll(featuresList.get(0).keySet());
        Map<String, Double> temp = new HashMap<>();
        for (int i = 0; i <= max; i++) {
            temp = new HashMap<>();
            for (String feat : features)
                temp.put(feat, 0.0);//new Random().nextDouble() * 2 - 1); //generate random weights for all features
            weights.put(i, temp);
        }
    }

    void train(List<Map<String, Integer>> featuresList, int[] answers) {
        //System.out.println(featuresList.size());
        boolean noChanges = false;
        for (int a = 0; a < this.epoch && !noChanges; a++) {
            for (int i = 0; i < featuresList.size(); i++) {
                noChanges = predictTrain(featuresList.get(i), answers[i]) || noChanges;
            }
        }
    }

    private boolean predictTrain(Map<String, Integer> features, int answer) {
        int predictedVal = predict(features);
        if (predictedVal != answer) {
            updateWeights(predictedVal, features, answer);
            return false;
        }
        return true;
    }

    int predict(Map<String, Integer> featuresList) {
        int max[] = {Integer.MIN_VALUE, 0};
        int curTotal;
        for (int i = 0; i <= this.max; i++) {
            curTotal = predictClass(i, featuresList);
            if (curTotal > max[0]) {

                max[0] = curTotal;
                max[1] = i;
            }
        }
        return max[1];
    }

    private int predictClass(Integer classInt, Map<String, Integer> featureMap) {
        int total = 0;
        for (String feature : features) {
            total += featureMap.get(feature) * weights.get(classInt).get(feature);
        }
        return total + w0[classInt];
    }

    private void updateWeights(int classA, Map<String, Integer> correct, int answer) {
        for (String feature : features) {
            weights.get(classA).put(feature, weights.get(classA).get(feature) + correct.get(feature));
            w0[classA] -= 1;

            weights.get(answer).put(feature, weights.get(answer).get(feature) + correct.get(feature));
            w0[answer] += 1;
        }
    }

    void printWeights() {
        for (int i = 0; i <= this.max; i++) {
            System.out.println("i: " + i + " w0: " + this.w0[i] + " " + weights.get(i));
        }
    }
}
