package Arithmetic;


public class ChooseSort {
    public static int[] sort(int[] arr){
        for (int i = 0;i<arr.length;i++){
            int min = i;
            for(int j = i+1; j<arr.length;j++){
                if(arr[min] > arr[j]){
                    min = j;
                }
            }
            if( min != i ) {
                int num = arr[min];
                arr[min] = arr[i];
                arr[i] = num;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = ChooseSort.sort(new int[]{56,24,12,33,57,15,8,1,2,5,0,8,6,4});
        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
