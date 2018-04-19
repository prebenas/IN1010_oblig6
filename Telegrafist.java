/**
  Klasse som representer en telegrafist som lytter etter krypterte meldinger og
  videresender dem som instanser av Melding. Instanser av klassen er "thread-safe"
*/

public class Telegrafist{
  Kanal kanal;

  /**
    Konstruktør som tar ett parameter:
    @param Kanal kanal  Kanalen telegrafisten skal lytte til.
  */
  public Telegrafist(Kanal kanal){
    this.kanal = kanal;
  }

  public void sendMelding(){
    String kryptertString;
    do{
      kryptertString = kanal.lytt();
      if(kryptertString != null){ System.out.println(kryptertString); }

    } while( kryptertString != null );
  }


}
