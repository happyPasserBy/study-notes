package Arithmetic;

/**
* 循环i次，每次保证i之前有序
*
 */
public class InsertSort {
    public static int[] sort(int[] arr){
        for (int i = 1; i < arr.length; i++){
            for (int j = i; j > 0; j--) {
                if(arr[j] < arr[j-1]){
                    int current = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = current;
                }
            }
        }
        return arr;
    }
    public static void main(String[] args) {
        int[] arr = InsertSort.sort(new int[]{56,24,12,33,57,15,8,1,2,5,0,8,6,4});
        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
