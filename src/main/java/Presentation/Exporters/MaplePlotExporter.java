package Presentation.Exporters;

import Logic.NeuronStructures.MultiLayerPerceptron;

public class MaplePlotExporter {
    /**
     * Exports the NeuralNetwork in the form applicable for Maple2016 to use as a normal function.
     */
    public static String exportNeuralNetwork(MultiLayerPerceptron mlp){
        StringBuilder sb = new StringBuilder();
        sb.append("identity:=x->x;\n")
          .append("ReLU:=x->max(0,x);\n")
          .append("sigmoid:=x->1/(1+exp(-x));\n")
          .append("with(ListTools):\n");

        for(int i = 0; i < mlp.getLayers().size();i++){
            sb.append(mlp.getLayers().get(i).toMapleString(i));
        }

        sb.append('\n');
        sb.append("MLP:=(x_1,x_2)->");
        for(int i=mlp.getLayers().size()-1; i>=0;i--){
        sb.append("NeuronLayer"+i+"(");
        }

        sb.append("x_1,x_2");

        for(int i=0; i<mlp.getLayers().size(); i++){
            sb.append(')');
        }
        sb.append(";\n");

        return sb.toString();
    }
}
