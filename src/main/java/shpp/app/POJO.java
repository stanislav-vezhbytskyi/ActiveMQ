package shpp.app;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

//this is not a pojo :)
public class POJO {
    @CsvBindByPosition(position = 0)
    @NotNull
    @Size(min = 7)
    @ContainSymbol(symbol = "a")
    String name;
    @NotNull
    String EDDR;
    @CsvBindByPosition(position = 1)
    @Min(10)
    int count;

    public String getEDDR() {
        return EDDR;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @NotNull
    LocalDate createdAt;

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public POJO(String name, String eddr, int count, LocalDate createdAt) {
        this.name = name;
        this.EDDR = eddr;
        this.count = count;
        this.createdAt = createdAt;
    }

    public POJO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEDDR(String eddr) {
        this.EDDR = eddr;
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
                ", eddr='" + EDDR + '\'' +
                ", count=" + count +
                ", createdAt=" + createdAt +
                '}';
    }
}
