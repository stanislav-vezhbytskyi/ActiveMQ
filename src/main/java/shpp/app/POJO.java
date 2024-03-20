package shpp.app;

import java.io.Serializable;
import java.time.LocalDate;

//this is not a pojo :)
public class POJO implements Serializable {
    String name;
    String eddr;
    int count;
    LocalDate createdAt;

    public POJO(String name, String eddr, int count, LocalDate createdAt) {
        this.name = name;
        this.eddr = eddr;
        this.count = count;
        this.createdAt = createdAt;
    }

    public POJO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEddr(String eddr) {
        this.eddr = eddr;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "POJO{" +
                "name='" + name + '\'' +
                ", eddr='" + eddr + '\'' +
                ", count=" + count +
                ", createdAt=" + createdAt +
                '}';
    }
}
