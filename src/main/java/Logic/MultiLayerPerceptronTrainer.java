package Logic;

import Data.Datasets.Dataset;
import Data.Datasets.ValidationSet;
import Data.Datasets.TrainingSet;
import Logic.NeuronStructures.MultiLayerPerceptron;

import java.util.ArrayList;

public class MultiLayerPerceptronTrainer {

    private static ValidationSet validationSet;
    private static TrainingSet trainingSet;

    public static void provideValidations(Dataset set){
        validationSet = new ValidationSet(set);
        trainingSet = new TrainingSet(set);
    }

    public static void provideTrainingSet(Dataset set){
        trainingSet = new TrainingSet(set);
    }

    public static void provideValidationSet(Dataset set){
        validationSet = new ValidationSet(set);
    }

    public static Dataset getValidationSet(){
        return validationSet;
    }

    /**
     * Performs multiple epochs of training for given Perceptron instance
     * @param mlp Perceptron to be trained using the samples in training set
     * @param epochs number of epochs
     */
    public static void train(MultiLayerPerceptron mlp, int epochs){
        for(int i = 0; i < epochs; i++){
            /**
             * Randomize the order of the samples.
             */
            trainingSet.shuffle();
            for(int j = 0; j < trainingSet.getTests().size(); j++){
                ArrayList<Float> actualOutput = mlp.calculateOutput(trainingSet.getInput(j));
                ArrayList<Float> expectedOutput = trainingSet.getOutput(j);

                mlp.modifyWeights(actualOutput,expectedOutput);
            }
        }
    }

    /**
     * For provided validation set and MultiLayer Perceptron this method
     * calculates the overall squared error on the whole set;
     *
     * @param mlp Multilayer Perceptron to verify
     * @return sum of squared errors on the validation set.
     */
    public static float calculateErrorOnValidationSet(MultiLayerPerceptron mlp) {
        float error=0.0f;
        float errorOnJthSample;
        for(int j = 0; j < validationSet.getTests().size(); j++){
            ArrayList<Float> actualOutput = mlp.calculateOutput(trainingSet.getInput(j));
            ArrayList<Float> expectedOutput = trainingSet.getOutput(j);
            for(int k = 0; k < actualOutput.size();k++){
                errorOnJthSample= Math.abs(expectedOutput.get(k) - actualOutput.get(k));
                error+=errorOnJthSample*errorOnJthSample;
            }
         }
        return error;
    }
}
