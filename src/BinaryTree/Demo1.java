package BinaryTree;

public class Demo1 {
    public static void main(String[] args) {
        int[] arr = {10,4,15,6,12,3};
        Tree t = new Tree();
        for (int i = 0; i <  arr.length; i++) {
            t.insert(new Node(arr[i]));
        }
        System.out.println("中序遍历-------------");
        t.middleFind(t.getRoot());
        System.out.println("先序遍历-------------");
        t.beforeFind(t.getRoot());
        System.out.println("后序遍历-------------");
        t.backFind(t.getRoot());
        System.out.println("查找-----------------");
        System.out.println(t.find(12));
        System.out.println("最小值---------------");
        System.out.println(t.findMin(new Node(10)));
    }
}
