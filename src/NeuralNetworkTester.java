import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class NeuralNetworkTester {

    /**
     * method to test Neural Network at Runtime
     * @param args unused
     */
    public static void main(String[] args) {
        // Reads in all 60000 pictures and answers
        byte[] trainingPictures = decompressGzipFile("data/train-images-idx3-ubyte.gz");
        byte[] trainingAnswers = decompressGzipFile("data/train-labels-idx1-ubyte.gz");

        // Creates neural network
        NeuralNetwork nn = new NeuralNetwork(new int[]{784, 100, 10}, .5);

        System.out.println("Started Training");
        for (int i = 0; i < 60000; i++) { // 60000 pictures
            // Sets up picture data
            int pixel;
            int answer;
            double[] inputs = new double[784];
            for (int j = 0; j < 28; j++) {
                for (int k = 0; k < 28; k++) {
                    pixel = trainingPictures[i * 784 + j * 28 + k + 16];
                    pixel = pixel < 0 ? pixel + 256 : pixel;
                    inputs[j * 28 + k] = pixel / 255.0;
                }
            }

            // Classifies input data
            double[] predicted = nn.classify(inputs);

            // determines predicted and real answers
            answer = trainingAnswers[i + 8];
            double[] real = new double[10];
            for (int j = 0; j < 10; j++) {
                real[j] = j == answer ? 1 : 0;
            }
            int max = 0;
            for (int j = 1; j < 10; j++) {
                max = predicted[max] < predicted[j] ? j : max;
            }

            // Backprops to update weights and biases
            nn.backProp(real);

            if (i % 1000 == 0 && i != 0) {
                System.out.printf("Finished training case %d\n", i);
            }
        }
        System.out.println("Finished Training\n");

        byte[] testingPictures = decompressGzipFile("data/t10k-images-idx3-ubyte.gz");
        byte[] testingAnswers = decompressGzipFile("data/t10k-labels-idx1-ubyte.gz");
        int correct = 0;

        System.out.println("Started Testing");
        for (int i = 0; i < 10000; i++) { // 10000 pictures
            // Sets up picture data
            int pixel;
            int answer;
            double[] inputs = new double[784];
            for (int j = 0; j < 28; j++) {
                for (int k = 0; k < 28; k++) {
                    pixel = testingPictures[i * 784 + j * 28 + k + 16];
                    pixel = pixel < 0 ? pixel + 256 : pixel;
                    inputs[j * 28 + k] = pixel / 255.0;
                }
            }

            // Classifies input data
            double[] predicted = nn.classify(inputs);
            int max = 0;
            for (int j = 1; j < 10; j++) {
                max = predicted[max] < predicted[j] ? j : max;
            }

            // determines predicted and real answers
            answer = testingAnswers[i + 8];
            if (max == answer) {
                correct++;
            }
            if (i % 1000 == 0 && i != 0) {
                System.out.printf("Finished testing case %d\n", i);
            }
//            System.out.printf("%d, %d\n", max, answer);
        }
        System.out.println("Finished Testing\n");
        System.out.printf("Results: %d, %.2f%%\n", correct, correct / 100.0);
    }

    /**
     * Opens .gz files containing data
     * @param gzipFile String path of file to be opened
     * @return byte array of data in the file
     */
    private static byte[] decompressGzipFile(String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            return gis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
