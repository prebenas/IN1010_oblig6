/**
Klasse som representerer en melding med kryptert tekst som sendes fra en
telegrafist til Monitor.
*/
public class Melding{
  private String tekst;
  private int sekvensNr, kanalNr;

  public Melding(String tekst, int sekvensNr, int kanalNr){
    this.tekst = tekst;
    this.sekvensNr = sekvensNr;
    this.kanalNr = kanalNr;
  }

  public String hentTekst(){ return tekst; }

  public void settTekst(String tekst){ this.tekst = tekst; }

  public int hentSekvensNr(){ return sekvensNr; }

  public int hentKanalNr(){ return kanalNr; }
}
