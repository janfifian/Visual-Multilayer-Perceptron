package Logic.Neurons;

import Logic.ActivationFunctions.ActivationFunction;
import Logic.NeuronStructures.NeuronLayer;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private static Random random = new Random();
    private ArrayList<Float> weights;
    private float bias;
    private ActivationFunction activationFunction;
    private ArrayList<Float> recentlyObtainedInput;
    private float recentlyCalculatedValue;
    private static float range = 2.0f;
    public static float learningCoefficient;

    public static void setRange(float f){
        range = f;
    };
    public static void setSeed(long l){
        random.setSeed(l);
    };

    static{
        random.setSeed(System.currentTimeMillis());
    }

    public Neuron(int inputSize, ActivationFunction activationFunction){
        this.activationFunction = activationFunction;
        this.weights = new ArrayList<Float>();
        for(int i = 0; i < inputSize; i++){
            weights.add(random.nextFloat()*range - range*0.5f);
        }
        bias = random.nextFloat()*range - range*0.5f;
    }

    public float calculateOutput(ArrayList<Float> input){
        recentlyObtainedInput= input;
        float argument = Neuron.innerProduct(input,weights);
        recentlyCalculatedValue= activationFunction.calculate(argument+bias);
        return recentlyCalculatedValue;
    }


    public void shiftBias(float modification){
        bias-=modification;
    }

    public ArrayList<Float> getWeights(){
        return weights;
    }

    private static float innerProduct(ArrayList<Float> A, ArrayList<Float> B){
        float result = 0.0f;
        for(int i = 0; i < A.size(); i++){
            result += A.get(i) * B.get(i);
        }
        return result;
    }

    public float getRecentlyCalculatedValue(){
        return recentlyCalculatedValue;
    }

    public float calculateOuterDelta(float actualOutput, float expectedOutput,int index) {
        //  ( ( P(x) - y) * f'_l (P_(l-1) (x) ) * N^(l-1)_i(i)
        return (actualOutput - expectedOutput)*activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias)*recentlyObtainedInput.get(index);
    }

    public float calculateOuterBiasDelta(float actualOutput, float expectedOutput){
        //  ( ( P(x) - y) * f'_l (P_(l-1) (x) )
        return (actualOutput - expectedOutput)*activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
    }

    public float calculateInnerDelta(NeuronLayer previouslyCalculatedLayer, ArrayList<ArrayList<Float>> deltasArray, int ownIndex, int relativeIndex) {
        float derivative = activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
        float sum = 0.0f;
        for(int i = 0; i < previouslyCalculatedLayer.getNeurons().size(); i++){
            sum+=deltasArray.get(i).get(deltasArray.get(i).size()-1)*previouslyCalculatedLayer.getNeurons().get(i).getWeights().get(ownIndex);
        }
        return derivative*sum*recentlyObtainedInput.get(relativeIndex);
    }

    public Float calculateInnerBiasDelta(NeuronLayer previouslyCalculatedLayer, ArrayList<ArrayList<Float>> deltasArray, int ownIndex) {
        float derivative = activationFunction.calculateDerivative(innerProduct(recentlyObtainedInput,weights)+bias);
        float sum = 0.0f;
        for(int i = 0; i < previouslyCalculatedLayer.getNeurons().size(); i++){
            sum+=deltasArray.get(i).get(deltasArray.get(i).size()-1)*previouslyCalculatedLayer.getNeurons().get(i).getWeights().get(ownIndex);
        }
        return derivative*sum;
    }

    public void applyModifications(ArrayList<Float> modification) {
        for(int i =0; i < weights.size(); i++){
            weights.set(i, weights.get(i)-learningCoefficient*modification.get(i));
        }
        shiftBias(learningCoefficient*modification.get(modification.size()-1));
    }
}
