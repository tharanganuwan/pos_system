package tm;

public class BuyItemBillingTM {
    private String itemName;
    private String itemPrice;
    private String quantity;
    private String total;

    public BuyItemBillingTM() {
    }

    public BuyItemBillingTM(String itemName, String itemPrice, String quantity, String total) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
