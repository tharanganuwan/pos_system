package tm;

public class StockTM {
    private String id;
    private String name;
    private String price;
    private String quantity;
    private String mfd;
    private String exp;

    public StockTM() {
    }

    public StockTM(String id, String name, String price, String quantity, String mfd, String exp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.mfd = mfd;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMfd() {
        return mfd;
    }

    public void setMfd(String mfd) {
        this.mfd = mfd;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
