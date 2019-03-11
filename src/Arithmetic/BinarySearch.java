package Arithmetic;

public class BinarySearch {
    public static int search(int[] arr,int num){
        int L = 0;
        int R = arr.length-1;
        while(L<=R){
            int middle = (L + R)/2;
            if(arr[middle] > num){
                R = middle - 1;
            }else if(arr[middle] < num){
                L = middle + 1;
            }else if(arr[middle] == num){
                return middle;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] arr = {1,4,6,8,23,45,76,89,123,345,678,889};
        System.out.println(BinarySearch.search(arr,89));
    }
}
