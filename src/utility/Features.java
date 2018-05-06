package utility;/*
 * We take in the images from the files and extract features to pass into
 * the perceptron and naive bayes
 *
 * make sure result is returned as List<Map<String, Integer>> since Maps allow us to
 * add and remove features with minimal code modification
 *
 * IDEAS:
 *
 * digits:
 *  circles
 *  lines
 *
 * face:
 *  any ideas?
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Features {

    public static List<Map<String, Integer>> allFeatures(char[][][] images) {
        List<Map<String, Integer>> pixels = createBasicFeatures(images);
        List<Map<String, Integer>> quadrants = quadrants(images);
        List<Map<String, Integer>> cols = addCols(images);
        List<Map<String, Integer>> rows = addRows(images);
        for (int i = 0; i < images.length; i++) {
            pixels.get(i).putAll(quadrants.get(i));
            pixels.get(i).putAll(cols.get(i));
           pixels.get(i).putAll(rows.get(i));
        }
        return pixels;
    }

    public static List<Map<String, Integer>> createBasicFeatures(char[][][] images) {
        List<Map<String, Integer>> featureList = new ArrayList<>();
        Map<String, Integer> temp;
        for (char[][] image : images) {
            temp = new HashMap<>();
            for (int j = 0; j < images[0].length; j++) {
                for (int k = 0; k < images[0][0].length; k++) {
                    temp.put("[" + j + "," + k + "]", image[j][k] == '#' ? 2 : image[j][k] == '+' ? 1 : 0);
                }
            }
            featureList.add(temp);
        }
        return featureList;
    }

    private static List<Map<String, Integer>> quadrants(char[][][] images) {
        List<Map<String, Integer>> featureList = new ArrayList<>();
        Map<String, Integer> temp;
        int width = images[0].length, height = images[0][0].length;
        int widthHalf = width % 4 == 0 ? width / 4 : width / 2, heightHath = height / 4;
        int[][][] nums = charToInt(images);
        int curTotal;
        for (int[][] num : nums) {
            temp = new HashMap<>();
            for (int i = 0; i < width; i += widthHalf) {
                for (int j = 0; j < height; j += heightHath) {
                    curTotal = 0;
                    for (int k = i; k < i + widthHalf; k++) {
                        for (int l = j; l < j + heightHath; l++) {
                            curTotal += num[k][l];
                        }
                    }
                    temp.put("quadrant " + (((i / (width / 2)) * 2) + (j / (height / 2))), curTotal);
                }
            }
            featureList.add(temp);
        }
        return featureList;
    }

    static List<Map<String, Integer>> rowsAndCols(char[][][] images) {
        List<Map<String, Integer>> rows = addRows(images);
        List<Map<String, Integer>> cols = addCols(images);
        for (int i = 0; i < images.length; i++) {
            rows.get(i).putAll(cols.get(i));
        }
        return rows;
    }

    public static List<Map<String, Integer>> addCols(char[][][] images) {
        int colTotal;
        List<Map<String, Integer>> featureList = new ArrayList<>();
        Map<String, Integer> temp;
        for (char[][] image : images) {
            temp = new HashMap<>();
            for (int i = 0; i < image[0].length; i++) {
                colTotal = 0;
                for (int j = 0; j < image.length; j++) {
                    colTotal += image[j][i] == '#' ? 1 : 0;
                }
                temp.put("col " + i, colTotal);
            }
            featureList.add(temp);
        }
        return featureList;
    }

    private static List<Map<String, Integer>> addRows(char[][][] images) {
        int colTotal;
        List<Map<String, Integer>> featureList = new ArrayList<>();
        Map<String, Integer> temp;
        for (char[][] image : images) {
            temp = new HashMap<>();
            for (int i = 0; i < image.length; i++) {
                colTotal = 0;
                for (int j = 0; j < image[0].length; j++) {
                    colTotal += image[i][j] == '#' ? 1 : 0;
                }
                temp.put("col " + i, colTotal);
            }
            featureList.add(temp);
        }
        return featureList;
    }

    /**
     * converts symbols to numbers
     *
     * @param images
     * @return
     */
    private static int[][][] charToInt(char[][][] images) {
        int depth = images.length, rows = images[0].length, cols = images[0][0].length;
        int[][][] nums = new int[depth][rows][cols];
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    nums[i][j][k] = images[i][j][k] == '#' ? 2 : images[i][j][k] == '+' ? 1 : 0;
                }
            }
        }
        return nums;
    }
}
