package tm;

public class BillingItemsSummaryTM {
    private String invoiceId;
    private String date;
    private String time;
    private String total;
    private String discount;
    private String payment;
    private String balance;

    public BillingItemsSummaryTM() {
    }

    public BillingItemsSummaryTM(String invoiceId, String date, String time, String total, String discount, String payment, String balance) {
        this.invoiceId = invoiceId;
        this.date = date;
        this.time = time;
        this.total = total;
        this.discount = discount;
        this.payment = payment;
        this.balance = balance;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
