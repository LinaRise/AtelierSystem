package sample;
import java.io.*;

/*
  Alina Galeeva
  05.11.2018
 */



public class User {
    private String login;
    private String password;


    //создаем констркутор
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

//проервка €вл€етс€ ли админстратором
public boolean isAdmin() throws IOException { //проверка €в ли введ пароль и логин админовскими
    File file=new File("logPass.txt");
    FileReader fileReader=null;
    boolean flag=false; //параметр дл€ вы€снени€ €вл€етс€ ли пользователеь администратором
    try {
        fileReader=new FileReader(file);
    } catch (FileNotFoundException e) {
        System.out.println("‘айл logPass.txt не найден при вызове метода isAdmin в User ");
        e.printStackTrace();
    }
    BufferedReader bufferedReader=new BufferedReader(fileReader);
    String line=null;
    try {
        line=bufferedReader.readLine();
    } catch (IOException e) {
        System.out.println("‘айл logPass.txt пуст при вызове метода isAdmin в User");
        e.printStackTrace();
    }
    while (line != null) {
        String[] arrSplit = line.split("\\|",3);
        if (login.equals(arrSplit[0])&& password.equals(arrSplit[1])&& arrSplit[2].trim().equals("admin")){//если введенные значени€ равны файловому,то это админ
            flag=true;
            break;
        }
        try {
            line=bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("‘айл logPass.txt пуст при вызове метода isAdmin в User");
            e.printStackTrace();
        }
    }bufferedReader.close();
    fileReader.close();
    return flag;//возвращаем админ или нет
}

    //метод провер€ющий есть ли введнный логин и пароль в файле(зрегистрирован ли он),
    // возвращает true или false
    public boolean checkData() {
        boolean flag = false;//присваиваем зачение переменной flag= false
        try {
            File file = new File("logPass.txt");//создаем переменную типа File и передаем ему путь к файлу
            FileReader reader = new FileReader(file);//открываем поток чтени€
            BufferedReader bread = new BufferedReader(reader);// открываем поток чтени€ текста из входного потока путем буферизации символов
            String line = bread.readLine();//присваиваем строке 1 прочитаанную строку файла
            while (line != null) {//цикл: пока есть строки в файле
                String[] arrSplit = line.split("\\|");//раздел€ем строку по символу "|" и записываем значени€ в массив
                if (arrSplit[0].equals(login)) {           //проверка на соответствие введенного логина и логина в строке файла
                    if (arrSplit[1].equals(password)) {//проверка на сосответвие введенного парол€ и парол€ в строке файла
                        flag = true;//присваиваем переменной flag=true, если совпали  логин и пароль
                        break;  //прерываем цикл
                    }
                }
                line = bread.readLine();//если цикл не был прерван, считываем следущую строку файла

            }bread.close();//закрываем поток чтени€
            reader.close();//закрываем поток чтени€
        } catch (IOException e) { //если произошла ошибка
            System.out.println("‘айл не найден "+e);//выводим сообщение
            e.printStackTrace();
        }
        return flag;//возврщаем значение flag(true/false) в зависисимости от совпадеи€ введенного логина и парол€ с тем, что есть в файле
    }


    //метод проверки введнного логина при рестрации, возвращает true или false
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
            System.out.println("‘айл logPass.txt не найден "+e);
            e.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }

//метод регистрации
    public void registrate() throws IOException {
        String path="logPass.txt";
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append("\r\n");
            String text = login + "|" + password+"|worker";
            writer.write(text);
            writer.flush();//чтобы данные пошли в файл сразу
        }

    }

}
