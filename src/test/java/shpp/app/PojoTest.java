package shpp.app;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PojoTest {
    @Test
    public void getCountTest() {
        POJO pojo = new POJO();
        pojo.setCount(5);
        Assert.assertEquals(pojo.getCount(), 5);
    }

    @Test
    public void getNameTest() {
        POJO pojo = new POJO();
        pojo.setName("name");
        Assert.assertEquals(pojo.getName(), "name");
    }

    @Test
    public void getEDDRTest() {
        POJO pojo = new POJO();
        pojo.setEDDR("1");
        Assert.assertEquals(pojo.getEDDR(), "1");
    }

    @Test
    public void getCreatedAtTest() {
        POJO pojo = new POJO();
        pojo.setCreatedAt(LocalDate.MAX);
        Assert.assertEquals(pojo.getCreatedAt(), LocalDate.MAX);
    }
}

