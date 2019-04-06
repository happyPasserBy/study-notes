public class Test{
    public static void main(String[] args) {
        Animal cat = new Cat();
        car.shout();
    }
}
abstract Animal {
    abstract void shout();
}
class Cat implements Animal{
 public void shout(){
     System.out.println("喵！");
 }
}