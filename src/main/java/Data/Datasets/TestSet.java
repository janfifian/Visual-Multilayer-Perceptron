package Data.Datasets;

import java.util.ArrayList;

public class TestSet extends Dataset {
    public TestSet(){
        super();
    }

    public TestSet(ArrayList<ArrayList<Float>> tests){
        super(tests);
    }

    public TestSet(Dataset dataset){
        super(dataset.getTests());
    }
}
