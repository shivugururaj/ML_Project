package com.ml.project.mlproject.classifiers;

import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class SVMClassifier {
  private static final String INPUT = "output/train_users_libsvm.txt";
  private static final String TEST_INPUT = "output/test_users_libsvm.txt";
  JavaRDD<LabeledPoint> input;
  private JavaRDD<LabeledPoint> testData;
  private List<Double> predictions;

  public SVMClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
    testData = MLUtils.loadLibSVMFile(jsc.sc(), TEST_INPUT).toJavaRDD();
  }

  public double classify() {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.85, 0.15 }, 12345);
    JavaRDD<LabeledPoint> training = splitData[0];
    JavaRDD<LabeledPoint> validation = splitData[1];

    int numIterations = 100;
    SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);

    model.clearThreshold();

    JavaRDD<Tuple2<Object, Object>> scoreAndLabels = validation.map(p -> {
      Double score = model.predict(p.features());
      return new Tuple2<Object, Object>(score, p.label());
    });
    
    BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
    double auROC = metrics.areaUnderROC();

    JavaPairRDD<Double, Double> predictionAndLabel = validation
        .mapToPair(p -> new Tuple2<Double, Double>(model.predict(p.features()), p.label()));
    double accuracy = 1.0 * predictionAndLabel.filter(pl -> pl._1().equals(pl._2())).count() / validation.count();
    
    predictions = testData.map(arg0 -> model.predict(arg0.features())).collect();
    
    System.out.println("Area under ROC = " + auROC);
    
    return accuracy;
  }
  
  public List<Double> getPredictions() {
    return predictions;
  }
}
