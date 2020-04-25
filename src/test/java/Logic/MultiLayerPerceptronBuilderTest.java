package Logic;

import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.NeuronStructures.MultiLayerPerceptron;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptronBuilderTest {

    @Test
    public void createsWorkingPerceptron(){
        MultiLayerPerceptronBuilder builder= new MultiLayerPerceptronBuilder(2);
        MultiLayerPerceptron perceptron =
        builder.setLearningCoefficient(0.5f).addLayer(2,2.0f, ActivationFunctionChoice.ReLU).
                addLayer(1,1.0f,ActivationFunctionChoice.Identity).build();
        ArrayList<Float> input = new ArrayList<Float>(List.of(1.0f,0.4f));
        float output = perceptron.calculateOutput(input).get(0);
        Assert.assertEquals(true, output < 10000.0f);

    }

}