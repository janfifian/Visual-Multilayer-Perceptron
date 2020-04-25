package Logic;

import Data.Datasets.Dataset;
import Data.Datasets.TestSet;
import Data.Datasets.TrainingSet;
import Logic.NeuronStructures.MultiLayerPerceptron;

import java.util.ArrayList;

public class MultiLayerPerceptronTrainer {

    private static TestSet testSet;
    private static TrainingSet trainingSet;

    public static void provideTests(Dataset set){
        testSet = new TestSet(set);
        trainingSet = new TrainingSet(set);
    }

    public static void provideTrainingSet(Dataset set){
        trainingSet = new TrainingSet(set);
    }

    public static void provideTestSet(Dataset set){
        testSet = new TestSet(set);
    }

    public static Dataset getTests(){
        return testSet;
    }

    public static void train(MultiLayerPerceptron mlp, int epochs){
        for(int i = 0; i < epochs; i++){
            for(int j = 0; j < trainingSet.getTests().size(); j++){
                ArrayList<Float> actualOutput = mlp.calculateOutput(trainingSet.getInput(j));
                ArrayList<Float> expectedOutput = trainingSet.getOutput(j);

                mlp.modifyWeights(actualOutput,expectedOutput);
            }
        }
    }
}
