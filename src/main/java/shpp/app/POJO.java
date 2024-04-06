package shpp.app;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

//this is not a pojo :)
public class POJO {
    @CsvBindByPosition(position = 0)
    @NotNull
    @Size(min = 7)
    @ContainSymbol(symbol = "a")
    private String name;
    @NotNull
    private String EDDR;
    @CsvBindByPosition(position = 1)
    @Min(10)
    private int count;
    @NotNull
    LocalDate createdAt;


    @Override
    public int hashCode() {
        return Objects.hash(name, EDDR, count, createdAt);
    }

    public POJO(String name, String eddr, int count, LocalDate createdAt) {
        this.name = name;
        this.EDDR = eddr;
        this.count = count;
        this.createdAt = createdAt;
    }

    public String getEDDR() {
        return EDDR;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public POJO() {
    }

    public POJO setName(String name) {
        this.name = name;
        return this;
    }

    public POJO setEDDR(String eddr) {
        this.EDDR = eddr;
        return this;
    }

    public POJO setCount(int count) {
        this.count = count;
        return this;
    }

    public POJO setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof POJO)) return false;
        POJO pojo = (POJO) o;
        return count == pojo.count && Objects.equals(name, pojo.name) && Objects.equals(EDDR, pojo.EDDR) && Objects.equals(createdAt, pojo.createdAt);
    }
}
