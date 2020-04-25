package Logic;

import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.NeuronStructures.MultiLayerPerceptron;
import Logic.NeuronStructures.NeuronLayer;

import java.util.ArrayList;

public class MultiLayerPerceptronBuilder {
    ArrayList<NeuronLayer> layers;
    int lastInputSize;
    float learningCoefficient;

    public MultiLayerPerceptronBuilder(int initialiInputSize){
        layers = new ArrayList<>();
        this.lastInputSize = initialiInputSize;
    }

    public MultiLayerPerceptronBuilder addLayer(int size, float weightsRange, ActivationFunctionChoice activationFunction){
            layers.add(new NeuronLayer(size, lastInputSize, weightsRange, activationFunction));
            lastInputSize = size;
        return this;
    }

    public MultiLayerPerceptronBuilder setLearningCoefficient(float coef){
        this.learningCoefficient = coef;
        return this;
    }

    public MultiLayerPerceptron build(){
        return new MultiLayerPerceptron(layers, learningCoefficient);
    }

}
