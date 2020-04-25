package Data.Datasets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * A class representing set of examples for the neural network
 */
public class Dataset {
    private ArrayList<ArrayList<Float>> tests;
    /**
     * Holds information about how many input parameters are held in the single sample
     */
    private static int inputSize;

    /**
     * Holds information about how many output results are held in the single sample
     */
    private static int outputSize;
    /**
     * Holds information on what separator is used in the sample files
     */
    private static final String FIELD_SEPARATOR = "\t";

    /**
     * Creates an empty instance of Dataset
     */
    public Dataset(){
        tests = new ArrayList<ArrayList<Float>>();
    }

    /**
     * Creates a Dataset containing given array of samples
     * @param tests
     */
    public Dataset(ArrayList<ArrayList<Float>> tests){
        this.tests = tests;
    }

    /**
     * Returns the array of samples held
     * @return
     */
    public ArrayList<ArrayList<Float>> getTests() {
        return tests;
    }

    /**
     * Replaces the array of samples held so far with a given one.
     * @param tests
     */
    public void setTests(ArrayList<ArrayList<Float>> tests) {
        tests = tests;
    }

    /**
     * Adds a single sample to the Dataset
     * @param test A sample to be added
     */
    public void addTest(ArrayList<Float> test){
        getTests().add(test);
    }

    /**
     * Sets the inputSize variable to a given value.
     * @param size
     */
    public static void setInputSize(int size){
        inputSize = size;
    }

    /**
     * Sets the outputSize variable to a given value.
     * @param size
     */
    public static void setOutputSize(int size){
        outputSize = size;
    }

    /**
     * Reads input part from the single sample
     * @param i index of the sample to read
     * @return array of input variables
     */
    public ArrayList<Float> getInput(int i){
        return new ArrayList<Float>(this.tests.get(i).subList(0,inputSize));
    }

    /**
     * Reads Dataset instance from a text/csv file.
     * @param inputFile
     * @return
     */
    public Dataset getDatasetFromFile(File inputFile){
        Dataset dataset = new Dataset();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inputFile));

            int n = Integer.parseInt(br.readLine());
            for(int i = 0; i < n; i++){
                dataset.addTest(mapToTestInstance(br.readLine().split(FIELD_SEPARATOR) ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Converts a row of information from the file to the single sample for a Dataset.
     * @param itemsToMap
     * @return
     */
    private ArrayList<Float> mapToTestInstance(String[] itemsToMap){
        ArrayList<Float> STI = new ArrayList<Float>();
        STI.add(Float.parseFloat(itemsToMap[0]));
        STI.add(Float.parseFloat(itemsToMap[1]));
        STI.add(Float.parseFloat(itemsToMap[2]));
        return STI;
    }

    /**
     * Gets the output part of the i-th sample
     * @param i index of the sample to be read
     * @return array of output variables
     */
    public ArrayList<Float> getOutput(int i){
        return new ArrayList<Float>(this.tests.get(i).subList( inputSize,inputSize+outputSize));
    }
}
