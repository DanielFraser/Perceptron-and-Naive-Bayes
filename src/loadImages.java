import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class loadImages {
    static char[][][] getImages(String filename, double percent) throws IOException {

        int width = 28, height = 28;
        if (filename.contains("face")) {
            width = 60;
            height = 70;
        }
        int total = (int) Math.round((countTotal(filename)/height) * percent);
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
                if (i >= total)
                    break;
            }
        }
        return imageArray;
    }

    private static int countTotal(String filename) throws IOException {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                total++;
            }
        }
        return total;
    }

    static int[] getAnswers(String filename, double percent) throws IOException {
        int answers[] = new int[(int) Math.round(countTotal(filename)*percent)];
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                answers[i] = Integer.valueOf(line);
                i++;
                if(i >= (int) (countTotal(filename) * percent))
                    break;
            }
        }

        return answers;
    }
}