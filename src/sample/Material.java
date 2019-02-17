package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Material {
private String material;
private String color;
private String amount;
private String extra;
    private String materilOne;
    private String colorOne;
    private String amountOne;

    public Material(String material, String color, String amount, String extra) {
        this.material = material;
        this.color = color;
        this.amount = amount;
        this.extra = extra;
    }

    public Material() {

    }

    public Material(String materilOne, String colorOne, String amountOne) {
        this.materilOne = materilOne;
        this.colorOne = colorOne;
        this.amountOne = amountOne;
    }

    public String getMaterilOne() {
        return materilOne;
    }

    public void setMaterilOne(String materilOne) {
        this.materilOne = materilOne;
    }

    public String getColorOne() {
        return colorOne;
    }

    public void setColorOne(String colorOne) {
        this.colorOne = colorOne;
    }

    public String getAmountOne() {
        return amountOne;
    }

    public void setAmountOne(String amountOne) {
        this.amountOne = amountOne;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }



    ArrayList<String> getMaterialsFile(){
        ArrayList<String> list=new ArrayList<>();
        File file = new File("material.txt");
        FileReader reader;
        try {
            reader = new FileReader(file);

        BufferedReader bread = new BufferedReader(reader);
        String line;

        line = bread.readLine();
        while (line!=null){
            list.add(line);
            line=bread.readLine();
        }
        } catch (IOException e) {
            System.out.println("���� material.txt �� ������ ��� ������ ������ matTableFillIn " + e);
            e.printStackTrace();
        }return list;
    }


    @FXML//���������� ����������� ����� ����������
    static void rewriteMaterialCatalog(ObservableList<Material> materialCatalog) throws IOException {
        File file = new File("material.txt");
        FileWriter writer = new FileWriter(file, false);
        for (Material material : materialCatalog) {
            System.out.println(material.getMaterial() + "/" + material.getColor() + "/" + material.getAmount() + "/" + material.getExtra());
            writer.write(material.getMaterial() + "/" + material.getColor() + "/" + material.getAmount() + "/" + material.getExtra());
            writer.write("\r\n");
            writer.flush();


        }writer.close();
    }

    static void rewriteMaterialCatalog(ArrayList<String> list) throws IOException {
        File file = new File("material.txt");
        FileWriter writer = new FileWriter(file, false);
        for (String line: list) {
            System.out.println(line);
            writer.write(line);
            writer.write("\r\n");
            writer.flush();
        }writer.close();
    }




//������ ����� ������ � ��������� � ����
    static void addMaterial(Material material) throws IOException {
        File file = new File("material.txt");
        //�������� ������ ������� � ����� �����
        FileWriter writer = new FileWriter(file, true);
        //������ ��, ��� ����� �������� � ����
        String text = material.getMaterial() + "/" + material.getColor() + "/" +
                material.getAmount() + "/" + material.getExtra()+"\r\n";
        writer.write(text);//������ � ����
        writer.flush();
        writer.close();//�������� ������ ������
    }

    //����� ��� ������ ����������  � ������ ����� � ������
    ArrayList<String> materialsFileread() throws IOException {
        File file = new File("material.txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("���� material.txt �� ������ ��� ������ ������ materialsFileread " + e);
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
//�������� ���������, ������� ���� ������� ��� ������ ������
    String getMatForNewOrder()  {
        BufferedReader reader;
        StringBuilder text= new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader("matForOrder.txt"));
            String line=reader.readLine();
            line=line.replaceAll("[\\[\\]]","");
            System.out.println("��� ������ "+line);
            String[] arrSplit=line.split(",");

            for (String anArrSplit : arrSplit) {
                text.append("\\").append(anArrSplit);
                // text += "\\" + anArrSplit;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }return text.toString();

    }

    //�������� �� ������������� ����������  ����� � ����������
    static boolean isFileExists(){
        boolean flag;
        String fileName = "matForOrder.txt";
        flag = (new File(fileName)).exists();
        return flag;
    }

//�������� ��������� �� ������������ ��� ����������
    public static boolean isUniqueMaterial(Material material) {
        Material material1 = new Material();
        boolean flag = true;
        ArrayList<String> materialsList = null;
        try {
            materialsList = material1.materialsFileread();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert materialsList != null;
        if (!materialsList.isEmpty()) {
            for (String line : materialsList) {
                System.out.println("������ �����");
                if (line.length() != 0 || !line.equals("")) {
                    String[] arrSplit = line.split(Pattern.quote("/"));
                    if (arrSplit[0].equals(material.getMaterial().trim()) && (arrSplit[1].equals(material.getColor().trim()))) {
                        System.out.println("���� ����������");
                        flag=false;
                        break;

                    }

                }
            }
        }return flag;
    }




}
