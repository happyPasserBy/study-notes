package BinaryTree;

public class Node {
    private int data;
    private Node Left;
    private Node Right;
    private Node(){}
    public Node(int data){
        this.data = data;
    }
    public int getData() {
        return data;
    }
    public void setData(int data) {
        this.data = data;
    }
    public Node getLeft() {
        return Left;
    }
    public void setLeft(Node left) {
        Left = left;
    }
    public Node getRight() {
        return Right;
    }
    public void setRight(Node right) {
        Right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
