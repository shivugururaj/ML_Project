package com.ml.project.mlproject.classifiers;

import java.util.HashMap;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class RandomForestClassifier {
  private static final String INPUT = "output/train_users_libsvm.txt";
  JavaRDD<LabeledPoint> input;

  public RandomForestClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
  }

  public void classify() {
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
    Double accuracy = 1.0 * predictionAndLabel.filter(pl -> pl._1().equals(pl._2())).count() / validation.count();
    System.out.println("Accuracy: " + accuracy);
  }
}
