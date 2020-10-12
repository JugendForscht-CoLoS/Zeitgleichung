

import java.util.Arrays;

/**
 * Einfaches Programm zur Fourier-Analyse
 * kein Fast-Fourier-Algorithmus, Laufzeit hier uninteressant, wichtiger einfaches Verständnis
 * @author Christof Jost
 * @version 2019/12/12
 *
 */
public class Fourier {
  
  /**
   * der Eingabevektor, d.h. n Datenpunkte: f(0), f(T/n), f(2T/n ...f((n-1)T/n)
   */
  public static double[] eingabe;
  //static double[] eingabe = {0, 1, 0, -1};
  /**
   * die Fourierkoeffizienten an; a0 ist der konstante Anteil
   */
  static double[] cosinusse;
  
  /**
   * die Fourierkoeffizienten bn; b0 ist immer 0
   */
  static double[] sinusse;
  
  /**
   * berechnet die an
   */
  public static void koeffizientenBerechnen() {
    int N = eingabe.length;
    cosinusse = new double[N/2 +1];
    sinusse = new double[N/2 +1];
    
    for (int k = 0; k < N/2 + 1; k++) {
      double an = 0;
      double bn = 0;
      for(int j = 0; j < N; j++) {
        an += eingabe[j]*Math.cos(j*k*2*Math.PI/N);
        bn += eingabe[j]*Math.sin(j*k*2*Math.PI/N);
      }
      cosinusse[k] = an*2/N;
      sinusse[k] = bn*2/N;
    }
  }
  
  public static void ausgeben() {
    System.out.println(Arrays.toString(cosinusse));
    System.out.println(Arrays.toString(sinusse));
    System.out.println();
    System.out.println(cosinusse[0]/2 +" + "); //das ist der konstante Anteil
    for(int k = 1; k < cosinusse.length -1; k++) { //hier kommen die Cosinusse
      System.out.print(cosinusse[k]+"*Math.cos(" +k +"*w*t) +" );
    }
    if(eingabe.length % 2 == 1) {
      System.out.println(cosinusse[cosinusse.length - 1]+"*Math.cos(" + (cosinusse.length - 1) +"*w*t)" );
    }
    else {
      System.out.println(0.5*cosinusse[cosinusse.length - 1]+"*Math.cos(" + (cosinusse.length - 1) +"*w*t)" );
    }
    //das waren cos, jetzt kommen sin
    for(int k = 1; k < sinusse.length - 2; k++) { //Beginn bei 1, weil in 0 immer 0 steht (es gibt keinen 2. konstanten Anteil!)
      System.out.print(sinusse[k]+"*Math.sin(" +k +"*w*t) +" );
    }
    if(eingabe.length % 2 == 1) {
      System.out.print(sinusse[sinusse.length - 2]+"*Math.sin(" +(sinusse.length - 2) +"*w*t) + " );
      System.out.print(sinusse[sinusse.length - 1]+"*Math.sin(" +(sinusse.length - 1) +"*w*t)" );
    }
    else {
      System.out.print(sinusse[sinusse.length - 2]+"*Math.sin(" +(sinusse.length - 2) +"*w*t)" );
    }
    
    
    
  }

  public static void main(String[] args) {
    koeffizientenBerechnen();
    ausgeben();
  }
}
