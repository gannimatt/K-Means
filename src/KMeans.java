import java.util.*;
import java.io.*;

public class KMeans {
    private static double[][] data;
    private static String[] labels;
    private static int[] assignments;

    public static void main(String[] args) throws IOException {

        String path = "iris_kmeans.txt";
        loadData(path);

        System.out.print("Enter number of clusters (k): ");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        scanner.nextLine();

        initializeClusters(k);

        boolean changed;
        do {
            double[][] centroids = calculateCentroids(k);
            changed = reassignClusters(centroids);
            reportResults(centroids, k);
        } while (changed);

        scanner.close();
    }

    private static void loadData(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        List<double[]> dataList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(",");
            double[] features = Arrays.stream(parts, 0, parts.length - 1)
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            dataList.add(features);
            labelList.add(parts[parts.length - 1]);
        }
        scanner.close();

        data = dataList.toArray(new double[0][]);
        labels = labelList.toArray(new String[0]);
        assignments = new int[data.length];
    }

    private static void initializeClusters(int k) {
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            assignments[i] = random.nextInt(k);
        }
    }

    private static double[][] calculateCentroids(int k) {
        double[][] centroids = new double[k][data[0].length];
        int[] counts = new int[k];

        for (int i = 0; i < data.length; i++) {
            int cluster = assignments[i];
            for (int j = 0; j < data[i].length; j++) {
                centroids[cluster][j] += data[i][j];
            }
            counts[cluster]++;
        }

        for (int i = 0; i < k; i++) {
            if (counts[i] > 0) {
                for (int j = 0; j < centroids[i].length; j++) {
                    centroids[i][j] /= counts[i];
                }
            }
        }
        return centroids;
    }

    private static boolean reassignClusters(double[][] centroids) {
        boolean changed = false;
        for (int i = 0; i < data.length; i++) {
            double minDistance = Double.MAX_VALUE;
            int bestCluster = 0;
            for (int j = 0; j < centroids.length; j++) {
                double distance = euclideanDistance(data[i], centroids[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCluster = j;
                }
            }
            if (assignments[i] != bestCluster) {
                assignments[i] = bestCluster;
                changed = true;
            }
        }
        return changed;
    }

    private static double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    private static void reportResults(double[][] centroids, int k) {
        double totalSumDistance = 0;
        Map<Integer, Map<String, Integer>> clusterLabelCounts = new HashMap<>();

        for (int i = 0; i < assignments.length; i++) {
            int cluster = assignments[i];
            totalSumDistance += euclideanDistance(data[i], centroids[cluster]);

            Map<String, Integer> labelCount = clusterLabelCounts.computeIfAbsent(cluster, key -> new HashMap<>());
            labelCount.merge(labels[i], 1, Integer::sum);
        }

        System.out.printf("Total Sum of Distances: %.2f\n", totalSumDistance);
        for (int i = 0; i < k; i++) {
            System.out.print("Cluster " + (i + 1) + ": ");
            Map<String, Integer> labelCount = clusterLabelCounts.getOrDefault(i, new HashMap<>());
            int sum = labelCount.values().stream().mapToInt(Integer::intValue).sum();
            labelCount.forEach((label, count) -> System.out.printf("%d%% %s, ", count * 100 / sum, label));
            System.out.println();
        }
    }
}
