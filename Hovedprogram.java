import java.util.Scanner;


class Hovedprogram{
  public static void main(String[] args){

    System.out.print("Oppgi antall telegrafister: ");
    int antallTelegrafister = lesInt();

    Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
    Kanal[] kanaler = ops.hentKanalArray();

    Telegrafist t = new Telegrafist(kanaler[0]);
    t.sendMelding();



  }

  private static int lesInt(){
    Scanner inn = new Scanner(System.in);
    boolean ferdig = false;
    int tall = 0;
    while(!ferdig){
      try{
        tall = inn.nextInt();
        ferdig = true;
      }
      catch(Exception e){
        System.out.println("FEIL VED INNTASTING, PRØV IGJEN: ");
      }
    }
    return tall;
  }
}
