package algorithms;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NB {
    private final ArrayList<String> features;
    private int maxClasses;
    private double[] initProb;
    private int[] total;
    private List<Map<String, Map<Integer, Double>>> probabilities = new ArrayList<>();

    public NB(List<Map<String, Integer>> featuresList, int max) {
        this.maxClasses = max; //0-max classes
        this.features = new ArrayList<>(); //contains a list of all features
        this.features.addAll(featuresList.get(0).keySet()); //updates features from map
        this.initProb = new double[max + 1]; //initial probability of each class (with no features involved)
        this.total = new int[max + 1]; //total of each class
        for (int i = 0; i <= max; i++) {
            this.probabilities.add(new HashMap<>()); //add in new class
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
            choice = features.get(feature);
            //totals all possible choices for each feature
            if (this.probabilities.get(aClass).get(feature).containsKey(choice)) {
                this.probabilities.get(aClass).get(feature).put(choice, this.probabilities.get(aClass).get(feature).get(choice) + 1);
            } else {
                this.probabilities.get(aClass).get(feature).put(choice, 1.0);
            }
        }
    }

    private void finishProb() {
        for (int i = 0; i <= this.maxClasses; i++) {
            for (String feature : this.features) {
                for (Integer s : this.probabilities.get(i).get(feature).keySet()) {
                    this.probabilities.get(i).get(feature).put(s, (this.probabilities.get(i).get(feature).get(s)) / this.total[i]); //converts all to conditional probability
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
    }

    /**
     * Predicts every item in the list
     *
     * @param featuresList
     * @param answers
     */
    public int predictALL(List<Map<String, Integer>> featuresList, int[] answers) {
        int totalCorrect = 0;
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < featuresList.size(); i++) {
            totalCorrect += predictClass(featuresList.get(i)) == answers[i] ? 1 : 0;
            temp.add(predictClass(featuresList.get(i)));
        }
        return totalCorrect;
    }

    /**
     * returns class with highest probability
     *
     * @param features
     * @return
     */
    public int predictClass(Map<String, Integer> features) {
        double total = 0, curTotal;
        List<Integer> tieBreaker = new ArrayList<>();
        int Aclass = 0;
        for (int i = 0; i <= this.maxClasses; i++) {
            curTotal = 1;
            for (String s : this.features) {
                if (this.probabilities.get(i).get(s).containsKey(features.get(s))) {
                    curTotal *= this.probabilities.get(i).get(s).get(features.get(s));
                }
                else { //find closest neighbor
                    int closest = Integer.MAX_VALUE;
                    for(Integer key : this.probabilities.get(i).get(s).keySet()){
                        if(Math.abs(features.get(s) - key) < Math.abs(features.get(s) - closest)){
                            closest = key;
                        }
                    }
                    curTotal *= this.probabilities.get(i).get(s).get(closest);
                }
            }
            curTotal *= initProb[i];
            if (curTotal > total) {
                total = curTotal;
                Aclass = i;
            }

        }
        return Aclass;
    }

    public void printProbs() {
        for (Map<String, Map<Integer, Double>> probability : this.probabilities) {
            for (String feature : this.features) {
                System.out.println("feature = " + feature + " : " + probability.get(feature));
            }
        }
    }
}
