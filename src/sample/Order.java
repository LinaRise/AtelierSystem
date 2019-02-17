package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Order {
    private int  orderNum;
    private int clientId;
    private String clientFio;
    private String telephone;
    private String order;
    private String measures;
    private String state;
    private String cost;
    private String loginOfWorker;
    private String matForOrder;
    private String oneMaterial;
    private String materilOne;
    private String colorOne;
    private String amountOne;



    public Order(int orderNum, String clientFio, String telephone, String order, String measures, String state, String cost, String loginOfWorker, String matForOrder) {
        this.orderNum = orderNum;
        this.clientFio = clientFio;
        this.telephone = telephone;
        this.order = order;
        this.measures = measures;
        this.state = state;
        this.cost = cost;
        this.loginOfWorker = loginOfWorker;
        this.matForOrder = matForOrder;
    }

    public Order(int orderNum, int clientId, String telephone, String order, String measures,
                 String state, String cost, String loginOfWorker, String matForOrder) {
        this.orderNum = orderNum;
        this.clientId = clientId;
        this.telephone = telephone;
        this.order = order;
        this.measures = measures;
        this.state = state;
        this.cost = cost;
        this.loginOfWorker = loginOfWorker;
        this.matForOrder = matForOrder;
    }

   /* public Order(String measures, String telephone) {
        this.telephone = telephone;
        this.measures = measures;
    }

    public Order(String oneMaterial) {
        this.oneMaterial=oneMaterial;

    }*/

    public String getClientFio() {
        return clientFio;
    }

    public void setClientFio(String clientFio) {
        this.clientFio = clientFio;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Order() {
    }

    public int getClientId() {
        return clientId;
    }



    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLoginOfWorker() {
        return loginOfWorker;
    }

    public void setLoginOfWorker(String loginOfWorker) {
        this.loginOfWorker = loginOfWorker;
    }

    public void setMatForOrder(String matForOrder) {
        this.matForOrder = matForOrder;
    }

    public String getMatForOrder() {
        return matForOrder;
    }

    //����� ��� ������ ����� ������� � ���� orders.txt
    void  addNewOrder()  {
        File file=new File("orders.txt");//������� ������ File
        try {
            //�������� ���� ���� ���������� ����������, true- �������� ���������� ����� ����
            FileWriter    writer =new FileWriter(file,true);
            //�������� ���������� � ������ ��� ������
            writer.write(orderNum + "|" + clientId + "|" + telephone + "|" +
                    order + "|" + measures + "|" + state + "|" + cost + "|" + loginOfWorker + "|" +
                    matForOrder + "\r\n");
            writer.flush();//���������� ����� ��� ����, ����� ������ � ���� ��������� �����
            writer.close();//��������� ����� ������
        }
        catch (IOException e) {//����� ��������� ������
            //������� ��������� �� ������
            System.out.println("���� Orders.txt �� ������ ��� ������ ������ addNewOrder "+e);
            e.printStackTrace();
        }
         Alert alert = new Alert(Alert.AlertType.INFORMATION);//������� ������ Alert
         alert.setTitle("���������");//����������� ���������
         alert.setHeaderText("������ ���������");//����������� ������� ����
         alert.setContentText("������ ������ ������� ���������");//����������� �������� �����
        alert.showAndWait();//������� ��������� �� �����
    }

//������� ������ ������
   static public int orderNum() throws IOException {
        String line ;
        String[] arrSplit = {null,null};

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("orders.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       assert reader != null;
       while ((line = reader.readLine()) != null) {
                arrSplit = line.split("\\|", 2);
            }
        System.out.println(Arrays.toString(arrSplit));
            int number=1;
       try {
           if(arrSplit[0]!= null) {
                number = Integer.parseInt(arrSplit[0])+1;

           }
       }
        catch (NumberFormatException e)
       {
         number=1;
       }return number;
    }


    //����� ��� ������ ����� ������� � ������ ����� � ������
    ArrayList<String> ordersFileread() throws IOException {
        File file = new File("orders.txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("���� Orders.txt �� ������ ��� ������ ������ orderTableFillIn " + e);
            e.printStackTrace();
        }
        BufferedReader bread = new BufferedReader(reader);
        String line ;
        ArrayList<String> lineArray = new ArrayList();
        while ((line = bread.readLine()) != null) {
            lineArray.add(line);
        }reader.close();
        bread.close();
        return lineArray;
    }








//���������� � �������� ����� ����� ��������� �������

     static void rewriteOrderCatalog(ObservableList<Order> orderCatalog,ArrayList<String> ordersArr)  {
        System.out.println("size"+ordersArr.size());
        for (Order order:orderCatalog){
            for(int i=0;i<ordersArr.size();i++) {
                String line = ordersArr.get(i);
                if (line.length() != 0 ) {
                    String[] arrSplit = line.split(Pattern.quote("|"));
                    if (Integer.parseInt(arrSplit[0].trim())==(order.getOrderNum())) {
                        ordersArr.set(i, order.getOrderNum() + "|" + Client.getClientId(order.getClientFio()) + "|" + order.getTelephone() + "|"
                                + order.getOrder() + "|" + order.getMeasures() + "|" + order.getState() + "|" + order.getCost()+"|"+order.getLoginOfWorker()+"|"+order.getMatForOrder());
                    }
                }
            }
        }System.out.println("size"+ordersArr.size());
         for (String anOrdersArr : ordersArr) {
             System.out.println(anOrdersArr);
         }
        Order order1=new Order();
        order1.writeToOrderFile(ordersArr);
    }


    @FXML//���������� ����������� �����
    public static void rewriteOrderCatalog(ObservableList<Order> catalogTable) throws IOException {
        String path ="orders.txt";
        File file = new File(path);

        FileWriter writer = new FileWriter(file, false);
        for (Order order : catalogTable) {
            System.out.println(order.getOrderNum() + "|" + Client.getClientId(order.getClientFio()) + "|" + order.getTelephone() + "|"
                    + order.getOrder() + "|" + order.getMeasures() + "|" + order.getState() + "|" + order.getCost()+"|"+order.getLoginOfWorker()+"|"+order.getMatForOrder());
            writer.write(order.getOrderNum() + "|" + Client.getClientId(order.getClientFio()) + "|" + order.getTelephone() + "|"
                    + order.getOrder() + "|" + order.getMeasures() + "|" + order.getState() + "|" + order.getCost()+"|"+order.getLoginOfWorker()+"|"+order.getMatForOrder());
            writer.flush();
            writer.write("\r\n");

        }writer.close();
    }

//����� ��� �������� ���������� ������ ������
    ArrayList<String> saveNewOrder(ArrayList<String> list,String[] arrSplit){
        for (int i=0;i<arrSplit.length;i++){
            String[] matSplit = arrSplit[i].split(Pattern.quote("$").trim());//�� ������ ������
            System.out.println("�� ������"+ Arrays.toString(arrSplit));
            for(int k=0;k<list.size();k++) {
                String[] orderMat = list.get(k).split(Pattern.quote("/").trim()); //���������
                System.out.println("�� ������"+ Arrays.toString(orderMat));
                if (matSplit[0].equals(orderMat[0]) && (matSplit[1].equals(orderMat[1]))){
                    System.out.println(Arrays.toString(orderMat));
                    System.out.println(Arrays.toString(matSplit));
                    System.out.println(orderMat[2]);
                    System.out.println(matSplit[2]);
                    System.out.println(Double.parseDouble(orderMat[2])+" 1 �����");
                    System.out.println(Double.parseDouble(matSplit[2])+" 2 �����");
                    double n=Double.parseDouble(orderMat[2])-Double.parseDouble(matSplit[2]);
                    System.out.println(" �����"+n);
                    orderMat[2]= String.valueOf(n);
                    list.set(k,orderMat[0]+"/"+orderMat[1]+"/"+orderMat[2]+"/"+orderMat[3]);
                    System.out.println("���������"+list.get(i));
                }
            }
        }return list;

    }







//������ � ���� ������� ����� ��������
    public void writeToOrderFile(ArrayList <String> orderArray){
        try {
            FileWriter  writer = new FileWriter("orders.txt");
            for (String anOrderArray : orderArray) {
                writer.write(anOrderArray);
                writer.flush();
                writer.write("\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










        public static void addOrder(Order order)  {
        File file = new File("orders.txt");
        FileWriter writer;
        try {
            writer = new FileWriter(file, true);

            String text = order.getOrderNum() + "|" + order.getClientId() + "|" + order.getTelephone() + "|"
                    + order.getOrder()+"|"+order.getMeasures()+"|"+order.getState()+"|"+order.getCost()+"|"+order.getLoginOfWorker();
            writer.write(text);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }






}
