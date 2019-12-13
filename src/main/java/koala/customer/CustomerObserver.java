package koala.customer;

public interface CustomerObserver {
    void addCustomer(Customer customer);

    void changeCustomer(int index, Customer customer);
}
