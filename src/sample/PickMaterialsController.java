package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.stage.Stage;

import sample.AnimationAndDecor.AnimationAndDecor;

import java.io.*;
import java.util.ArrayList;

import java.util.ListIterator;
import java.util.Optional;

public class PickMaterialsController {
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
    private Button addMaterials;



    //����� ��������� ����� ������ ���������� ��� ������
    void pickSceneCall() {
        Stage stage = new Stage();
        stage.setTitle("");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/pickMaterials.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            scene.getStylesheets().add(0, "sample/styles/pickMaterials.css");
            AnimationAndDecor.addIcon(stage, "sample/assets/needle.png");
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("�����");
                alert.setHeaderText("����� �� �� ���������� ����� ������� ��� ������");
                alert.setContentText("�� �������?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();

                }
            });
        } catch (IOException e) {
            System.out.println("���� pickMaterials.fxml �� ������ ");
            e.printStackTrace();
        }

    }




    //����� ��� ���������� ������� ���������� �� �����
    private void matTableFillIn() {
        File file = new File("material.txt");

        BufferedReader bread = null;
        try {
            bread = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = bread.readLine();
        } catch (IOException e) {
            System.out.println("���� material.txt �� ������ ��� ������ ������ matTableFillIn " + e);
            e.printStackTrace();
        }//����� ����� � ����������
        while (line != null) {
            if (line.length() != 0 || !line.equals("")) {
                String[] arrSplit = line.split("/", 4);
                materialData.add(new Material(arrSplit[0], arrSplit[1],arrSplit[2], arrSplit[3]));
                matColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
                colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
                amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                extraColumn.setCellValueFactory(new PropertyValueFactory<>("extra"));
                matTable.setItems(materialData);
                matTable.setEditable(true);
                //amountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            try {
                line = bread.readLine();
            } catch (IOException e) {
                System.out.println("���� material.txt ���� ��� �� ������ " + e);
                e.printStackTrace();
            }

        }
        try {
            bread.close();
        } catch (IOException e) {
            System.out.println("���� material.txt ���� ��� �� ������ "+e);
            e.printStackTrace();
        }
    }
    //��� ������ �������������� ������ ���������� ����������
@FXML
    public void onEditCommit(TableColumn.CellEditEvent<Material, String> materialStringCellEditEvent) {
        Material material=matTable.getSelectionModel().getSelectedItem();
        material.setAmount(materialStringCellEditEvent.getNewValue());
    }

//�����  ��������� ��� ������
   void getMaterialsForOrder() {
        matTable.setOnMousePressed(event -> addMaterials.setOnAction(event1->{
       ObservableList<Material> selectedItems = matTable.getSelectionModel().getSelectedItems();
       System.out.println(selectedItems);

       // TEST
       ArrayList<String> selectedData = new ArrayList<>();
       for (Material material : selectedItems) {
           selectedData.add(material.getMaterial() + "$" + material.getColor() + "$" + material.getAmount());
       }
       try {
           PrintWriter writer = new PrintWriter("matForOrder.txt","windows-1251");
           writer.write(String.valueOf(selectedData));
           writer.flush();
           writer.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       ListIterator<String> iterator = selectedData.listIterator();
       while (iterator.hasNext()) {
           System.out.println(iterator.next());
       }
       // END TEST
        addMaterials.getScene().getWindow().hide();
        }));


    }


    @FXML
    void initialize(){

        matTableFillIn();
        matTable.setEditable(true);
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        matTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getMaterialsForOrder();

       // matTable.setEditable(true);
       // amountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //matTable.setEditable(true);
        //amountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }


}
