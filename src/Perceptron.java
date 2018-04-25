import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {

    private Map<Integer, Map<String, Double>> weights = new HashMap<>();
    private ArrayList<String> features;
    private ArrayList<Integer> classes;
    final double learningRate = 0.2;

    public Perceptron(ArrayList<Integer> classes, Set<String> features) {
        this.classes = classes;
        this.features.addAll(features);
        Random rand = new Random();
        Map<String, Double> temp = new HashMap<>();
        for (String feat : features)
            temp.put(feat, ThreadLocalRandom.current().nextDouble(-1, 1)); //generate random weights for all features

        for (Integer s : classes)
            weights.put(s, temp);
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {

        for (int a = 0; a < 100; a++) {
            for (int i = 0; i < featuresList.size(); i++) {
                predictTrain(featuresList.get(i), answers[i]);
            }
        }
    }

    private void predictTrain(Map<String, Integer> featuresList, int answer) {
        boolean val = false;
        for (Integer aClass : classes) {
            val = predictClass(aClass, featuresList) >= 0;
            if (aClass == answer)
                if (!val)
                    updateWeights(aClass, Math.abs(aClass - answer));
                else if (val)
                    updateWeights(aClass, -Math.abs(aClass - answer));
        }
    }

    public int predict(Map<String, Integer> featuresList) {
        int max[] = {Integer.MIN_VALUE, 0};
        int curTotal;
        for (int i = 0; i < classes.size(); i++) {
            curTotal = predictClass(classes.get(i), featuresList);
            if (curTotal > max[0]) {
                max[0] = curTotal;
                max[1] = i;
            }
        }
        return max[1];
    }

    private int predictClass(Integer classStr, Map<String, Integer> featureMap) {
        int total = 0;
        for (String feature : features) {
            total += featureMap.get(feature) * (weights.get(classStr)).get(feature);
        }
        return total;
    }

    private void updateWeights(int classA, int error) {
        for (int i = 0; i < features.size(); i++) {
            weights.get(classA).put(features.get(i), weights.get(classA).get(i) + (learningRate*error));
        }
    }
}
