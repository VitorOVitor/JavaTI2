package Seilaalgumacoisa;
import java.util.Scanner;

public class Soma{
    public static void main(String[] args){
        Scanner teclado= new Scanner(System.in);
         System.out.println("Digite o valor do primeiro numero");
         int num1=teclado.nextInt();
         teclado.nextLine();
         System.out.println("Digite o valor do segundo numero");
         int num2=teclado.nextInt();
         int soma= num1+num2;
         System.out.println("O valor da soma vai ser de "+soma);
         teclado.close();
    }
}