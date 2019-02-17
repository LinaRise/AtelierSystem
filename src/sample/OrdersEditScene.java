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

//устанвока сцены дял окна

    public void setOrderEditStage(Stage orderEditStage) {
        this.orderEditStage = orderEditStage;
    }

    //присваиваем  полям значения объекта класса
    public void setOrder(Order order) {
        this.order = order;
        stateBox.setValue(order.getState());
        measures.setText(order.getMeasures());
        orderInfo.setText(order.getOrder());
        telephone.setText(order.getTelephone());
        cost.setText(order.getCost());
    }


    //метод, возвращающий true, если пользователь кликнул OK, в другом случае false
    public boolean isOkClicked() {
        return okClicked;
    }

    //метод, который возвращает true, если данные, введенные в текстовые поля, корректные
    private boolean isInputValid() {
        final int i = 11;
        String errorMessage = "";

        if (measures.getText() == null || measures.getText().length() == 0) {
            errorMessage += "Нет доступных мерок!\n";
        }
        if (orderInfo.getText() == null || orderInfo.getText().length() == 0) {
            errorMessage += "Нет доступного описания заказа!\n";
        }
        if (telephone.getText() == null || telephone.getText().length() == 0 || !telephone.getText().matches("\\d+") || telephone.getText().length() > i) {
            errorMessage += "Нет доступного способа связи,лиюо номер указан неверно!\n";
        }
        if (cost.getText() == null || cost.getText().length() == 0) {
            errorMessage += "Не указана стоимость!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(orderEditStage);
            alert.setTitle("Ошибка ");
            alert.setHeaderText("Пожалуйста, внесите корректную информацию о заказе");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

    }

//если нажато ок
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
//если нажато отмена
    @FXML
    private void handleCancel() {
        orderEditStage.close();
    }

//метод для ограничения ввода  в поле стоимости
    public void costTyped(javafx.scene.input.KeyEvent keyEvent) {
        final int maxCharacter = 20;

        String key = keyEvent.getCharacter();

        if ((!key.isEmpty() && (!Character.isDigit(key.charAt(0)) && !key.equals(".")) || (cost.getText().length() >= maxCharacter))) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.beep();
            keyEvent.consume();
        }
    }


    //метод для ввода в  поле телефона  только цифр и звуковой сигнал при наборе неразрешенных
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
        list.add("Оплачено");
        list.add("Выполнено");
        list.add("Выдано клиенту");
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


