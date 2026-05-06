package laba5.shared.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Поле не может быть null
    private Long y; //Значение поля должно быть больше -728, Поле не может быть null
    public Coordinates(Long x, Long y){
        this.x=x;
        this.y=y;
    }

    /**
     * Правильное строковое представление
     */
    @Override
    public String toString(){
        return  "("+x  +", "+ y +")";
    }

    /**
     * @return проверка валидации
     */
    public String validate(){
        if(x==null) return "x не может быть пустым";
        if (y<=-728 || y==null) return "y должен быть больше -728 и пустым";
        return "validate";
    }

    public String getX() {
        return x.toString();
    }
    public String getY() {
        return y.toString();
    }
}
