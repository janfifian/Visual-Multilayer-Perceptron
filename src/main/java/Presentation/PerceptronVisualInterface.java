package Presentation;

import Data.Datasets.Dataset;
import Data.Datasets.LogicOperators;
import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.MultiLayerPerceptronBuilder;
import Logic.MultiLayerPerceptronTrainer;
import Logic.NeuronStructures.MultiLayerPerceptron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PerceptronVisualInterface extends Frame {
    private static PlotPrepper prepper;
    private static MultiLayerPerceptron perceptron;
    private static Label trainingSetStatus;
    private static Label testSetStatus;
    private static Label validationSetStatus;
    private static JSpinner spinner;
    private static JSpinner spinner2;

    public static void main(String[] args) {

        prepper = new PlotPrepper();
        MultiLayerPerceptronBuilder mlpBuilder = new MultiLayerPerceptronBuilder(2);
        Dataset.setInputSize(2);
        Dataset.setOutputSize(1);
        perceptron = mlpBuilder
                .addLayer(3,1.0f, ActivationFunctionChoice.Sigmoid)
                .addLayer(3,1.0f, ActivationFunctionChoice.Sigmoid)
                .addLayer(1,1.0f, ActivationFunctionChoice.Identity)
                .setLearningCoefficient(0.03f)
                .build();
        prepper.setMultiLayerPerceptron(perceptron);

        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Perceptron Visual Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Tests and training sets
        Dataset testSet = new Dataset(LogicOperators.getXorDataset());
        Dataset trainingSet = new Dataset(LogicOperators.getXorDataset());
        MultiLayerPerceptronTrainer.provideTestSet(testSet);
        MultiLayerPerceptronTrainer.provideTrainingSet(trainingSet);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Set up plot prepper display
        prepper.setPoints(MultiLayerPerceptronTrainer.getTests());


        //Size and display the window.
        Insets insets = frame.getInsets();
        frame.setSize(600,700);
        frame.setVisible(true);
    }

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(null);

        JButton b1 = new JButton("Draw Plot");
        JButton b2 = new JButton("Progress");
        JButton b3 = new JButton("Summary");

        pane.add(b1);
        pane.add(b2);
        pane.add(b3);

        Insets insets = pane.getInsets();
        Dimension size = b1.getPreferredSize();
        b1.setBounds(450 + insets.left, 15 + insets.top,
                size.width, size.height);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                prepper.createChartWithController();
            }
        });

        size = b2.getPreferredSize();
        b2.setBounds(450 + insets.left, 55 + insets.top,
                size.width, size.height);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                progressLearning();
            }
        });

        size = b3.getPreferredSize();
        b3.setBounds(450 + insets.left, 95 + insets.top,
                size.width, size.height);


        JTextArea instructions = new JTextArea();
        instructions.setLineWrap(true);
        instructions.setEditable(true);
        instructions.setText("The resulting network and its properties will appear\nhere after pressing 'Summary' button.");
        instructions.setBounds(20+insets.left,400+insets.top,560,200);
        pane.add(instructions);

        trainingSetStatus = new Label();
        trainingSetStatus.setText("Training set undefined.");
        trainingSetStatus.setBounds(20, 15, 400, 20);
        pane.add(trainingSetStatus);

        testSetStatus = new Label();
        testSetStatus.setText("Test set undefined.");
        testSetStatus.setBounds(20, 55, 400, 20);
        pane.add(testSetStatus);

        Label progressButtonStatus = new Label();
        progressButtonStatus.setText("Training Epochs per button press:");
        progressButtonStatus.setBounds(20, 180, 400, 20);
        pane.add(progressButtonStatus);

        Integer value = 1000;
        Integer min = 100;
        Integer max = 100000;
        Integer step = 100;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner = new JSpinner(model);
        spinner.setBounds(20,200, 100, 25);
        pane.add(spinner);

        JButton b4 = new JButton("Create GIF");
        pane.add(b4);
        size = b4.getPreferredSize();
        b4.setBounds(450 + insets.left, 150,
                size.width, size.height);
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                generateGIFFrames();
            }
        });


        Label gifButtonStatus = new Label();
        gifButtonStatus.setText("Epoch frames for GIF:");
        gifButtonStatus.setBounds(430, 180, 150, 20);
        pane.add(gifButtonStatus);

        Integer value2 = 10;
        Integer min2 = 0;
        Integer max2 = 200;
        Integer step2 = 1;
        SpinnerNumberModel model2 = new SpinnerNumberModel(value2, min2, max2, step2);
        spinner2 = new JSpinner(model2);
        spinner2.setBounds(450,200, 100, 25);
        pane.add(spinner2);
    }

    static void generateGIFFrames() {
        int framecount = Integer.parseInt(spinner2.getValue().toString());
        for(int i = 1; i <= framecount; i++){
            progressLearning();
            try {
                prepper.createGIFFrame(i);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    static void progressLearning(){
        MultiLayerPerceptronTrainer.train(perceptron,Integer.parseInt(spinner.getValue().toString()));
    }


}
