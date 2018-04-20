import java.util.*;

public class Perceptron {

    private Map<String, Map<String, Double>> weights = new HashMap<>();

    public Perceptron(ArrayList<String> classes, Set<String> features)
    {
        Random rand = new Random();
        Map<String, Double> temp = new HashMap<>();
        for(String feat : features)
            temp.put(feat, rand.nextDouble()); //generate random weights for all features

        for(String s : classes)
            weights.put(s, temp);
    }

    public void train(int[][][] images, int[] output)
    {
        weights.keySet();
    }
}
