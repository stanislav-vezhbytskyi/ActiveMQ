package shpp.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContainSymbolValidatorTest {
    private ContainSymbolValidator validator;
    @Before
    public void setUp(){
        validator = new ContainSymbolValidator();
    }
    @Test
    public void containSymbolTest() throws IllegalAccessException {
        POJO pojo = new POJO();
        pojo.setName("fjfjfja");
        Assert.assertTrue(validator.isValid(pojo));
    }
    @Test
    public void doesNotContainSymbolTest() throws IllegalAccessException {
        POJO pojo = new POJO();
        pojo.setName("ffffffff");
        Assert.assertFalse(validator.isValid(pojo));
    }
}
