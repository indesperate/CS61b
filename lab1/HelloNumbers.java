public class HelloNumbers{
    public static void main(String[] args){
        int x = 0;
        int sum = 0;
        while (x < 10){
            sum = sum + x;
            x = x + 1;
            System.out.print(sum + " ");
        }
    }
}
