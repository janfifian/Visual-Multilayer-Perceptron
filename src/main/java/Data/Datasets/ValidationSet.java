package Data.Datasets;

import java.util.ArrayList;

/**
 * A specialised Dataset class containing validation values used to check to what extent is the network ready for real challenges.
 */
public class ValidationSet extends Dataset {
    /**
     * Constructs an empty ValidationSet.
     */
    public ValidationSet(){
        super();
    }

    /**
     * Constructs a ValidationSet from the array of validation samples.
     * @param tests ArrayList of test samples (in the form of ArrayList of values)
     */
    public ValidationSet(ArrayList<ArrayList<Float>> tests){
        super(tests);
    }

    /**
     * Specialises Dataset to a ValidationSet
     * @param dataset Dataset to be turned into ValidationSet.
     */
    public ValidationSet(Dataset dataset){
        super(dataset.getTests());
    }
}
