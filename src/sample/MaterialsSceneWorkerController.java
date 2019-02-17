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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import sample.AnimationAndDecor.AnimationAndDecor;
import sample.AnimationAndDecor.Shake;

import java.io.*;

import java.util.Arrays;
import java.util.Optional;


public class MaterialsSceneWorkerController {
    private ObservableList<Material> materialData = FXCollections.observableArrayList();
    @FXML
    private TableView<Material> matTable;

    @FXML
    private TableColumn<Material, String> matColumn;

    @FXML
    private TableColumn<Material, String> colorColumn;

    @FXML
    private TableColumn<Material, String> amountColumn;

    @FXML
    private TableColumn<Material, String> extraColumn;
    @FXML
    private Label matLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label extraLabel;

    @FXML
    private Button addInfoButton;

    @FXML
    private Button orderCatBtn;

    @FXML
    private Button newOrderBtn;

    @FXML
    private Button menuBtn;
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private AnchorPane materialStage;




    //метод вызова сцены с каталогом материлов для работников
    void materialsWorkerSceneCall(){
        Stage stage = new Stage();
        stage.setTitle("Каталог материалов");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/matScene2.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AnimationAndDecor.addIcon(stage, "sample/assets/needle.png");
            scene.getStylesheets().add(0, "sample/styles/materialsCatalogScene.css");
            stage.show();
            stage.setOnCloseRequest(event ->{
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Выход");
                alert.setHeaderText("Нажав ОК Вы выйдете из каталога материалов");
                alert.setContentText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                   MainMenuSceneController menuScene = new MainMenuSceneController();
                    menuScene.mainMenuSceneCall();
                    stage.close();
                }
            });
        } catch (IOException e) {
            System.out.println("Файл  matCatalogSceneW.fxml не найден " );
            e.printStackTrace();
        }
    }



//метод для заполнения таблицы материалов из файла
private void matTableFillIn(){
        File file = new File("material.txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Файл material.txt не найден при вызове метода matTableFillIn " + e);
            e.printStackTrace();
        }
        BufferedReader bread = new BufferedReader(reader);
        String line = null;
        try {
            line = bread.readLine();
        } catch (IOException e) {
            System.out.println("Файл material.txt не найден при вызове метода matTableFillIn " + e);
            e.printStackTrace();
        }//сплит сторк и заполнение
        while (line != null) {
            if (line.length() != 0 || !line.equals("")) {
                String[] arrSplit = line.split("/", 4);
                System.out.println(Arrays.toString(arrSplit));
                materialData.add(new Material(arrSplit[0], arrSplit[1], arrSplit[2], arrSplit[3]));
                matColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
                colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
                amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                extraColumn.setCellValueFactory(new PropertyValueFactory<>("extra"));
                matTable.setItems(materialData);
            }
                try {
                    line = bread.readLine();
                } catch (IOException e) {
                    System.out.println("Файл material.txt пуст или не найден " + e);
                    e.printStackTrace();
                }

        }
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Файл material.txt пуст или не найден "+e);
            e.printStackTrace();
        }
    }



//метод для отображения подробной информации
    private void showMaterialDetails(Material material) {

        if (material != null) {
           matLabel.setText(material.getMaterial());
            colorLabel.setText(material.getColor());
            amountLabel.setText(String.valueOf(material.getAmount()));
           extraLabel.setText(material.getExtra());
        } else {
            matLabel.setText(null);
            colorLabel.setText(null);
            amountLabel.setText(null);
            extraLabel.setText(null);
        }
    }



    //метод, вызывающий сцену редактирования для работника
    private boolean showMaterialEditWDialog(Material material) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditWMaterialSceneController.class.getResource("fxml/editWMaterial.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage editWMStage = new Stage();
            editWMStage.setTitle("Редактировать материалы");
            editWMStage.initModality(Modality.WINDOW_MODAL);
            editWMStage.initOwner(null);
            AnimationAndDecor.addIcon(editWMStage,"sample/assets/needle.png");
            Scene scene = new Scene(page);
            editWMStage.setScene(scene);

            // Передаём адресата в контроллер.
            EditWMaterialSceneController controller = loader.getController();
            controller.setEditWMStage(editWMStage);
            controller.setMaterial(material);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            editWMStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            System.out.println("Ошибка вывода диалоговой сцены на экран" + e);
            e.printStackTrace();
            return false;
        }
    }




    private void  handleNewMaterial() {
        matTable.refresh();
        Material tempMaterial = new Material();
        boolean okClicked = this.showMaterialEditWDialog(tempMaterial);
        if (okClicked) {
            materialData.add(tempMaterial);
            matTable.getItems().clear();
            matTableFillIn();
        }
    }




    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleMaterialEdit() {
        System.out.println("вы нажади кнопку изменить");
        Material selectedMaterial = matTable.getSelectionModel().getSelectedItem();
        if (selectedMaterial != null) {
            boolean okClicked = showMaterialEditWDialog(selectedMaterial);
            if (okClicked) {
                showMaterialDetails(selectedMaterial);
                int selectedIndex = matTable.getSelectionModel().getSelectedIndex();
               materialData.set(selectedIndex,selectedMaterial);
                try {
                    Material.rewriteMaterialCatalog(matTable.getItems());
                } catch (IOException e) {
                    System.out.println("Файл materials.txt не найден при вызове метода EditWMaterial "+e);
                    e.printStackTrace();
                }

            }
        } else {
            Shake loginSceneAnim=new Shake(materialStage);
            loginSceneAnim.playAnim();
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выбрано");
            alert.setHeaderText("Ни один материал не выбран");
            alert.setContentText("Пожалйуста, выберите материал");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteOrder() {
        if (User.isLoggedAdmin()) {
            int selectedIndex = matTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление");
                alert.setHeaderText("Нажав ОК Вы удалите материал");
                alert.setContentText("Вы уверены?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    matTable.getItems().remove(selectedIndex);
                    try {
                        Material.rewriteMaterialCatalog(matTable.getItems());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    matTable.getSelectionModel().clearSelection();
                }

            } else {
                Shake loginSceneAnim=new Shake(materialStage);
                loginSceneAnim.playAnim();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(null);
                alert.setTitle("Не выбрано");
                alert.setHeaderText("Не выбран материал");
                alert.setContentText("Пожалуйста, выберите материал в таблице");
                alert.showAndWait();
            }
        }
    }




    @FXML
    void initialize(){
        if (!User.isLoggedAdmin()){
            deleteBtn.setDisable(true);
            addBtn.setDisable(true);
        }
        if (User.isLoggedAdmin()) {
            menuBtn.setVisible(false);
            newOrderBtn.setVisible(false);
            orderCatBtn.setVisible(false);
        }


        matTableFillIn();
        matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> showMaterialDetails(newValue));
        addInfoButton.setOnAction(event-> {
            handleMaterialEdit();
           matTable.getSelectionModel().clearSelection();
        });


  orderCatBtn.setOnAction(event -> {
    OrdersCatSceneW orderSecne=new OrdersCatSceneW();
    orderSecne.orderCatSceneCall();
    orderCatBtn.getScene().getWindow().hide();
});

    newOrderBtn.setOnAction(event -> {
    NewOrderSceneController newOrderScene=new NewOrderSceneController();
    newOrderScene.newOrderSceneCall();
    newOrderBtn.getScene().getWindow().hide();
});

        menuBtn.setOnAction(event -> {
            MainMenuSceneController menuScene=new MainMenuSceneController();
            menuScene.mainMenuSceneCall();
            menuBtn.getScene().getWindow().hide();

        });

        deleteBtn.setOnAction(event -> {handleDeleteOrder();
            matTable.getSelectionModel().clearSelection();
        });

           addBtn.setOnAction(event -> handleNewMaterial());


    }
}
