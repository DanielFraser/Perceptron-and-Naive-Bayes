/*
 * We take in the images from the files and extract features to pass into
 * the perceptron and naive bayes
 *
 * make sure result is returned as List<Map<String, Integer>> since Maps allow us to
 * add and remove features with minimal code modification
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Features {

    public static List<Map<String, Integer>> createBasicFeatures(char[][][] images) {
        List<Map<String, Integer>> featureList = new ArrayList<>();
        Map<String, Integer> temp;
        for (int i = 0; i < images.length; i++) {
            temp = new HashMap<>();
            for (int j = 0; j < images[0].length; j++) {
                for (int k = 0; k < images[0][0].length; k++) {
                    temp.put("[" + j + "," + k + "]", images[i][j][k] == '#' ? 2 : images[i][j][k] == '+' ? 1 : 0);
                }
            }
            featureList.add(temp);
        }
        return featureList;
    }
}
