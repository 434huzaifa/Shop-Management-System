package ewu.cse.shopmanagementsystem;

public class Customer {
    private String productName = "";
    private String productQuantity = "";
    private String totalProductPrice = "";

    public Customer() {

    }

    public Customer(String productName, String productQuantity, String totalProductPrice) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.totalProductPrice = totalProductPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(String totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "productName='" + productName + '\'' +
                ", productQuantity='" + productQuantity + '\'' +
                ", totalProductPrice='" + totalProductPrice + '\'' +
                '}';
    }
}
