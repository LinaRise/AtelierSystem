package sample.AnimationAndDecor;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AnimationAndDecor {

   //метод для добавления своей иконки в сцену
   //принимает на вход сцену и путь к иконке приложения
    public static void addIcon(Stage stage, String pathToIcon){
        //прикрепляет к сцене иконку приложения
        stage.getIcons().add(new Image(pathToIcon));
    }
}
