package model;

/**
 * Represents a product with various attributes describing it.
 */
public class Products {
    
    /** The unique identifier for each product record. */
    private Long id;
    
    /** Records the name of the product. */
    private String productName;
    
    /** Stores the model of the product. */
    private String model;
    
    /** Captures the unique serial number of each product unit. */
    private String serialNumber;

    // Constructors, getters, setters, toString method

    /**
     * Default constructor for Products.
     */
    public Products() {
    }

    /**
     * Constructs a new Products instance with the specified details.
     * 
     * @param id The unique identifier for the product.
     * @param productName The name of the product.
     * @param model The model of the product.
     * @param serialNumber The unique serial number of the product.
     */
    public Products(Long id, String productName, String model, String serialNumber) {
        this.id = id;
        this.productName = productName;
        this.model = model;
        this.serialNumber = serialNumber;
    }

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Returns a string representation of the Products object,
     * including id, productName, model, and serialNumber.
     *
     * @return A string representation of the Products instance.
     */
    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}