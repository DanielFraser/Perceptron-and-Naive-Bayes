import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class loadImages {
    public static char[][][] getImages(String filename) throws IOException {

        int width = 28, height = 28, total = 5000;
        if (filename.contains("face")) {
            width = 70;
            height = 60;
            total = 451;
        }
        char imageArray[][][] = new char[total][height][width];
        int i = 0, k = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                imageArray[i][k] = line.toCharArray();
                if (k == height - 1) {
                    k = 0;
                    i++;
                } else
                    k++;
            }
        }
        //printImage(imageArray, height, width);
        return imageArray;
    }

    private static void printImage(int[][][] arr, int height, int width) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++)
                    System.out.print(arr[i][j][k]);
                System.out.println();
            }
            System.out.println();
        }

    }

    public static int[] getAnswers(String filename) throws IOException {
        int answers[] = new int[filename.contains("face") ? 451 : 5000];
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                answers[i] = Integer.valueOf(line);
                i++;
            }
        }

        return answers;
    }
}