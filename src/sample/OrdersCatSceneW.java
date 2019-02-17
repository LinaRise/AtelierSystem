package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

import sample.AnimationAndDecor.AnimationAndDecor;
import sample.AnimationAndDecor.Shake;


import java.io.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class OrdersCatSceneW {


    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private ObservableList<Material> orderMaterialData= FXCollections.observableArrayList();
    private final ObservableList<String> list = FXCollections.observableArrayList();
    private List<String> matForOrder;
        @FXML
        private TableView<Order> ordersTab;

        @FXML
        private TableColumn<Order, String> orderNum;

        @FXML
        private TableColumn<?,?> fio;

        @FXML
        private Label orderNumLabel;

        @FXML
        private ComboBox stateBox;


    @FXML
    private TextArea measures;

    @FXML
    private TextArea orderTextArea;

    @FXML
        private Label costLabel;

    @FXML
    private TableView<Material> matTab;

    @FXML
    private TableColumn<?, ?> material;

    @FXML
    private TableColumn<?, ?> color;

    @FXML
    private TableColumn<?, ?> amount;


    @FXML
    private Button deleteOrderBtn;

    @FXML
    private Button changeOrderbtn;

    @FXML
    private Button materialsBtn;

    @FXML
    private Button newOrderBtn;

    @FXML
    private Button menuBtn;

    @FXML
    private Label telephone;

    @FXML
    private AnchorPane ordersScene;




    //метод выводящий сцену катлога заквзов
    void orderCatSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("Каталог заказов");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/ordersCatSceneW.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(0, "sample/styles/ordersCatScene.css");
            AnimationAndDecor.addIcon(stage, "sample/assets/needle.png");
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Выход");
                alert.setHeaderText("Нажав Ок вы выйдите из каталога товаров");
                alert.setContentText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    MainMenuSceneController menuScene=new MainMenuSceneController();
                    menuScene.mainMenuSceneCall();
                    stage.close();

                }
            });
        } catch (IOException e) {
            System.out.println("Файл ordersCatSceneW.fxml не найден " );
            e.printStackTrace();

        }

    }



    //метод для заполнения таблицы заказа из файла для конкретног работника
    private void orderTableFillIn() {
        Order order=new Order();
        ArrayList<String> lineArray = null;
        try {
            lineArray = order.ordersFileread();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 1;
        User user=new User();
        String loginOfworker=user.gettingLoggedLogin();

        if(lineArray!=null || !lineArray.isEmpty()){
            for (String line : lineArray) {
            if(line.length()!=0 || !line.equals("")) {
                String[] arrSplit = line.split(Pattern.quote("|"));
System.out.println(Arrays.toString(arrSplit));
                String login=arrSplit[7];
                String clientFio=Client.getClientFio(arrSplit[1]);
                System.out.println(arrSplit[0]);
                System.out.println(clientFio+" fio");
                if (loginOfworker.equals(login) || User.isLoggedAdmin()){
//                System.out.println(Arrays.toString(arrSplit));
                    orderData.add(new Order(Integer.parseInt(arrSplit[0]), clientFio, arrSplit[2], arrSplit[3], arrSplit[4], arrSplit[5], arrSplit[6], arrSplit[7],arrSplit[8]));
                    fio.setCellValueFactory(new PropertyValueFactory<>("clientFio"));
                    orderNum.setCellValueFactory(new PropertyValueFactory<>("orderNum"));
                    ordersTab.setItems(orderData);
                    i++;
                }
            }else i++;
        }
        }
    }


    private void orderDetailTableFillIn(Order order){
        //
        if (order != null) {
            String materials=order.getMatForOrder();
            System.out.println("матеиалы заказа "+materials);
            String[] arrSplit=materials.split(Pattern.quote("\\"));
            matForOrder= new ArrayList<>();
            for (int i=1;i<arrSplit.length;i++){

                System.out.println("arrSplit[i] "+arrSplit[i]);
                String[] arrMaterial=arrSplit[i].split(Pattern.quote("$"));

                    orderMaterialData.add(new Material(arrMaterial[0].trim(),arrMaterial[1].trim(),arrMaterial[2].trim()));
                    System.out.println(arrMaterial[0]+arrMaterial[1]+arrMaterial[2]);
                material.setCellValueFactory(new PropertyValueFactory<>("materilOne"));
                color.setCellValueFactory(new PropertyValueFactory<>("colorOne"));
                amount.setCellValueFactory(new PropertyValueFactory<>("amountOne"));
                matTab.setItems(orderMaterialData);
            }


            orderNumLabel.setText(String.valueOf(order.getOrderNum()));
            stateBox.setValue(order.getState());
            telephone.setText(order.getTelephone());
            measures.setText(order.getMeasures());
            costLabel.setText(order.getCost());
            orderTextArea.setText(order.getOrder());

        }

    }



    //метод, вызывающий сцену редактирования для работника
    public boolean showOrderEditWDialog(Order order) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditWMaterialSceneController.class.getResource("fxml/ordersEditScene.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage ordersEditScene = new Stage();
            AnimationAndDecor.addIcon(ordersEditScene,"sample/assets/needle.png");
            ordersEditScene.setTitle("Редактировать заказ");
            ordersEditScene.initModality(Modality.WINDOW_MODAL);
            ordersEditScene.initOwner(null);
            Scene scene = new Scene(page);
            ordersEditScene.setScene(scene);

            // Передаём адресата в контроллер.
            OrdersEditScene controller = loader.getController();
            controller.setOrderEditStage(ordersEditScene);
            controller.setOrder(order);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            ordersEditScene.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            System.out.println("Ошибка вывода диалоговой сцены на экран" + e);
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    private void handleOrderEdit() {
        System.out.println("вы нажади кнопку изменить");
        Order order=new Order();
        ArrayList<String> ordersArr = null;
        try {
            ordersArr=order.ordersFileread();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Order selectedOrder = ordersTab.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean okClicked = showOrderEditWDialog(selectedOrder);
            if (okClicked) {
                showOrderDetails(selectedOrder);
                int selectedIndex = ordersTab.getSelectionModel().getSelectedIndex();
                orderData.set(selectedIndex,selectedOrder);
                    Order.rewriteOrderCatalog(ordersTab.getItems(),ordersArr);

            }

            }
         else {
            Shake loginSceneAnim=new Shake(ordersScene);
            loginSceneAnim.playAnim();
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выбрано");
            alert.setHeaderText("Ни один заказ не выбран");
            alert.setContentText("Пожалйуста, выберите заказ");

            alert.showAndWait();
        }
    }
    //метод для отображения подробной информации
    private void showOrderDetails(Order order) {

        if (order != null) {
            stateBox.setValue(order.getState());
            orderTextArea.setText(order.getOrder());
            measures.setText(order.getMeasures());
            telephone.setText(order.getTelephone());
            costLabel.setText(order.getCost());
        } else {
            stateBox.setValue(null);
            orderTextArea.setText(null);
            measures.setText(null);
            telephone.setText(null);
            costLabel.setText(null);
        }
    }

    //метод для удаления товара доступно только администратору
    @FXML
    private void handleDeleteOrder() {
        if (User.isLoggedAdmin()) {
            int selectedIndex = ordersTab.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Удаление");
                    alert.setHeaderText("Нажав ОК Вы удалите заказ");
                    alert.setContentText("Вы уверены?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ordersTab.getItems().remove(selectedIndex);
                        try {
                            Order.rewriteOrderCatalog(ordersTab.getItems());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ordersTab.getSelectionModel().clearSelection();
                    }

            } else {
                Shake loginSceneAnim=new Shake(ordersScene);
                loginSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(null);
                alert.setTitle("Не выделено");
                alert.setHeaderText("Не выбран заказ");
                alert.setContentText("Пожалуйста, выберите заказ в таблице");
                alert.showAndWait();
            }
        }
    }

//запрет редактирования
        private void restrictEdit (Node node){
            node.setFocusTraversable(false);
            node.setMouseTransparent(true);

        }







        @FXML
        void initialize () {

            OrdersCatSceneW scene = new OrdersCatSceneW();
            scene.restrictEdit(measures);
            scene.restrictEdit(telephone);
            scene.restrictEdit(stateBox);
            scene.restrictEdit(orderTextArea);
            if (!User.isLoggedAdmin()){
                deleteOrderBtn.setDisable(true);
            }

             if(User.isLoggedAdmin()) {
                 menuBtn.setVisible(false);
                 newOrderBtn.setVisible(false);
                 materialsBtn.setVisible(false);
             }
            list.add("Оплачено");
            list.add("Выполнено");
            list.add("Выдано клиенту");
            stateBox.setItems(list);
            stateBox.setValue("");
            orderTableFillIn();
            ordersTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                matTab.getItems().clear();
                orderDetailTableFillIn(newValue);
            });

            changeOrderbtn.setOnAction(event -> handleOrderEdit());


            menuBtn.setOnAction(event -> {
                event.consume();
                MainMenuSceneController menuScene = new MainMenuSceneController();
                menuScene.mainMenuSceneCall();
                menuBtn.getScene().getWindow().hide();
            });

            newOrderBtn.setOnAction(event -> {
                newOrderBtn.getScene().getWindow().hide();
                NewOrderSceneController newOrderScene=new NewOrderSceneController();
                newOrderScene.newOrderSceneCall();
            });

            materialsBtn.setOnAction(event -> {
                materialsBtn.getScene().getWindow().hide();
                MaterialsSceneWorkerController matScene=new MaterialsSceneWorkerController();
                matScene.materialsWorkerSceneCall();
            });






    }
    }

