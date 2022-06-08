package ewu.cse.shopmanagementsystem;

public class ProductHelper {

    private String productName = "";
    private String price = "";
    private String expDate = "";
    private String category = "";
    private String quantity = "";
    private String productId =productName+category;

    public ProductHelper() {

    }

    public void setProductId(String productName,String category) {
        this.productId = productName+category;
    }

    public ProductHelper(String productName, String price, String expDate, String category, String quantity) {
        this.productName = productName;
        this.price = price;
        this.expDate = expDate;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int dataCount() {
        int c = 5;
        if (getCategory().matches("")) {
            c--;
        }
        if (getExpDate().matches("")) {
            c--;
        }
        if (getProductName().matches("")) {
            c--;
        }
        if (getQuantity().matches("")) {
            c--;
        }
        if (getPrice().matches("")) {
            c--;
        }
        return c;
    }

    @Override
    public String toString() {
        return "productHelper{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", expDate='" + expDate + '\'' +
                ", category='" + category + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
