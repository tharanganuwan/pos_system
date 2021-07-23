package tm;

public class SearchItemsBillingTM {
    private String idItem;
    private String nameItem;
    private Double priceItem;
    private Double quantityItem;

    public SearchItemsBillingTM() {
    }

    public SearchItemsBillingTM(String idItem, String nameItem, Double priceItem, Double quantityItem) {
        this.idItem = idItem;
        this.nameItem = nameItem;
        this.priceItem = priceItem;
        this.quantityItem = quantityItem;
    }


    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Double getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(Double priceItem) {
        this.priceItem = priceItem;
    }

    public Double getQuantityItem() {
        return quantityItem;
    }

    public void setQuantityItem(Double quantityItem) {
        this.quantityItem = quantityItem;
    }

    @Override
    public String toString() {
            //return  nameItem;

            if(nameItem!=null){
                return nameItem;
            }
            if(idItem!=null)
                return idItem;

            return "";
    }

}
