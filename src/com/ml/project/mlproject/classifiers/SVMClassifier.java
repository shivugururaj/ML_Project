package com.ml.project.mlproject.classifiers;

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
  JavaRDD<LabeledPoint> input;

  public SVMClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
  }

  public void classify() {
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

    System.out.println("Area under ROC = " + auROC);

  }
}
