package com.ml.project.mlproject.classifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class DecisionTreesClassifier {

  private static final String INPUT = "output/train_users_libsvm.txt";
  private static final String TEST_INPUT = "output/test_users_libsvm.txt";
  private JavaRDD<LabeledPoint> input;
  private JavaRDD<LabeledPoint> testData;
  private List<Double> predictions;

  public DecisionTreesClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
    testData = MLUtils.loadLibSVMFile(jsc.sc(), TEST_INPUT).toJavaRDD();
  }

  public double classify() {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.85, 0.15 }, 12345);
    JavaRDD<LabeledPoint> trainData = splitData[0];
    JavaRDD<LabeledPoint> validationData = splitData[1];

    int numClasses = 2;

    String impurity = "gini";
    int maxDepth = 5;
    int maxBins = 32;

    Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();

    final DecisionTreeModel model = DecisionTree.trainClassifier(trainData, numClasses, categoricalFeaturesInfo,
        impurity, maxDepth, maxBins);
    
    JavaPairRDD<Double, Double> predicationLabels = validationData
        .mapToPair(arg0 -> new Tuple2<Double, Double>(model.predict(arg0.features()), arg0.label()));

    double accuracy = predicationLabels.filter(pl -> pl._1().equals(pl._2())).count() / (double) validationData.count();

    predictions = testData.map(arg0 -> model.predict(arg0.features())).collect();
    
    return accuracy;
  }
  
  public List<Double> getPredictions() {
    return predictions;
  }
}
