import java.util.Scanner;


class Hovedprogram{
  public static void main(String[] args){

    System.out.print("\nOppgi antall telegrafister: ");
    int antallTelegrafister = lesInt();
    System.out.println("Oppgi antall kryptografer:");
    int antallKryptografer = lesInt();

    // Oppretter Operasjonssentral og Kanal array:
    Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
    Kanal[] kanaler = ops.hentKanalArray();

    //Oppretter monitorene:
    MonitorKryptert monitorKryptert = new MonitorKryptert();
    MonitorDekryptert monitorDekryptert = new MonitorDekryptert();

    //Oppretter telegrafister og legger dem inn i tråder:
    for(int i = 0; i < antallTelegrafister; i++){
      Telegrafist teleRunnable = new Telegrafist(kanaler[i], monitorKryptert);
      Thread traad = new Thread(teleRunnable);
      traad.start();
    }

    //Oppretter kryptografer og legger dem inn i tråder:
    for(int i = 0; i < antallKryptografer; i++){
      Kryptograf kryptoRunnable = new Kryptograf(monitorKryptert, monitorDekryptert, kanaler.length);
      Thread traad = new Thread(kryptoRunnable);
      traad.start();
    }



  }

  private static int lesInt(){
    Scanner inn = new Scanner(System.in);
    boolean ferdig = false;
    int tall = 0;
    while(!ferdig){
      try{
        tall = inn.nextInt();
        if(tall < 1) tall = 1;
        ferdig = true;
      }
      catch(Exception e){
        System.out.println("FEIL VED INNTASTING, PRØV IGJEN: ");
      }
    }
    return tall;
  }
}
