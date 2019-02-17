package sample;
import java.io.*;

/*
  Alina Galeeva
  05.11.2018
 */



public class User {
    private String login;
    private String password;


    //������� �����������
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {

    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String gettingLoggedLogin(){
        String loginOfWorker=null;
        try {
            BufferedReader reader=new BufferedReader(new FileReader("temp.txt"));
            String line=reader.readLine();
            String[] arrSplit=line.split("\\|");
            loginOfWorker=arrSplit[0].trim();
            System.out.println(loginOfWorker);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }return loginOfWorker;
    }

    public void writingLogin() {
        FileWriter writer = null;
        try {
            writer = new FileWriter("temp.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (isAdmin()) {
                writer.write(login+"|"+"admin");
                    writer.flush();
                    writer.close();
            }
            else if(!isAdmin()){
                writer.write(login+"|"+"worker");
                writer.flush();
                writer.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLoggedAdmin(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("temp.txt"));
            String line=reader.readLine();
            String[] arrSplit=line.split("\\|",2);
        if (arrSplit[1].trim().equals("admin")){
            return true;
        }
        } catch (IOException e) {
            e.printStackTrace();
        }return false;
    }

//�������� �������� �� ��������������
public boolean isAdmin() throws IOException { //�������� �� �� ���� ������ � ����� ������������
    File file=new File("logPass.txt");
    FileReader fileReader=null;
    boolean flag=false; //�������� ��� ��������� �������� �� ������������� ���������������
    try {
        fileReader=new FileReader(file);
    } catch (FileNotFoundException e) {
        System.out.println("���� logPass.txt �� ������ ��� ������ ������ isAdmin � User ");
        e.printStackTrace();
    }
    BufferedReader bufferedReader=new BufferedReader(fileReader);
    String line=null;
    try {
        line=bufferedReader.readLine();
    } catch (IOException e) {
        System.out.println("���� logPass.txt ���� ��� ������ ������ isAdmin � User");
        e.printStackTrace();
    }
    while (line != null) {
        String[] arrSplit = line.split("\\|",3);
        if (login.equals(arrSplit[0])&& password.equals(arrSplit[1])&& arrSplit[2].trim().equals("admin")){//���� ��������� �������� ����� ���������,�� ��� �����
            flag=true;
            break;
        }
        try {
            line=bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("���� logPass.txt ���� ��� ������ ������ isAdmin � User");
            e.printStackTrace();
        }
    }bufferedReader.close();
    fileReader.close();
    return flag;//���������� ����� ��� ���
}

    //����� ����������� ���� �� �������� ����� � ������ � �����(�������������� �� ��),
    // ���������� true ��� false
    public boolean checkData() {
        boolean flag = false;//����������� ������� ���������� flag= false
        try {
            File file = new File("logPass.txt");//������� ���������� ���� File � �������� ��� ���� � �����
            FileReader reader = new FileReader(file);//��������� ����� ������
            BufferedReader bread = new BufferedReader(reader);// ��������� ����� ������ ������ �� �������� ������ ����� ����������� ��������
            String line = bread.readLine();//����������� ������ 1 ������������ ������ �����
            while (line != null) {//����: ���� ���� ������ � �����
                String[] arrSplit = line.split("\\|");//��������� ������ �� ������� "|" � ���������� �������� � ������
                if (arrSplit[0].equals(login)) {           //�������� �� ������������ ���������� ������ � ������ � ������ �����
                    if (arrSplit[1].equals(password)) {//�������� �� ����������� ���������� ������ � ������ � ������ �����
                        flag = true;//����������� ���������� flag=true, ���� �������  ����� � ������
                        break;  //��������� ����
                    }
                }
                line = bread.readLine();//���� ���� �� ��� �������, ��������� �������� ������ �����

            }bread.close();//��������� ����� ������
            reader.close();//��������� ����� ������
        } catch (IOException e) { //���� ��������� ������
            System.out.println("���� �� ������ "+e);//������� ���������
            e.printStackTrace();
        }
        return flag;//��������� �������� flag(true/false) � ������������� �� ��������� ���������� ������ � ������ � ���, ��� ���� � �����
    }


    //����� �������� ��������� ������ ��� ���������, ���������� true ��� false
    public boolean checkRegistartionLogin() {
        String path="logPass.txt";
        boolean flag = true;
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bread = new BufferedReader(reader);
            String line = bread.readLine();
            while (line != null) {
                String[] arrSplit = line.split("\\|");
                if (arrSplit[0].equals(login))  flag = false;
                line = bread.readLine();
               // flag = flag & true;
            }

        } catch (IOException e) {
            System.out.println("���� logPass.txt �� ������ "+e);
            e.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }

//����� �����������
    public void registrate() throws IOException {
        String path="logPass.txt";
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append("\r\n");
            String text = login + "|" + password+"|worker";
            writer.write(text);
            writer.flush();//����� ������ ����� � ���� �����
        }

    }

}
