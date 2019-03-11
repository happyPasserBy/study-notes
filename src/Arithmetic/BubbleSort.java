package Arithmetic;

/*
*冒泡的特性
*   1. 相邻的两个元素比较(两两比较)
*   2. 每次循环总是把最大的元素筛选出来并移动到最后
*   3. 时间复杂度O(n*n)
* */
public class BubbleSort {
    public static int[] sort(int[] list){
        for(int i=0;i<list.length;i++){
            for( int j=0;j<list.length;j++){
                if(j == list.length -1){
                    continue;
                }
                if(list[j] > list[j+1]){
                    int z = list[j+1];
                    list[j+1] = list[j];
                    list[j] = z;
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[] arr =  BubbleSort.sort(new int[]{56,24,12,33,57,15,8,1,2,5,0,8,6,4});
        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
