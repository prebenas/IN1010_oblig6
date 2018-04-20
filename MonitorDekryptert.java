/**
Instanser av denne klassen representerer et buffer-lager for midlertidig oppbevaring
av dekrypterte meldinger. Meldingene sendes inn av kryptografene via kall på
sendInnMelding og hentes ut av operasjonsleder via kall på metoden hentUtMelding.
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

class MonitorDekryptert{
  //ArrayList for oppbevaring av Meldinger:
  ArrayList<Melding> meldinger;

  Lock listeEndringsLaas;
  Condition listeIkkeTom;

  public MonitorDekryptert(){
    meldinger = new ArrayList<Melding>();
    listeEndringsLaas = new ReentrantLock();
    listeIkkeTom = listeEndringsLaas.newCondition();
  }

  // Metode for å levere inn Melding instans:
  public void sendInnMelding(Melding melding){
    listeEndringsLaas.lock();
    try{
      meldinger.add(melding);
      //System.out.println(melding.hentTekst());
      listeIkkeTom.signalAll();
    }
    finally{ listeEndringsLaas.unlock(); }
  }

  //Metode for å hente ut Melding instanser:
  public Melding hentUtMelding() throws InterruptedException{
    listeEndringsLaas.lock();
    try{
      while(meldinger.size() == 0){
        listeIkkeTom.await();
      }
      return meldinger.remove(0);
    }
    finally{ listeEndringsLaas.unlock(); }
  }

}
