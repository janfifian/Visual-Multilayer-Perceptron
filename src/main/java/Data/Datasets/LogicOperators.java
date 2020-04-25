package Data.Datasets;

import java.util.ArrayList;
import java.util.List;

public class LogicOperators {
    /**
     * Gets the dataset for AND
     * @return
     */
    public static ArrayList<ArrayList<Float >> getAndDataset(){
        ArrayList<ArrayList<Float> > tests = new ArrayList<>();
        tests.add(new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(0.0f,1.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,1.0f,1.0f)) );
        return tests;
    }

    /**
     * Gets the dataset for OR
     * @return
     */
    public static ArrayList<ArrayList<Float >> getOrDataset(){
        ArrayList<ArrayList<Float> > tests = new ArrayList<>();
        tests.add(new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(0.0f,1.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,0.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,1.0f,1.0f)) );
        return tests;
    }

    /**
     * Gets the dataset for XOR
     * @return
     */
    public static ArrayList<ArrayList<Float >> getXorDataset(){
        ArrayList<ArrayList<Float> > tests = new ArrayList<>();
        tests.add(new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(0.0f,1.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,0.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,1.0f,0.0f)) );
        return tests;
    }

    /**
     * Gets the dataset for IFF
     * @return
     */
    public static ArrayList<ArrayList<Float >> getIffDataset(){
        ArrayList<ArrayList<Float> > tests = new ArrayList<>();
        tests.add(new ArrayList<Float>(List.of(0.0f,0.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(0.0f,1.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,1.0f,1.0f)) );
        return tests;
    }

    /**
     * Gets the dataset for Implication
     * @return
     */
    public static ArrayList<ArrayList<Float >> getImpliesDataset(){
        ArrayList<ArrayList<Float> > tests = new ArrayList<>();
        tests.add(new ArrayList<Float>(List.of(0.0f,0.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(0.0f,1.0f,1.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,0.0f,0.0f)) );
        tests.add(new ArrayList<Float>(List.of(1.0f,1.0f,1.0f)) );
        return tests;
    }
}
