public class Kryptograf implements Runnable{

  //Statisk variabel som teller antall uferdige kanaler;
  private static int uferdigeKanaler;

  private MonitorKryptert monitorKryptert;
  private MonitorDekryptert monitorDekryptert;

  /**
  Konstruktør som tar to parametre:
  @param MonitorKryptert monitorKryptert   Monitoren som lagrer krypterte meldinger
  @param MonitorDekryptert monitorDekryptert  Monitoren som lagrer dekrypterte meldinger
  */
  public Kryptograf(MonitorKryptert monitorKryptert, MonitorDekryptert monitorDekryptert, int antallKanaler){
    this.monitorKryptert = monitorKryptert;
    this.monitorDekryptert = monitorDekryptert;
    uferdigeKanaler = antallKanaler;
  }

  //Implementasjon av run metoden. En Melding-instans hentes fra monitorKryptert,
  //teksten i meldingen oversettes og legges tilbake i meldingen.
  //Deretter sendes meldingen til monitorDekryptert:
  @Override
  public void run(){
    try{
      String kryptertStreng;
      String klartekstStreng;
      do{
        Melding melding = monitorKryptert.hentUtMelding();
        kryptertStreng = melding.hentTekst();
        if(!kryptertStreng.equals("SLUTT")){
          klartekstStreng = Kryptografi.dekrypter(kryptertStreng);
          melding.settTekst(klartekstStreng);
          monitorDekryptert.sendInnMelding(melding);
        }
        else{
          uferdigeKanaler--;
          System.out.println("***UFERDIGE KANALER: " + uferdigeKanaler);
          if(uferdigeKanaler == 0){
            melding.settTekst("INGEN FLERE MELDINGER");
            monitorDekryptert.sendInnMelding(melding);
          }
        }
        Thread.sleep(1);
      } while(uferdigeKanaler > 0);
    }
    catch(InterruptedException e){}

  }
}
