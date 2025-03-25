# K-Means on Iris Dataset

## Overview

This Java program implements the **K-Means Clustering algorithm** from scratch to group data points from the **Iris dataset** into `k` clusters based on their feature similarity. It calculates the total intra-cluster distance and provides a label distribution summary for each cluster.

## Features

- Reads data from a CSV-like file (`iris_kmeans.txt`)
- Accepts user-defined number of clusters (`k`)
- Assigns initial random cluster memberships
- Iteratively refines clusters using centroid updates
- Reports total sum of distances and label distribution per cluster

## Dataset Format

The dataset file should be named `iris_kmeans.txt` and placed in the root directory. Each line must follow this format:

```
<feature1>,<feature2>,<feature3>,<feature4>,<label>
```

### Example:

```
5.7,2.5,5.0,2.0,Iris-virginica  
5.6,3.0,4.1,1.3,Iris-versicolor  
5.5,3.5,1.3,0.2,Iris-setosa
```

## How to Run

1. **Ensure you have Java installed.**

2. **Place the `iris_kmeans.txt` file in the root directory of your project.**

3. **Compile and run the program:**
   ```bash
   javac KMeans.java
   java KMeans
   ```

4. **Input the desired number of clusters (`k`) when prompted.**

### Sample Output

```
Enter number of clusters (k): 3
Total Sum of Distances: 15.87
Cluster 1: 80% Iris-virginica, 20% Iris-versicolor, 
Cluster 2: 100% Iris-setosa, 
Cluster 3: 60% Iris-versicolor, 40% Iris-virginica, 
```

## License

This project is licensed under the [MIT License](LICENSE).

## Contributing

Contributions are welcome! Fork this repository and submit a pull request with improvements or feature enhancements.
