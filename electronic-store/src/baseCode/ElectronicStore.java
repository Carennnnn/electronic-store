package baseCode;

//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import gui.ElectronicStoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class ElectronicStore{
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    //ElectronicStoreView view;
    String name;
    Product[] stock; //Array to hold all products
    private int[] productPrice = new int[MAX_PRODUCTS];
    private int[] productAmountList = new int[MAX_PRODUCTS];
    private ArrayList<String> cartList = new ArrayList<String>();
    private HashMap<Integer, Integer> cartIndexAndProductIndex = new HashMap<Integer, Integer>();
    private String totalString = "Current Cart ($0.00):";
    private double totalPrice = 0;
    private int numSale = 0;
    private double revenue = 0.0;
    private double pricePerSale = 0.0;
    private String[] mostPopularItemsString = new String[3];



    public ElectronicStore(String initName){
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
    }

    public String getName(){
        return name;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct){
        if(curProducts < MAX_PRODUCTS){
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    //Sells 'amount' of the product at 'index' in the stock array
    //Updates the revenue of the store
    //If no sale occurs, the revenue is not modified
    //If the index is invalid, the revenue is not modified
    public void sellProducts(int index, int amount){
        if(index >= 0 && index < curProducts){
            revenue += stock[index].sellUnits(amount);
        }
    }

    public double getRevenue(){
        return revenue;
    }

    public int getCurProducts() { return curProducts; }

    //Prints the stock of the store
    public void printStock(){
        for(int i = 0; i < curProducts; i++){
            System.out.println(i + ". " + stock[i] + " (" + stock[i].getPrice() + " dollars each, " + stock[i].getStockQuantity() + " in stock, " + stock[i].getSoldQuantity() + " sold)");
        }
    }

    public void completeSale(){
        numSale++;
        revenue += totalPrice;
        pricePerSale = revenue/numSale;
        totalPrice = 0;
        cartList.clear();
        mostPopularItem();
    }

    public void mostPopularItem(){
        int[] newProductAmountList = new int[curProducts];
        for(int i = 0; i < curProducts; i++){
            newProductAmountList[i] = productAmountList[i];
        }
        int temp = 0;
        int n = curProducts;
        for(int i = 0; i < n; i++){
            for(int j = 1; j < n-i; j++){
                if(newProductAmountList[j-1] > newProductAmountList[j]){
                    temp = newProductAmountList[j-1];
                    newProductAmountList[j-1] = newProductAmountList[j];
                    newProductAmountList[j] = temp;
                }
            }
        }

        int[] popularIndex = new int[3];
        for(int i = 0; i < n; i++){
            if(productAmountList[i] == newProductAmountList[0]){
                popularIndex[0] = i;
                break;
            }
        }

        for(int i = 0; i < n; i++){
            if(productAmountList[i] == newProductAmountList[1] && i!= popularIndex[0]){
                popularIndex[1] = i;
                break;
            }
        }

        for(int i = 0; i < n; i++){
            if(productAmountList[i] == newProductAmountList[2] && i!= popularIndex[0] && i!=popularIndex[1]){
                popularIndex[2] = i;
                break;
            }
        }

        mostPopularItemsString[0] = stock[popularIndex[0]].toString();
        mostPopularItemsString[1] = stock[popularIndex[1]].toString();
        mostPopularItemsString[2] = stock[popularIndex[2]].toString();


    }

    public String[] getMostPopularItemsString(){
        for(int i = 0; i < 3; i++){
            if(mostPopularItemsString[i]==(null)){
                mostPopularItemsString[i] = stock[i].toString();
            }
        }
        return mostPopularItemsString;
    }




    public String getNumSaleString(){
        return String.format("%d", numSale);
    }

    public String getRevenueString(){
        return String.format("%.2f", revenue);
    }

    public String getPricePerSaleString(){
        if(pricePerSale == 0){
            return "N/A";
        }
        return String.format("%.2f", pricePerSale);
    }


    public Product[] getStock(){
        return stock;
    }

    public int[] getProductAmount(){

        for(int i = 0; i < curProducts; i++){
            productAmountList[i] = 10;
        }
        return productAmountList;
    }

    public int[] getProductPrice(){
        productPrice = new int[curProducts];
        productPrice[0] = 100;
        productPrice[1] = 200;
        productPrice[2] = 150;
        productPrice[3] = 250;
        productPrice[4] = 500;
        productPrice[5] = 750;
        productPrice[6] = 25;
        productPrice[7] = 75;

        return productPrice;
    }


    public ArrayList<String> getProductString() {
        ArrayList<String> productString = new ArrayList<>();
        for (int i = 0; i < curProducts; i++) {
            if(hasNoStock(i)) {

            }else{
                productString.add(stock[i].toString());
            }
        }
        return productString;
    }


    public void reset(){
        for(int i = 0; i < curProducts; i++){
            productAmountList[i] = 10;
        }
        cartList.clear();
        totalPrice = 0;
        numSale = 0;
        revenue = 0.0;
        pricePerSale = 0.0;
        mostPopularItemsString = new String[3];
    }



    public void addProductToCart(int index){
        int stockIndex=0;
        for(int i =0;i<curProducts;i++){
            if(stock[i].toString().equals(getProductString().get(index))){
                stockIndex = i;
            }
        }

        if(productAmountList[stockIndex] > 0) {
            productAmountList[stockIndex] -= 1;
            cartList.add(stock[stockIndex].toString());
            productPrice = getProductPrice();
            totalPrice += productPrice[stockIndex];
            cartIndexAndProductIndex.put(cartList.size()-1, stockIndex);
        }
    }


    public boolean hasNoStock(int i){
        if(productAmountList[i] == 0){
            return true;
        }
        return false;
    }


    public String getTotalString(){
        totalString = String.format("Current Cart ($%.2f):", totalPrice);
        return totalString;
    }

    public ArrayList<String> getCartList(){
        return cartList;
    }

    public void removeItemFromCart(int cartIndex){
        int index = cartIndexAndProductIndex.get(cartIndex);
        cartList.remove(cartIndex);
        productAmountList[index]++;
    }


    public static ElectronicStore createStore(){
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", 15.5, false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", 23, true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", 8, false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", 12, true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
}
