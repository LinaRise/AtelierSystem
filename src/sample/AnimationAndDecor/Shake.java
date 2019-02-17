package sample.AnimationAndDecor;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class Shake {
    private TranslateTransition tt;

    public Shake(Node node){//метод принимает на вход любой элемент
        //переменной типа TranslateTransition присваиваем продолжительнось, и элемент, к чему применять
        tt=new TranslateTransition(Duration.millis(70),node);
        //откуда начать
        tt.setFromX(0f);
        //на сколько отклонить по оси X
        tt.setByX(10f);
        //количество повторов
        tt.setCycleCount(4);
        //вернуть на то же место по окончании анимации
        tt.setAutoReverse(true);
    }
    //метода проигрывания анимации
    public void playAnim(){
        tt.playFromStart();
    }
}





