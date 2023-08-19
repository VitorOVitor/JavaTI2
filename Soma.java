import java.util.Scanner;

public class Soma{
    public static void main(String[] args){
        Scanner teclado= new Scanner(System.in);
        System.out.println("Por favor, digite um numero");
        int num1=teclado.nextInt();
        teclado.nextLine();
       System.out.println("Por favor, digite o segundo numero");
        int num2=teclado.nextInt();
        teclado.nextLine();
        Somar(num1,num2);
        teclado.close();
    }

  public static void Somar(int num1, int num2){
    int soma=0;
    soma=num1+num2;
    System.out.println("O valor da soma sera de "+soma);
  }

}
