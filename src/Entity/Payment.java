package Entity;

public class Payment {
    private String InvoiceNumber;
    private double Amount;

    public Payment(String InvoiceNumber, double Amount) {
        this.InvoiceNumber = InvoiceNumber;
        this.Amount = Amount;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String InvoiceNumber) {
        this.InvoiceNumber = InvoiceNumber;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }
}
