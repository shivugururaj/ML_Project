package com.ml.project.mlproject.classifiers;

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
  JavaRDD<LabeledPoint> input;

  public GradientBoostingClassifier(JavaSparkContext jsc) {
    input = MLUtils.loadLibSVMFile(jsc.sc(), INPUT).toJavaRDD();
  }

  public void classify() {
    JavaRDD<LabeledPoint>[] splitData = input.randomSplit(new double[] { 0.85, 0.15 }, 12345);
    JavaRDD<LabeledPoint> training = splitData[0];
    JavaRDD<LabeledPoint> validation = splitData[1];
    
    BoostingStrategy strategy = BoostingStrategy.defaultParams("Classification");
    strategy.setNumIterations(15);
    
    final GradientBoostedTreesModel model = GradientBoostedTrees.train(training, strategy);
    JavaPairRDD<Double, Double> predicationLabels = validation
        .mapToPair(arg0 -> new Tuple2<Double, Double>(model.predict(arg0.features()), arg0.label()));

    double accuracy = predicationLabels.filter(pl -> pl._1().equals(pl._2())).count() / (double) validation.count();

    System.out.println(accuracy);
  }
}
