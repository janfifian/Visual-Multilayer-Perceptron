package Presentation;

import Data.Datasets.Dataset;
import Data.Datasets.LogicOperators;
import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.MultiLayerPerceptronBuilder;
import Logic.MultiLayerPerceptronTrainer;
import Logic.NeuronStructures.MultiLayerPerceptron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PerceptronGUI extends JFrame implements KeyListener {
    private static PlotPrepper prepper;
    private Label label;
    private TextField txtField;
    private Label infoOnStart;
    private Label infoOnIterations;
    private Label infoOnLearningMethod;
    private int itersPerKeyPress;
    private static MultiLayerPerceptron perceptron;

    private boolean hasStarted;

    public void keyPressed(KeyEvent ke) {
        char i = ke.getKeyChar();
        switch(i) {
            case '1':
                infoOnLearningMethod.setText("Currently Selected Dataset: Or");
                MultiLayerPerceptronTrainer.provideTests(new Dataset(LogicOperators.getOrDataset()));
                break;
            case '2':
                infoOnLearningMethod.setText("Currently Selected Dataset: And");
                MultiLayerPerceptronTrainer.provideTests(new Dataset(LogicOperators.getAndDataset()));
                break;
            case '3':
                infoOnLearningMethod.setText("Currently Selected Dataset: Xor");
                MultiLayerPerceptronTrainer.provideTests(new Dataset(LogicOperators.getXorDataset()));
                break;
            case '4':
                infoOnLearningMethod.setText("Currently Selected Dataset: Iff");
                MultiLayerPerceptronTrainer.provideTests(new Dataset(LogicOperators.getIffDataset()));
                break;
            case '5':
                infoOnLearningMethod.setText("Currently Selected Dataset: Implies");
                MultiLayerPerceptronTrainer.provideTests(new Dataset(LogicOperators.getImpliesDataset()));
                break;
            case 's':
                itersPerKeyPress =1000;
                Dataset.setInputSize(2);
                Dataset.setOutputSize(1);
                infoOnStart.setText("Perceptron has started training.");
                infoOnIterations.setText("Current iteration of the learning process: 0");
                prepper.setPoints(MultiLayerPerceptronTrainer.getTests());
                break;
            case 'p':
                MultiLayerPerceptronTrainer.train(perceptron,itersPerKeyPress);
                prepper.createChartWithController();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        prepper = new PlotPrepper();
        MultiLayerPerceptronBuilder mlpBuilder = new MultiLayerPerceptronBuilder(2);
        Dataset.setInputSize(2);
        Dataset.setOutputSize(1);
        perceptron = mlpBuilder
                .addLayer(3,1.0f, ActivationFunctionChoice.Sigmoid)
                .addLayer(1,1.0f, ActivationFunctionChoice.Identity)
                .setLearningCoefficient(0.01f)
                .build();
        prepper.setMultiLayerPerceptron(perceptron);

        PerceptronGUI pgui = new PerceptronGUI();
    }

    public PerceptronGUI(){
        super("Perceptron Graphic User Interface");

        JTextArea instructions = new JTextArea();
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setText("Keybinding:\n 1-5: choose dataset\n s: confirm dataset and initialize perceptron \n p: progress with learning by 10 epochs");
        add(instructions,BorderLayout.SOUTH);

        instructions.addKeyListener(this);
        Panel panel = new Panel();
        label = new Label();

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        infoOnStart = new Label();
        infoOnStart.setText("Perceptron has yet to start.");
        panel.add(infoOnStart, BorderLayout.NORTH);

        infoOnIterations = new Label();
        infoOnIterations.setText("Current iteration of the learning process: 0");
        panel.add(infoOnIterations, BorderLayout.SOUTH);

        infoOnLearningMethod = new Label();
        infoOnLearningMethod.setText("Currently Selected Dataset: None");
        panel.add(infoOnLearningMethod, BorderLayout.SOUTH);



        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        setSize(400,400);
        setVisible(true);
    }

}