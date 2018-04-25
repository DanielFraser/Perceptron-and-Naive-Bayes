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
        for (int a = 0; a < epoch && !noChanges; a++) {
            noChanges = false;
            for (int i = 0; i < featuresList.size(); i++) {
                noChanges = noChanges || predictTrain(featuresList.get(i), answers[i]);
            }
        }
    }

    private boolean predictTrain(Map<String, Integer> featuresList, int answer) {
        boolean change = false;
        boolean val;
        for (Integer aClass : classes) {
            val = predictClass(aClass, featuresList) >= 0;
            if (aClass == answer)
                if (!val) {
                    updateWeights(aClass, Math.abs(aClass - answer));
                    change = true;
                } else if (val) {
                    updateWeights(aClass, -Math.abs(aClass - answer));
                    change = true;
                }
        }
        return change;
    }

    public int predict(Map<String, Integer> featuresList) {
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

    private void updateWeights(int classA, int error) {
        for (int i = 0; i < features.size(); i++) {
            double learningRate = 0.2;
            weights.get(classA).put(features.get(i), weights.get(classA).get(features.get(i)) + (learningRate * error));
            w0[classA] += error > 0 ? 1 : -1;
        }
    }
}
