public class Singleton{
    private static volatile Singleton s;
    private Singleton(){}
    public Singleton getInstance(){
        if(s == null){
            this.s = new Singleton();
        }
        return s;
    }
    public static void main(String[] args) {

    }
}