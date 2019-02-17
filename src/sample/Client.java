package sample;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class Client {
    private String idOfClient;
    private String fioOfClienet;


    public void writingNewClient(String idOfClient,String fioOfClienet){
        try {
            FileWriter writer=new FileWriter("clients.txt",true);
            writer.write(idOfClient+"/"+fioOfClienet);
            writer.write("\r\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //подсчет номера заказа
    static public int clientId() throws IOException {
        String line;
        String[] arrSplit = {null,null};

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("clients.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while ((line = Objects.requireNonNull(reader).readLine()) != null) {
            arrSplit = line.split("/", 2);
        }
        System.out.println(Arrays.toString(arrSplit));
        int id=1;
        try {
            if(arrSplit[0]!= null) {
                id = Integer.parseInt(arrSplit[0])+1;
            }
        }
        catch (NumberFormatException e)
        {
            id=1;
        }return id;
    }

    //получение id клиента по его фамилии
    public static String getClientId(String fio){//на вход метод получает фамилию
        try {
            // открываем поток чтения текста из входного потока путем буферизации символов
            BufferedReader reader=new BufferedReader(new FileReader("clients.txt"));
            String line;
            while((line=reader.readLine())!=null){//пока есть строки в файле
                //разделяем строку по символу "/" и записываем в массив
                String[] arrSplit=line.split(Pattern.quote("/"));
                if (fio.trim().equals(arrSplit[1].trim())){//если фамилия соответвует фамилии в файле
                    return arrSplit[0].trim();//возвращаем id клиента
                }
            }
        } catch (IOException e) {//ловим ошибку
            e.printStackTrace();
        }return "";//если не найдено возвращаем пустую строку

    }

    public static String getClientFio(String id){
        String fio=null;
        try {
            BufferedReader reader=new BufferedReader(new FileReader("clients.txt"));
            String line;
            while((line=reader.readLine())!=null){
                String[] arrSplit=line.split(Pattern.quote("/"));
                if (id.equals(arrSplit[0])){
                    fio= arrSplit[1];
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }return fio;
    }



    public static boolean fileContainsWord(String word) throws IOException {
        File file=new File("clients.txt");
        BufferedReader reader=new BufferedReader(new FileReader(file));
        String line;
        while ((line=reader.readLine())!=null){
            String[] arrSplit=line.split(Pattern.quote("/"),2);
            if (word.trim().equals(arrSplit[1].trim())){
                return true;
            }

        }return false;
    }


}
