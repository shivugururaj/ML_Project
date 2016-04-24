package com.ml.project.mlproject.classifiers;

import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.GradientBoostedTrees;
import org.apache.spark.mllib.tree.configuration.BoostingStrategy;
import org.apache.spark.mllib.tree.model.GradientBoostedTreesModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class GradientBoostingClassifier {
  private static final String INPUT = "output/train_users_libsvm.txt";
  private static final String TEST_INPUT = "output/test_users_lubsvm.txt";
  private JavaRDD<LabeledPoint> input;
  private JavaRDD<LabeledPoint> testData;
  private List<Double> predictions;

  public GradientBoostingClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
    testData = MLUtils.loadLibSVMFile(jsc.sc(), TEST_INPUT).toJavaRDD();
  }

  public double classify() {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.85, 0.15 }, 12345);
    JavaRDD<LabeledPoint> training = splitData[0];
    JavaRDD<LabeledPoint> validation = splitData[1];

    BoostingStrategy strategy = BoostingStrategy.defaultParams("Classification");
    strategy.setNumIterations(15);

    final GradientBoostedTreesModel model = GradientBoostedTrees.train(training, strategy);
    JavaPairRDD<Double, Double> predicationLabels = validation
        .mapToPair(arg0 -> new Tuple2<Double, Double>(model.predict(arg0.features()), arg0.label()));

    double accuracy = predicationLabels.filter(pl -> pl._1().equals(pl._2())).count() / (double) validation.count();

    predictions = testData.map(arg0 -> model.predict(arg0.features())).collect();

    return accuracy;
  }

  public List<Double> getPredictions() {
    return predictions;
  }
}
