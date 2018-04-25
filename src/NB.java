import java.util.*;

public class NB {
    private final ArrayList<String> features;
    private int[] label;
    private int maxClasses;
    private int[][] possibleVals;
    private double[] initProb;
    private Map<Integer, Map<String, Double>> probabilities = new HashMap<>();
    private Map<String, Set<String>> choices;

    public NB(List<Map<String, Integer>> featuresList, int max) {
        this.maxClasses = max;
        this.features = new ArrayList<>();
        this.features.addAll(featuresList.get(0).keySet());
        this.possibleVals = new int[features.size()][];
        this.initProb = new double[features.size()];
        this.choices = new HashMap<>();
    }

    private void probability(int[] answers) {
        for (int i = 0; i < answers.length; i++) {
            this.initProb[answers[i]]++;
        }
        for (int i = 0; i < this.initProb.length; i++) {
            this.initProb[i] = this.initProb[i] / answers.length;
        }
    }

    private void getAllPossiblities(Map<String, Integer> featureMap, int answer) {

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
        probability(answers);
        int count = 0;
        int max = 0;

        for (int i = 0; i < featuresList.size(); i++) {
            getAllPossiblities(featuresList.get(i), answers[i]);
        }
        System.out.println(choices);

        for (int i = 0; i < featuresList.size(); i++) {
            getAllPossiblities(featuresList.get(i), answers[i]);
        }
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
