package Presentation;

import Data.Datasets.Dataset;
import Data.Datasets.LogicOperators;
import Data.Datasets.ValidationSet;
import Data.Datasets.TrainingSet;
import Logic.ActivationFunctions.ActivationFunctionChoice;
import Logic.MultiLayerPerceptronBuilder;
import Logic.MultiLayerPerceptronTrainer;
import Logic.NeuronStructures.MultiLayerPerceptron;
import Presentation.Exporters.MaplePlotExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PerceptronVisualInterface extends Frame {
    private static PlotPrepper prepper;
    private static MultiLayerPerceptron perceptron;
    private static Label trainingSetStatus;
    private static Label validationSetStatus;
    private static Label validationSetError;
    private static JSpinner spinner;
    private static JSpinner spinner2;
    private static final JFileChooser validationFileChooser = new JFileChooser();
    private static final JFileChooser trainingFileChooser = new JFileChooser();
    private static JButton selectTrainingDatasetFileButton = new JButton("Select Training Set");
    private static JButton selectValidationDatasetFileButton = new JButton("Select Validation Set");
    private static final String ERROR_ON_TEST_SET_INFO = "Current error on the validation set:  ";
    private static float errorOnValidationSet = 0.0f;

    public static void main(String[] args) {

        /**
         * Plot generator. Generally it is not necessary at all if we just want to generate and teach
         * the neural network itself.
         */
        prepper = new PlotPrepper();

        /**
         * MLP builder instance with input size equal to 2;
         */
        MultiLayerPerceptronBuilder mlpBuilder = new MultiLayerPerceptronBuilder(2);

        /**
         * Setting the input and output size for each sample
         */
        Dataset.setInputSize(2);
        Dataset.setOutputSize(1);

        /**
         * A part devoted to constructing the MLP by the builder.
         */
        perceptron = mlpBuilder
                .addLayer(1,1.0f,ActivationFunctionChoice.Sigmoid)
                .addLayer(1,1.0f, ActivationFunctionChoice.Identity)
                .setLearningCoefficient(0.15f)
                .build();
        prepper.setMultiLayerPerceptron(perceptron);

        /**
         * Modify this part to set plotting ranges.
         */
        prepper.setRanges(0.05f,1.05f,-0.05f,1.05f);

        createAndShowGUI();
    }

    /**
     * Creates and shows up the Graphic User Interface.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Perceptron Visual Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Validations and training sets
        Dataset validationSet = new Dataset(LogicOperators.getXorDataset());
        Dataset trainingSet = new Dataset(LogicOperators.getXorDataset());
        MultiLayerPerceptronTrainer.provideValidationSet(validationSet);
        MultiLayerPerceptronTrainer.provideTrainingSet(trainingSet);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Set up plot prepper display
        prepper.setPoints(MultiLayerPerceptronTrainer.getValidationSet());


        //Size and display the window.
        Insets insets = frame.getInsets();
        frame.setSize(600,700);
        frame.setVisible(true);
    }

    /**
     * Emplaces all the GUI objects.
     * @param pane Pane on which the objects are to be placed
     */
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



        JTextArea instructions = new JTextArea();
        instructions.setLineWrap(true);
        instructions.setEditable(true);
        instructions.setText("The resulting network and its properties will appear\nhere after pressing 'Summary' button.");
        instructions.setBounds(20+insets.left,400+insets.top,560,200);
        pane.add(instructions);

        size = b3.getPreferredSize();
        b3.setBounds(450 + insets.left, 95 + insets.top,
                size.width, size.height);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructions.setText(MaplePlotExporter.exportNeuralNetwork(perceptron));

            }
        });

        trainingSetStatus = new Label();
        trainingSetStatus.setText("Training set: XOR dataset.");
        trainingSetStatus.setBounds(20, 15, 400, 20);
        pane.add(trainingSetStatus);


        /**
         * Launches File-Chooser pop-up and provides some minor information on the picking results.
         */
        selectTrainingDatasetFileButton.setBounds(20, 45, 200, 20);
        selectTrainingDatasetFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = trainingFileChooser.showOpenDialog(pane);
                File inputCSVFile = null;
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    inputCSVFile = trainingFileChooser.getSelectedFile();
                    MultiLayerPerceptronTrainer.provideTrainingSet(TrainingSet.getDatasetFromFile(inputCSVFile));
                    trainingSetStatus.setText("Chosen file: " + inputCSVFile.getName());
                    trainingSetStatus.setForeground(Color.BLUE);
                } else if(inputCSVFile!=null) {
                    //Nothing changes, everything is fine.
                } else {
                    trainingSetStatus.setText("Failed to choose a training dataset file.");
                    trainingSetStatus.setForeground(Color.RED);
                }
            }
        });
        pane.add(selectTrainingDatasetFileButton);

        /**
         * Analogous construct for the validation set.
         */
        selectValidationDatasetFileButton.setBounds(20, 120, 200, 20);
        selectValidationDatasetFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = validationFileChooser.showOpenDialog(pane);
                File inputCSVFile = null;
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    inputCSVFile = validationFileChooser.getSelectedFile();
                    MultiLayerPerceptronTrainer.provideValidationSet(ValidationSet.getDatasetFromFile(inputCSVFile));
                    validationSetStatus.setText("Chosen file: " + inputCSVFile.getName());
                    prepper.setPoints(MultiLayerPerceptronTrainer.getValidationSet());
                    validationSetStatus.setForeground(Color.BLUE);
                    updateValidationSetError();
                } else if(inputCSVFile!=null) {
                    updateValidationSetError();
                    //Nothing changes, everything is fine.
                } else {
                    validationSetStatus.setText("Failed to choose a training dataset file.");
                    validationSetStatus.setForeground(Color.RED);
                }

            }
        });
        pane.add(selectValidationDatasetFileButton);

        validationSetStatus = new Label();
        validationSetStatus.setText("Validation set: XOR dataset.");
        validationSetStatus.setBounds(20, 95, 400, 20);
        pane.add(validationSetStatus);

        Label progressButtonStatus = new Label();
        progressButtonStatus.setText("Training Epochs per button press:");
        progressButtonStatus.setBounds(20, 180, 400, 20);
        pane.add(progressButtonStatus);

        Integer value = 1000;
        Integer min = 1;
        Integer max = 100000;
        Integer step = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner = new JSpinner(model);
        spinner.setBounds(20,200, 100, 25);
        pane.add(spinner);

        validationSetError = new Label();
        updateValidationSetError();
        validationSetError.setBounds(20, 230, 400, 20);
        pane.add(validationSetError);

        JButton b4 = new JButton("Create GIF");
        pane.add(b4);
        size = b4.getPreferredSize();
        b4.setBounds(450 + insets.left, 150,
                size.width, size.height);
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                generateGIFFrames();
                updateValidationSetError();
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

    private static void updateValidationSetError() {
        errorOnValidationSet=MultiLayerPerceptronTrainer.calculateErrorOnValidationSet(perceptron);
        validationSetError.setText(ERROR_ON_TEST_SET_INFO+ errorOnValidationSet);
    }

    /**
     * Generates frames for the making of GIF file.
     */
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

    /**
     * Performs a set number of learning epochs.
     */
    static void progressLearning(){
        MultiLayerPerceptronTrainer.train(perceptron,Integer.parseInt(spinner.getValue().toString()));
        updateValidationSetError();
    }


}
