package com.planb.thespeed.service.datahelper.datasource.sample;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.model.modelForView.OrderItem;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.ProductType;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.model.modelForView.StoreType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 11/30/17.
 *
 * @author channarith.bong
 */

public class SampleData {
    private static List<Store> estores;
    private static List<OrderItem> orderItems;
    private static List<StoreType> estoreTypes;
    private static List<Product> products;
    private static List<ProductType> productTypes;
    private static List<ProductCategory> productCategories;

    private static SampleData instance;

    /**
     *
     */
    private SampleData() {

        estores = new ArrayList<>();
        orderItems = new ArrayList<>();
        estoreTypes = new ArrayList<>();
        products = new ArrayList<>();
        productTypes = new ArrayList<>();
        productCategories = new ArrayList<>();

        generateData();
    }

    /**
     * @return
     */
    public static SampleData getInstance() {
        if (instance == null) {
            instance = new SampleData();
        }
        return instance;
    }

    /**
     *
     */
    private void generateData() {
        getProductTypes();
        getProductCategories();
        getStoreTypes();
        getStores();
        getOrderItems();
    }

    /**
     * @param parentId
     * @return
     */
    private List<Product> getProductList(Integer parentId) {
        if (products.size() == 0) {
            for (int i = 0; i < 6; i++) {
                Product product = new Product();
                product.setId((long) (parentId * 100 + i));
                product.setName("Product Name of " + i);
                product.setPrice(3.24f);
                product.setDetail("Good product near you.");
                product.setProductTypes(getProductTypes());
                products.add(product);
            }
            return products;
        }
        return products;
    }

    /**
     * @return
     */
    private List<ProductType> getProductTypes() {
        if (productTypes.size() == 0) {
            for (int i = 0; i < 3; i++) {
                ProductType productType = new ProductType();
                productType.setId(i);
                productType.setName("Fast Product" + i);
                productTypes.add(productType);
            }
            return productTypes;
        }
        return productTypes;
    }

    /**
     * @return
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * @return
     */
    public List<Store> getStores() {
        if (estores.size() == 0) {
            for (int i = 0; i < 30; i++) {
                Store res = new Store();
                res.setId((long) i);
                res.setName("Res Near You");
                res.setCategories(getProductCategories());
                res.setRate(67f);
                res.setPercentage(70f);
                res.setTag("Meal Deals");
                res.setTimeDelivery("23:40");
                res.setStoreTypes(getStoreTypes());
                estores.add(res);
            }
            return estores;
        }
        return estores;
    }

    /**
     * @param id
     * @return
     */
    public List<ProductCategory> getProductCateByRestId(Integer id) {
        return estores.get(id).getCategories();
    }

    /**
     * @param id
     * @return
     */
    public Store getStoreById(Integer id) {
        return estores.get(id);
    }

    /**
     * @param estores
     */
    public void setStores(List<Store> estores) {
        SampleData.estores = estores;
    }

    /**
     * @param orderItems
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        SampleData.orderItems = orderItems;
    }

    /**
     * @return
     */
    public List<StoreType> getStoreTypes() {
        if (estoreTypes.size() == 0) {
            estoreTypes.add(new StoreType(1, "Donuts", ""));
            estoreTypes.add(new StoreType(2, "Bakery", ""));
            estoreTypes.add(new StoreType(3, "Eggs", ""));
            return estoreTypes;
        }
        return estoreTypes;
    }

    /**
     * @param estoreTypes
     */
    public void setStoreTypes(List<StoreType> estoreTypes) {
        SampleData.estoreTypes = estoreTypes;
    }

    /**
     * @return
     */
    public List<Product> getProducts() {
        if (products.size() == 0) {
            for (int i = 0; i < 5; i++) {
                Product product = new Product();
                product.setId((long) i);
                product.setName("Foot Name [" + i + "]");
                product.setPrice(12.90f);
                product.setDetail("E-Corp Org");
                product.setProductTypes(getProductTypes());
                products.add(product);
            }
        }

        return products;
    }

    /**
     * @param products
     */
    public void setProducts(List<Product> products) {
        SampleData.products = products;
    }

    /**
     * @param productTypes
     */
    public void setProductTypes(List<ProductType> productTypes) {
        SampleData.productTypes = productTypes;
    }

    /**
     * @return
     */
    public List<ProductCategory> getProductCategories() {
        if (productCategories.size() == 0) {
            for (int i = 0; i < 10; i++) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId((long) i);
                productCategory.setName("Category [ " + i + " ]");
                productCategory.setProducts(getProducts());
                productCategories.add(productCategory);
            }
        }
        return productCategories;
    }

    /**
     * @param productCategories
     */
    public void setProductCategories(List<ProductCategory> productCategories) {
        SampleData.productCategories = productCategories;
    }

    /**
     * @return
     */
//    public List<OrderDetail> getOrderItem() {
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            OrderDetail orderDetail = new OrderDetail();
//            Address address = new Address();
//            address.setCity("Phnom Penh");
//            List<ProductItem> productItems = new ArrayList<>();
//            for (int i1 = 0; i1 < 5; i1++) {
//                com.iota.eshopping.model.modelForView.ProductItem productItem = new com.iota.eshopping.model.modelForView.ProductItem();
//                productItem.setItemId(1l);
//                productItem.setName("Product One");
//                productItem.setPrice(12.00);
//                productItem.setQtyOrdered(12);
//                productItem.setRowTotal(144d);
//                productItems.add(productItem);
//            }
//
//            orderDetail.setId(100l);
//            orderDetail.setUpdatedAt("12-JAN-2018");
//            orderDetail.setStatus("Pending");
//            orderDetail.setTotalQtyOrdered(3l);
//            orderDetail.setTotalDue(33d);
//            orderDetail.setStoreName("You Kiss Me");
//            orderDetail.setBillingAddress(address);
//            orderDetail.setProductItems(productItems);
//
//            orderDetails.add(orderDetail);
//        }
//        return orderDetails;
//    }

    /**
     * @param email
     * @return
     */
    public UserSecure getFakeUser(String email) {
        UserSecure userSecure = new UserSecure();
        Customer customer = new Customer();
        if (email != null) {
            customer.setEmail(email);
        } else {
            customer.setEmail("myhostmail_01@xmail.com");
        }

        customer.setFirstname("walter");
        customer.setLastname("send");
        /*customer.setCreatedAt(DateUtil.getCurrent());
        customer.setUpdateAt(DateUtil.getCurrent());*/
        userSecure.setCustomer(customer);
        userSecure.setPassword("Password1");
        return userSecure;
    }
}
