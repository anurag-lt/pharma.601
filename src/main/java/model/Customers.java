package model;

import java.util.Date;

/**
 * Represents a customer with their details.
 */
public class Customers {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Date registerDate;

    /**
     * Gets the unique identifier for the customer.
     * @return the customer's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the customer.
     * @param id the customer's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the customer.
     * @return the customer's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the customer.
     * @param firstName the customer's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the customer.
     * @return the customer's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the customer.
     * @param lastName the customer's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the customer.
     * @return the customer's email address.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address of the customer.
     * @param emailAddress the customer's email address.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the phone number of the customer.
     * @return the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     * @param phoneNumber the customer's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the registration date of the customer.
     * @return the date when the customer was registered.
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * Sets the registration date of the customer.
     * @param registerDate the date when the customer was registered.
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}