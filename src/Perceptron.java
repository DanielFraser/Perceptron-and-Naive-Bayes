import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Perceptron {

    private Map<Integer, Map<String, Double>> weights = new HashMap<>();
    private ArrayList<String> features;
    private List<Integer> classes;
    private int[] w0;
    private int epoch;

    public Perceptron(List<Map<String, Integer>> featuresList, int max, int epoch) {
        this.epoch = epoch;
        this.w0 = new int[max + 1]; //initial weight
        this.classes = IntStream.rangeClosed(0, max).boxed().collect(Collectors.toList());
        this.features = new ArrayList<>();
        this.features.addAll(featuresList.get(0).keySet());
        Random rand = new Random();
        Map<String, Double> temp = new HashMap<>();
        for (String feat : features)
            temp.put(feat, ThreadLocalRandom.current().nextDouble(-1, 1)); //generate random weights for all features

        for (Integer s : classes)
            weights.put(s, temp);
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {
        boolean noChanges = false;
        for (int a = 0; a < this.epoch && !noChanges; a++) {
            noChanges = false;
            for (int i = 0; i < featuresList.size(); i++) {
                noChanges = noChanges || predictTrain(featuresList.get(i), answers[i]);
            }
        }
    }

    private boolean predictTrain(Map<String, Integer> featuresList, int answer) {
        int predictedVal = predict(featuresList);
        if (predictedVal != answer) {
            updateWeights(predictedVal, featuresList, answer);
            return false;
        }
        return true;
    }

    int predict(Map<String, Integer> featuresList) {
        int max[] = {Integer.MIN_VALUE, 0};
        int curTotal;
        for (int i = 0; i < classes.size(); i++) {
            curTotal = predictClass(classes.get(i), featuresList);
            curTotal += w0[i];
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
            total += featureMap.get(feature) * (weights.get(classInt)).get(feature);
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
}
