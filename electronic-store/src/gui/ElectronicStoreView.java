package gui;

import baseCode.ElectronicStore;
import baseCode.Product;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectronicStoreView extends Pane {
    ElectronicStore model;

    private Button resetButton;
    private Button addButton;
    private Button removeButton;
    private Button completeButton;

    private ListView<String> stockList;
    private ListView<String> cartList;
    private ListView<String> popularList;

    private Label labelSummary;
    private Label stockSummary;
    private Label currentCart;
    private Label salesNumber;
    private Label revenue;
    private Label sale;
    private Label popular;

    private TextField numberField;
    private TextField revenueField;
    private TextField saleField;

    private ArrayList<String> CartList = new ArrayList<String>();
    private HashMap<Integer, Integer> cartIndexAndProductIndex = new HashMap<Integer, Integer>();



    public ElectronicStoreView(ElectronicStore initModel) {
        model = initModel;

        //create the button
        //reset button
        resetButton = new Button("Reset Store");
        resetButton.setText("Reset Store");
        resetButton.setAlignment(Pos.CENTER);
        resetButton.relocate(40, 510);
        resetButton.setPrefSize(200, 60);

        addButton = new Button("Add to Cart");
        addButton.setText("Add to Cart");
        addButton.setAlignment(Pos.CENTER);
        addButton.relocate(410, 510);
        addButton.setPrefSize(200, 60);

        removeButton = new Button("Remove from Cart");
        removeButton.setText("Remove from Cart");
        removeButton.setAlignment(Pos.CENTER);
        removeButton.relocate(740, 510);
        removeButton.setPrefSize(200, 60);

        completeButton = new Button("Complete Sale");
        completeButton.setText("Complete Sale");
        completeButton.setAlignment(Pos.CENTER);
        completeButton.relocate(960, 510);
        completeButton.setPrefSize(200, 60);

        getChildren().addAll(resetButton, addButton, removeButton, completeButton);


        stockList = new ListView<String>();
        stockList.relocate(300, 50);
        stockList.setPrefSize(420, 440);

        cartList = new ListView<String>();
        cartList.relocate(740, 50);
        cartList.setPrefSize(420, 440);

        popularList = new ListView<String>();
        popularList.relocate(20, 270);
        popularList.setPrefSize(260, 220);


        getChildren().addAll(stockList, cartList, popularList);


        labelSummary = new Label("Store Summary");
        labelSummary.relocate(100, 20);
        labelSummary.setPrefSize(100, 20);

        stockSummary = new Label("Store Stock:");
        stockSummary.relocate(470, 20);
        stockSummary.setPrefSize(100, 20);

        currentCart = new Label("Current Cart:");
        currentCart.relocate(880, 20);
        currentCart.setPrefSize(200, 20);

        salesNumber = new Label("# Sales:");
        salesNumber.relocate(20, 60);
        salesNumber.setPrefSize(100, 20);

        revenue = new Label("Revenue:");
        revenue.relocate(13, 115);
        revenue.setPrefSize(100, 20);

        sale = new Label("$ / Sale:");
        sale.relocate(20, 170);
        sale.setPrefSize(100, 20);

        popular = new Label("Most Popular Items:");
        popular.relocate(90, 235);
        popular.setPrefSize(200, 20);


        getChildren().addAll(labelSummary, stockSummary, currentCart, salesNumber, revenue, sale, popular);


        numberField = new TextField();
        numberField.relocate(80, 50);
        numberField.setPrefSize(200, 40);

        revenueField = new TextField();
        revenueField.relocate(80, 105);
        revenueField.setPrefSize(200, 40);

        saleField = new TextField();
        saleField.relocate(80, 160);
        saleField.setPrefSize(200, 40);


        getChildren().addAll(numberField, revenueField, saleField);

        update();
    }

    public Button getResetButton(){ return resetButton; }

    public Button getAddButton(){ return  addButton; }

    public Button getRemoveButton(){ return removeButton; }

    public Button getCompleteButton(){ return completeButton; }

    public TextField getNumberField() { return  numberField; }

    public TextField getRevenueField() { return revenueField; }

    public TextField getSaleField() { return  saleField; }

    public ListView getStockList() { return stockList; }

    public ListView getCartList() { return cartList; }

    public ListView getPopularList() { return popularList; }

    public Label getCurrentCart() { return currentCart; }

    public void initializeValue(){
        numberField.setText("0");
        revenueField.setText("0.00");
        saleField.setText("N/A");
        currentCart.setText("Current Cart ($0.00):");
    }


    public ArrayList<String> getSelectedString(){
        return CartList;
    }

    public void update(){
        //display stock list

        ArrayList<String> productStringArrayList = model.getProductString();
        stockList.setItems(FXCollections.observableArrayList(productStringArrayList));
        ArrayList cartArrayList = model.getCartList();
        cartArrayList.toArray();
        cartList.setItems(FXCollections.observableArrayList(cartArrayList));
        String[] mostPopularItemList = new String[3];
        mostPopularItemList = model.getMostPopularItemsString();

        popularList.setItems(FXCollections.observableArrayList(mostPopularItemList));


        //set disable
        int stockIndex = stockList.getSelectionModel().getSelectedIndex();
        if(stockIndex >= 0){
           addButton.setDisable(false);
        }else{
            addButton.setDisable(true);
        }

        int cartIndex = cartList.getSelectionModel().getSelectedIndex();
        if(cartIndex >= 0){
            removeButton.setDisable(false);
        }else{
            removeButton.setDisable(true);
        }

        if(cartArrayList.size() > 0){
            completeButton.setDisable(false);
        }else{
            completeButton.setDisable(true);
        }

        currentCart.setText(model.getTotalString());
        numberField.setText(model.getNumSaleString());
        revenueField.setText(model.getRevenueString());
        saleField.setText(model.getPricePerSaleString());


    }



}


