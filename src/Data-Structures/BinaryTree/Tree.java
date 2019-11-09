package BinaryTree;

public class Tree {
    private Node root;
    public boolean insert(Node node){
        if(root == null){
            root = node;
        }
        Node current = root;
        while(true){
            if(node.getData() > current.getData()){
                if(current.getRight() == null){
                    current.setRight(node);
                }else{
                    current = current.getRight();
                }
            }else if(node.getData() < current.getData()){
                if(current.getLeft() == null){
                    current.setLeft(node);
                }else{
                    current = current.getLeft();
                }
            }else if(node.getData() == current.getData()){
                return false;
            }
        }
    }
    public Node getRoot(){
        return root;
    }
    /**
     *中序遍历
     */
    public void middleFind(Node node){
        if(node.getLeft() != null){
            middleFind(node.getLeft());
        }
        System.out.println(node.getData());
        if(node.getRight() != null){
            middleFind(node.getRight());
        }
    }
    /**
     * 先序遍历
     */
    public void beforeFind(Node node){
        System.out.println(node.getData());
        if(node.getLeft() != null){
            beforeFind(node.getLeft());
        }
        if(node.getRight() != null){
            beforeFind(node.getRight());
        }
    }
    /**
     * 后序遍历
     */
    public void backFind(Node node){
        if(node.getLeft() != null){
            backFind(node.getLeft());
        }
        if(node.getRight() != null){
            backFind(node.getRight());
        }
        System.out.println(node.getData());
    }
    public Node find(int num){
        Node current = this.root;
        while(true){
            if(current == null){
                return null;
            }else if(num == current.getData()){
                return current;
            }else if(num > current.getData()){
                current = current.getRight();
            }else{
                current = current.getLeft();
            }
        }
    }
    public Node findMin(Node node){
        Node current = this.root;
        while(true){
            if(current == null){
                return node;
            }else if(node.getData() == current.getData()){
                return Leftfind(current);
            }else if(node.getData() > current.getData()){
                current = current.getRight();
            }else{
                current = current.getLeft();
            }
        }
    }
    public Node Leftfind(Node node){
        if(node.getLeft() == null){
            return node;
        }else{
            return Leftfind(node.getLeft());
        }
    }
    public Node deleteNode(Node node){

        this.delete(this.root,node);
        return null;
    }
    private Node delete(Node node, Node deleteNode){
        if(node == null){
            return null;
        }else if(deleteNode.getData() == node.getData()){
            return node;
        }else if(deleteNode.getData() < node.getData()){
            Node subNode = delete(deleteNode.getLeft(),deleteNode);
            if(subNode != null){
                // 未完待续
            }
        }else if(deleteNode.getData() > node.getData()){
            Node subNode = delete(deleteNode.getRight(),deleteNode);
        }
        return null;
    }
    private Node remove(Node node){
        if(node.getLeft() == null && node.getRight() == null){
            return node;
        }else if(node.getLeft() != null && node.getRight() == null){
            return node.getLeft();
        }else if(node.getRight() !=null && node.getLeft() == null){
            return node;
        }else{
            return findMin(node);
        }
    }
}
