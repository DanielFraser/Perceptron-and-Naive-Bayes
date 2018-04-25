import java.util.*;

public class NB {
    private final ArrayList<String> features;
    private int[] label;
    private int maxClasses;
    private double[] initProb;
    private Map<Integer, Map<String, Double>> probabilities = new HashMap<>();
    private Map<String, Set<String>> choices;

    public NB(List<Map<String, Integer>> featuresList, int max) {
        this.maxClasses = max; //0-max classes
        this.features = new ArrayList<>(); //contains a list of all features
        this.features.addAll(featuresList.get(0).keySet()); //updates features from map
        this.initProb = new double[features.size()]; //initial probability of each class (with no features involved)
        this.choices = new HashMap<>(); //the choices for every single feature
    }

    /**
     *
     * @param answers the array of the training answers
     */
    private void probability(int[] answers) {
        for (int answer : answers) { //goes through each answer and adds 1 to corresponding class
            this.initProb[answer]++;
        }
        for (int i = 0; i < this.initProb.length; i++) { //converts the totals to probabilities
            this.initProb[i] = this.initProb[i] / answers.length;
        }
    }

    /**
     *
     * @param featureMap a map of features from an image
     */
    private void getAllPossiblities(Map<String, Integer> featureMap) {

        //goes through each feature and adds the image's feature value to the set of
        //the corresponding feature
        for (String feature : features) {
            if (choices.containsKey(feature))
                choices.get(feature).add(String.valueOf(featureMap.get(feature)));
            else {
                choices.put(feature, new HashSet<>());
                choices.get(feature).add(String.valueOf(featureMap.get(feature)));
            }

        }

        //probabilities.get(answer).get(feature)
    }

    public void train(List<Map<String, Integer>> featuresList, int[] answers) {
        //create initial probabilities
        probability(answers);

        //get all possible choices for every feature
        for (int i = 0; i < featuresList.size(); i++) {
            getAllPossiblities(featuresList.get(i));
        }
        System.out.println(choices);

        //need to create possibilities for each (feature|class) for each features value
//        /**
//         * loop to set up the size of possible outcomes (distinguish between digit and face)
//         */
//        for (int m = 0; m < label.length; m++) {
//            if (max < label[m]) {
//                max = label[m];
//            }
//        }
//        /**
//         * loop to calculate p(y) for true and false
//         */
//        for (int i = 0; i < max; i++) {
//            for (int j = 0; j < label.length; j++) {
//                if (label[j] == 0) {
//                    count++;
//                }
//            }
//            p_y_true[i] = count / label.length;
//            p_y_false[i] = (label.length - count) / label.length;
//            count = 0;
//        }

        /**
         *
         */

    }


}
