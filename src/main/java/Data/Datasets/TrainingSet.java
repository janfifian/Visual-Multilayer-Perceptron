package Data.Datasets;

import java.util.ArrayList;

public class TrainingSet extends Dataset {
    public TrainingSet(){
        super();
    }

    public TrainingSet(ArrayList<ArrayList<Float>> tests){
        super(tests);
    }

    public TrainingSet(Dataset dataset){
        super(dataset.getTests());
    }
}
