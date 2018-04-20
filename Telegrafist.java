/**
  Klasse som representer en telegrafist som lytter etter krypterte meldinger og
  videresender dem som instanser av Melding. Klassen implementer Runnable grense-
  snittet slik at den kan kjøres i en tråd.
*/

public class Telegrafist implements Runnable{
  Kanal kanal;
  MonitorKryptert monitor;
  int kanalId, sekvensNr;


  /**
    Konstruktør som tar ett parameter:
    @param Kanal kanal  Kanalen telegrafisten skal lytte til.
  */
  public Telegrafist(Kanal kanal, MonitorKryptert monitor){
    this.kanal = kanal;
    this.monitor = monitor;
    sekvensNr = 0;
    kanalId = kanal.hentId();
  }

  // Implementering av run metoden som lytter til kanal og sender melding med
  // kryptert tekst til monitor:
  @Override
  public void run(){
    try{
      String kryptertString;
      do{
        kryptertString = kanal.lytt();
        if(kryptertString != null){
          Melding nyMelding = new Melding(kryptertString, sekvensNr++, kanalId);
          monitor.sendInnMelding(nyMelding);
         }
         else{
           Melding nyMelding = new Melding("SLUTT", sekvensNr++, kanalId);
           monitor.sendInnMelding(nyMelding);
         }
        Thread.sleep(1);
      } while( kryptertString != null );
      System.out.println("Avslutter telegrafist på kanal:" + kanalId);
    }
    catch(InterruptedException e){}

  }

}
