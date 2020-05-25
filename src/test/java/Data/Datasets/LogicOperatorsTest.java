package Data.Datasets;

import org.junit.Assert;
import org.junit.Test;

public class LogicOperatorsTest {

    @Test
    public void getAnyDataset() {
        Assert.assertEquals(4,LogicOperators.getOrDataset().size());
        Assert.assertEquals(4,LogicOperators.getAndDataset().size());
        Assert.assertEquals(4,LogicOperators.getXorDataset().size());
        Assert.assertEquals(4,LogicOperators.getIffDataset().size());
        Assert.assertEquals(4,LogicOperators.getImpliesDataset().size());
    }
}