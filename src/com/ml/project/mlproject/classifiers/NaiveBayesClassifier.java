package com.ml.project.mlproject.classifiers;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class NaiveBayesClassifier {
  private static final String INPUT = "output/train_users_libsvm.txt";
  JavaRDD<LabeledPoint> input;

  public NaiveBayesClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
  }

  public void classify() {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.9, 0.1 }, 12345);
    JavaRDD<LabeledPoint> training = splitData[0];
    JavaRDD<LabeledPoint> validation = splitData[1];

    final NaiveBayesModel model = NaiveBayes.train(training.rdd(), 1.0);
    JavaPairRDD<Double, Double> predicationLabels = validation
        .mapToPair(arg0 -> new Tuple2<Double, Double>(model.predict(arg0.features()), arg0.label()));

    double accuracy = predicationLabels.filter(pl -> pl._1().equals(pl._2())).count() / (double) validation.count();

    System.out.println(accuracy);

  }
}
