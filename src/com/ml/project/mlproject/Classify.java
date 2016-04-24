package com.ml.project.mlproject;

import java.util.HashMap;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

public class Classify {
	public static void main(String[] ar) {

		SparkConf sparkConf = new SparkConf().setAppName("JavaRandomForestClassificationExample").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		// Load and parse the data file.
		//String datapath = "/Users/shivugururaj/Documents/gdrive/spring 2016/machine learning/shivu/project/temp.csv";

		String trainDataPath="output/train_users_libsvm.txt";
		String testDataPath="output/test_users_libsvm.txt";

		JavaRDD<LabeledPoint> trainingData = MLUtils.loadLibSVMFile(jsc.sc(), trainDataPath).toJavaRDD();
		// Split the data into training and test sets (30% held out for testing)
		//JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.7, 0.3 });
		//JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(), testDataPath).toJavaRDD();
		// JavaRDD<LabeledPoint> trainingData = MLUtils.loadLibSVMFile(jsc.sc(),
		// trainDataPath).toJavaRDD();
		// JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(),
		// testDataPath).toJavaRDD();

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = 12;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		Integer numTrees = 3; // Use more in practice.
		String featureSubsetStrategy = "auto"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = 5;
		Integer maxBins = 32;
		Integer seed = 12345;

		/*final NaiveBayesModel model = NaiveBayes.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
				numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins, seed);*/
		
		final NaiveBayesModel model = NaiveBayes.train(trainingData.rdd(), 1.0);

		// Evaluate model on test instances and compute test error
		/*JavaPairRDD<Double, Double> predictionAndLabel = testData
				.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					@Override
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
					}
				});*/
		
		List<Double> predictions = testData.map(new Function<LabeledPoint, Double>() {

			@Override
			public Double call(LabeledPoint arg0) throws Exception {
				// TODO Auto-generated method stub
				return model.predict(arg0.features());
			}
		}).collect();
		
		/*for(Vector v:predictions) {
			for(Double d:v.toArray()) {
				System.out.print(d + " ");
			}
			System.out.println();
		}*/
		
		for(Double prediction:predictions) {
			System.out.print(prediction + " ");
		}
		
		/*Double testErr = 1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
			@Override
			public Boolean call(Tuple2<Double, Double> pl) {
				return !pl._1().equals(pl._2());
			}
		}).count() / testData.count();
		System.out.println("Test Error: " + testErr);
		System.out.println("Learned classification forest model:\n" + model.toDebugString());*/

		// Save and load model
		//model.save(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
		//RandomForestModel sameModel = RandomForestModel.load(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
	}

}
