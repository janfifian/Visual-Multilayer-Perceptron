package Data.Datasets;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A Dataset class used to contain samples which are used to train the neural network.
 * It is important to point out, that both TrainingSet and TestSet classes are introduced only to keep the
 * code as clear as possible (especially due to the chaotic nature of the code I write) and it would be
 * completely fine to stick just to the DataSet class instead.
 */
public class TrainingSet extends Dataset {
    /**
     * Constructs an empty TrainingSet.
     */
    public TrainingSet(){
        super();
    }

    /**
     * Constructs a TrainingSet from the array of test samples.
     * @param tests ArrayList of training samples (in the form of ArrayList of values)
     */
    public TrainingSet(ArrayList<ArrayList<Float>> tests){
        super(tests);
    }


    /**
     * Specialises Dataset to a TrainingSet
     * @param dataset Dataset to be turned into TrainingSet.
     */
    public TrainingSet(Dataset dataset){
        super(dataset.getTests());
    }

    public void shuffle() {
        Collections.shuffle(this.getTests());
    }
}
