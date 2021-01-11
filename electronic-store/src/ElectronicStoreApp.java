//Yuelin Liu
//101154473



import baseCode.ElectronicStore;
import gui.ElectronicStoreView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;

public class ElectronicStoreApp extends Application {

    ElectronicStore model;
    ElectronicStoreView view;
    private int[] productAmount;


    public void start(Stage primaryStage){
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleReset();
            }
        });

        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleAdd();
            }
        });

        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleRemove();
            }
        });

        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleComplete();
            }
        });

        view.getStockList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent m1) {
                handleStockListSelection();
            }
        });

        view.getCartList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent m2) {
                handleCartListSelection();
            }
        });

        primaryStage.setTitle("Electronic Store Application - Watts Up Electronics");
        primaryStage.setScene(new Scene(view, 1200, 600));
        view.initializeValue();
        productAmount = model.getProductAmount();
        primaryStage.show();
        view.update();
    }

    public void handleReset(){
        model.reset();
        view.update();
    }

    public void handleAdd(){
        int stockIndex = view.getStockList().getSelectionModel().getSelectedIndex();

        if(stockIndex >= 0){
            model.addProductToCart(stockIndex);
            view.update();
        }
    }


    public void handleRemove(){
        int cartIndex = view.getCartList().getSelectionModel().getSelectedIndex();
        if(cartIndex >= 0){
            model.removeItemFromCart(cartIndex);
            view.update();
        }

    }

    public void handleComplete(){
        model.completeSale();
        view.update();
    }

    public void handleStockListSelection(){
        int stockIndex = view.getStockList().getSelectionModel().getSelectedIndex();
        view.update();
    }

    public void handleCartListSelection(){
        int cartIndex = view.getCartList().getSelectionModel().getSelectedIndex();
        view.update();
    }

    public static void main(String[] args){launch(args);}

}
