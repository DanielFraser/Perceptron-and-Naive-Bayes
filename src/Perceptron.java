import java.util.*;

public class Perceptron {

    private Map<String, Map<String, Double>> weights = new HashMap<>();
    private ArrayList<String> classes, features;

    public Perceptron(ArrayList<String> classes, Set<String> features) {
        this.classes = classes;
        this.features.addAll(features);
        Random rand = new Random();
        Map<String, Double> temp = new HashMap<>();
        for (String feat : features)
            temp.put(feat, rand.nextDouble()); //generate random weights for all features

        for (String s : classes)
            weights.put(s, temp);
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {
        weights.keySet();
        Map<String, Integer> curFeats;
        int globalError = 0, localError = 0;
        double curVal = 0;
        for (int a = 0; a < 100; a++) {
            for (int i = 0; i < featuresList.size(); i++) {
                curVal = 0;
                curFeats = featuresList.get(i);
                for (int j = 0; j < features.size(); j++) {
                    curVal += curFeats.get(features.get(j)) * (weights.get(answers[i])).get(features.get(j));
                }
                localError += curVal >= 0 ? 0 : 1;
            }
            globalError += localError;
        }

    }
}
