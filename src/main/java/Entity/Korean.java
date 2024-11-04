package Entity;
import javax.persistence.*;
@Entity
@Table(name = "Korean")
public class Korean {
    @Id
    @Column(name = "SNO")
    private int sNo;

    @Column(name = "dName")
    private String dName;

    @Column(name = "price")
    private int price;

    public Korean() {

    }

    public Korean(int sNo, String dName, int price) {
        this.sNo = sNo;
        this.dName = dName;
        this.price = price;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-10d |", sNo, dName, price);

    }
}

