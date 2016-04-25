package com.ml.project.mlproject.classifiers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

import com.ml.project.mlproject.App;
import com.ml.project.mlproject.Mappings;

import scala.Tuple2;

public class RandomForestClassifier {
  private static final String INPUT = "output/train_users_libsvm.txt";
  private static final String TEST_INPUT = "output/test_users_libsvm.txt";
  JavaRDD<LabeledPoint> input;
  private JavaRDD<LabeledPoint> testData;
  private List<Double> predictions;

  public RandomForestClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
    testData = MLUtils.loadLibSVMFile(jsc.sc(), TEST_INPUT).toJavaRDD();
  }

  public void classify() throws IOException {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.85, 0.15 }, 12345);
    JavaRDD<LabeledPoint> training = splitData[0];
    JavaRDD<LabeledPoint> validation = splitData[1];

    int numTrees = 15;
    String featureSubsetStrategy = "auto";

    int numClasses = 12;
    HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
    String impurity = "gini";
    int maxDepth = 5;
    int maxBins = 32;

    RandomForestModel model = RandomForest.trainClassifier(training, numClasses, categoricalFeaturesInfo, numTrees,
        featureSubsetStrategy, impurity, maxDepth, maxBins, 12345);

    // Evaluate model on test instances and compute test error
    JavaPairRDD<Double, Double> predictionAndLabel = validation
        .mapToPair(p -> new Tuple2<Double, Double>(model.predict(p.features()), p.label()));
    double accuracy = 1.0 * predictionAndLabel.filter(pl -> pl._1().equals(pl._2())).count() / validation.count();

    predictions = testData.map(arg0 -> model.predict(arg0.features())).collect();

    JavaPairRDD<Object, Object> predictionAndLabels = validation
        .mapToPair(p -> new Tuple2<Object, Object>(model.predict(p.features()), p.label()));

    StringBuffer buffer = new StringBuffer();
    buffer.append("Random Forest Overall Stats \n");

    MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());

    Matrix confusion = metrics.confusionMatrix();
    buffer.append("Confusion matrix: \n").append(confusion).append("\n");
    System.out.println("Confusion matrix: \n" + confusion);

    // Overall statistics

    buffer.append("Precision = ").append(metrics.precision()).append("\n");
    buffer.append("Recall = ").append(metrics.recall()).append("\n");
    buffer.append("F1 Score = ").append(metrics.fMeasure()).append("\n");

    System.out.println("Precision = " + metrics.precision());
    System.out.println("Recall = " + metrics.recall());
    System.out.println("F1 Score = " + metrics.fMeasure());

    // Stats by labels
    for (int i = 0; i < metrics.labels().length; i++) {

      buffer.append("Class ").append(metrics.labels()[i]).append(" = ").append(metrics.precision(metrics.labels()[i]))
          .append("\n");
      buffer.append("Class ").append(metrics.labels()[i]).append(" = ").append(metrics.recall(metrics.labels()[i]))
          .append("\n");
      buffer.append("Class ").append(metrics.labels()[i]).append(" = ").append(metrics.fMeasure(metrics.labels()[i]))
          .append("\n");
      System.out.format("Class %f precision = %f\n", metrics.labels()[i], metrics.precision(metrics.labels()[i]));
      System.out.format("Class %f recall = %f\n", metrics.labels()[i], metrics.recall(metrics.labels()[i]));
      System.out.format("Class %f F1 score = %f\n", metrics.labels()[i], metrics.fMeasure(metrics.labels()[i]));
    }
    buffer.append("Weighted precision = ").append(metrics.weightedPrecision()).append("\n");
    buffer.append("Weighted recall = ").append(metrics.weightedRecall()).append("\n");
    buffer.append("Weighted F1 score = ").append(metrics.weightedFMeasure()).append("\n");
    buffer.append("Weighted false positive rate = ").append(metrics.weightedFalsePositiveRate()).append("\n");
    buffer.append("Random Forests Accuracy: ").append(accuracy).append("\n");
    buffer.append("------------------------------------").append("\n");

    System.out.format("Weighted precision = %f\n", metrics.weightedPrecision());
    System.out.format("Weighted recall = %f\n", metrics.weightedRecall());
    System.out.format("Weighted F1 score = %f\n", metrics.weightedFMeasure());
    System.out.format("Weighted false positive rate = %f\n", metrics.weightedFalsePositiveRate());
    
    FileWriter writer = new FileWriter(new File(App.OUTPUT_FILE), true);
    BufferedWriter bufferWritter = new BufferedWriter(writer);
    bufferWritter.write(buffer.toString());
    bufferWritter.flush();
    bufferWritter.close();
    
    Mappings.writePredictionsToCSV(predictions, "random_forests");
  }

  public List<Double> getPredictions() {
    return predictions;
  }
}
