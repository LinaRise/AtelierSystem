package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;


public class OrdersEditScene {
    private final ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private ComboBox stateBox;

    @FXML
    private TextArea orderInfo;

    @FXML
    private TextArea measures;

    @FXML
    private TextArea telephone;

    @FXML
    private TextArea cost;


    private Stage orderEditStage;
    private Order order;
    private boolean okClicked = false;

//��������� ����� ��� ����

    public void setOrderEditStage(Stage orderEditStage) {
        this.orderEditStage = orderEditStage;
    }

    //�����������  ����� �������� ������� ������
    public void setOrder(Order order) {
        this.order = order;
        stateBox.setValue(order.getState());
        measures.setText(order.getMeasures());
        orderInfo.setText(order.getOrder());
        telephone.setText(order.getTelephone());
        cost.setText(order.getCost());
    }


    //�����, ������������ true, ���� ������������ ������� OK, � ������ ������ false
    public boolean isOkClicked() {
        return okClicked;
    }

    //�����, ������� ���������� true, ���� ������, ��������� � ��������� ����, ����������
    private boolean isInputValid() {
        final int i = 11;
        String errorMessage = "";

        if (measures.getText() == null || measures.getText().length() == 0) {
            errorMessage += "��� ��������� �����!\n";
        }
        if (orderInfo.getText() == null || orderInfo.getText().length() == 0) {
            errorMessage += "��� ���������� �������� ������!\n";
        }
        if (telephone.getText() == null || telephone.getText().length() == 0 || !telephone.getText().matches("\\d+") || telephone.getText().length() > i) {
            errorMessage += "��� ���������� ������� �����,���� ����� ������ �������!\n";
        }
        if (cost.getText() == null || cost.getText().length() == 0) {
            errorMessage += "�� ������� ���������!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // ���������� ��������� �� ������.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(orderEditStage);
            alert.setTitle("������ ");
            alert.setHeaderText("����������, ������� ���������� ���������� � ������");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

    }

//���� ������ ��
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            order.setState(stateBox.getValue().toString());
            order.setOrder(orderInfo.getText());
            order.setMeasures(measures.getText());
            order.setTelephone(telephone.getText());
            order.setCost(cost.getText());
            Order.addOrder(order);

            okClicked = true;
            orderEditStage.close();
        }
    }
//���� ������ ������
    @FXML
    private void handleCancel() {
        orderEditStage.close();
    }

//����� ��� ����������� �����  � ���� ���������
    public void costTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter = 20;

        String key = keyEvent.getCharacter();

        if ((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals(".")) || (cost.getText().length() >= maxCharacter))) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.beep();
            keyEvent.consume();
        }
    }


    //����� ��� ����� �  ���� ��������  ������ ���� � �������� ������ ��� ������ �������������
    public void phoneTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter = 11;
        String key = keyEvent.getCharacter();
        if ((!key.isEmpty() && !Character.isDigit(key.charAt(0)) || (telephone.getText().length() >= maxCharacter))) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.beep();
            keyEvent.consume();
        }
    }

    @FXML
    void initialize() {
        list.add("��������");
        list.add("���������");
        list.add("������ �������");
        stateBox.setItems(list);
        stateBox.setValue("");


        orderInfo.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
               measures.requestFocus();
            }

        });

        measures.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                telephone.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
               orderInfo.requestFocus();
            }
        });

        telephone.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DOWN)) {
                cost.requestFocus();
            }
            if (event.getCode().equals(KeyCode.UP)){
                measures.requestFocus();
            }
        });
        cost.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                telephone.requestFocus();
            }
        });




    }
}


