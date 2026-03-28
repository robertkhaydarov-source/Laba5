package laba5.model;

public class Coordinates {
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
    public boolean validate(){
        if(x==null) return false;
        if (y<=-728 || y==null) return false;
        return true;
    }

    public String getX() {
        return x.toString();
    }
    public String getY() {
        return y.toString();
    }
}
