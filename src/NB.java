import java.util.*;

public class NB {
    private final ArrayList<String> features;
    private int[] label;
    private int maxClasses;
    private double[] initProb;
    private int[] total;
    private Map<Integer, Map<String, Map<Integer, Double>>> probabilities = new HashMap<>();
    private Map<String, Set<String>> choices;

    public NB(List<Map<String, Integer>> featuresList, int max) {
        this.maxClasses = max; //0-max classes
        this.features = new ArrayList<>(); //contains a list of all features
        this.features.addAll(featuresList.get(0).keySet()); //updates features from map
        this.initProb = new double[features.size()]; //initial probability of each class (with no features involved)
        this.total = new int[features.size()]; //initial probability of each class (with no features involved)
        this.choices = new HashMap<>(); //the choices for every single feature
        for (int i = 0; i <= max; i++) {
            this.probabilities.put(i, new HashMap<>()); //add in new class
            for (String feature : this.features) {
                this.probabilities.get(i).put(feature, new HashMap<>()); //add in features for each class
            }
        }
    }

    /**
     * @param answers the array of the training answers
     */
    private void probability(int[] answers) {
        for (int answer : answers) { //goes through each answer and adds 1 to corresponding class
            this.initProb[answer]++;
            this.total[answer]++;
        }
        for (int i = 0; i < this.initProb.length; i++) { //converts the totals to probabilities
            this.initProb[i] = this.initProb[i] / answers.length;
        }
    }

    private void fillInProbs(int aClass, Map<String, Integer> features) {
        //gets total of each type based on number
        int choice = 0;
        for (String feature : this.features) {
            //System.out.println(feature);
            choice = features.get(feature);
            if (this.probabilities.get(aClass).get(feature).containsKey(choice)) {
                this.probabilities.get(aClass).get(feature).put(choice, this.probabilities.get(aClass).get(feature).get(choice) + 1);
            } else {
                this.probabilities.get(aClass).get(feature).put(choice, 1.0);
            }
        }
    }

    private void finishProb()
    {
        for (int i = 0; i <= this.maxClasses; i++) {
            for (String feature : this.features) {
                for (Integer s : this.probabilities.get(i).get(feature).keySet()) {
                        this.probabilities.get(i).get(feature).put(s, this.probabilities.get(i).get(feature).get(s)/ this.total[i]);
                        System.out.println(this.probabilities.get(i).get(feature).get(s));
                }
            }
        }
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {
        //create initial probabilities
        probability(answers);

        //get all possible choices for every feature
        for (int i = 0; i < featuresList.size(); i++) {
            fillInProbs(answers[i], featuresList.get(i));
        }
        finishProb();
        System.out.println("Done training");

        //need to create possibilities for each (feature|class) for each features value
    }
}
