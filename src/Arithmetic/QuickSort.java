package Arithmetic;

public class QuickSort {
    public static void main(String[] args) {
        // int[] arr = {1, 4, 5, 67, 2, 7, 8, 6, 9, 44,123};
        int[] arr = {3,1,5};
        sort(arr, 0, arr.length-1);
        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }
    }
    /**
     * 快速排序
     *
     * @param arr
     * @param L   指向数组第一个元素
     * @param R   指向数组最后一个元素
     */
    public static void sort(int[] arr, int L, int R) {
        int i = L;
        int j = R;
        int pivot = arr[(L + R)/2];
        while(i<=j){
            while(pivot > arr[i]) i++;
            while(pivot < arr[j]) j--;
            if(i<=j){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if(j>L)sort(arr,L,j);
        if(i<R)sort(arr,i,R);
    }
}
