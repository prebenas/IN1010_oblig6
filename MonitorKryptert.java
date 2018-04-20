/**
Instanser av denne klassen representerer et buffer-lager for midlertidig oppbevaring
av krypterte meldinger. Meldingene sendes inn av telegrafistene via kall på
sendInnMelding og hentes ut av kryptografene via kall på metoden hentUtMelding.
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;


class MonitorKryptert{

  //ArrayList for oppbevaring av Meldinger:
  ArrayList<Melding> meldinger;

  Lock listeEndringsLaas;
  Condition listeIkkeTom;

  public MonitorKryptert(){
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
