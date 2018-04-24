import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {

    private Map<Integer, Map<String, Double>> weights = new HashMap<>();
    private ArrayList<String> classes, features;

    public Perceptron(ArrayList<String> classes, Set<String> features) {
        this.classes = classes;
        this.features.addAll(features);
        Random rand = new Random();
        Map<String, Double> temp = new HashMap<>();
        for (String feat : features)
            temp.put(feat, ThreadLocalRandom.current().nextDouble(-1, 1)); //generate random weights for all features

        for (String s : classes)
            weights.put(Integer.valueOf(s), temp);
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {
        Map<String, Integer> curFeats;
        int error;
        double curVal = 0;
        for (int a = 0; a < 100; a++) {
            for (int i = 0; i < featuresList.size(); i++) {
                curVal = 0;
                curFeats = featuresList.get(i);
                for (String feature : features) {
                    curVal += curFeats.get(feature) * (weights.get(answers[i])).get(feature);
                }
                error = curVal >= 0 ? 1 : 0;

                updateWeights(error);
            }
        }
    }

    public boolean predict(Map<String, Integer> featuresList, int answer) {
        for (String aClass : classes) {
            predictClass(aClass, featuresList, answer);
        }
        return false;
    }

    private void predictClass(String classStr, Map<String, Integer> featureMap, int answer) {
        int total = 0;
        for (String feature : features) {
            total += featureMap.get(feature) * (weights.get(answer)).get(feature);
        }
        //if()
    }

    private void updateWeights(int error) {

    }
}
