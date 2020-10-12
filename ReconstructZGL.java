import java.util.ArrayList;

public class ReconstructZGL {

	static public final double E = 2.49957864E9;//lineare Exzentrizität der Erdumlaufbahn
	static final double G = 6.674E-11;//Gravitationskonstante
	static final double mSonne = 1.989E30;//Masse der Sonne
	static final double C = mSonne * G;

	static Matrizen vAlt;
	static Matrizen OrtAlt;
	static Matrizen a;
	static Matrizen OrtNeu;
	static Matrizen vNeu;

	static Matrizen drehmatrix;
	static Matrizen OrtsvektorSonne; //Ortsvektor der Sonne
	static Matrizen OrtsvektorErde;
	static Matrizen VerbindungOSOE;
	static Matrizen kreuzprodukt;
	static double skalarprodukt;
	static double skalarproduktAns;

	static ArrayList<Integer> differenz = new ArrayList<Integer>();

	public static ArrayList<Integer> erdumlaufbahnBerechnen(Matrizen Erdachse){

		double [][] OrtAltarray = {{1.4959802296E11},{0}};
		OrtAlt = new Matrizen(OrtAltarray); //Start-Ort

		double [][] vAltarray = {{0},
				{1.00003041*Math.sqrt(C/OrtAlt.matrix[0][0])*Math.sqrt((1-(E/OrtAlt.matrix[0][0]))/(1+(E/OrtAlt.matrix[0][0])))}};
		vAlt = new Matrizen(vAltarray); //Start-Geschwindigkeit

		double [][] aarray = { { (-1 * G * ( mSonne * ( OrtAlt.matrix[0][0] + E ) ) / Math.pow( ( OrtAlt.matrix[0][0] + E ) * ( OrtAlt.matrix[0][0] + E ) + OrtAlt.matrix[1][0] * OrtAlt.matrix[1][0], 1.5) ) },
				{ ( -1 * G * ( mSonne * OrtAlt.matrix[1][0] ) / Math.pow( ( OrtAlt.matrix[0][0] + E ) * ( OrtAlt.matrix[0][0] + E ) + OrtAlt.matrix[1][0] * OrtAlt.matrix[1][0], 1.5) ) } };
		a = new Matrizen(aarray); //Start-Beschleunigung




		double[][] sonne = { {-1 * E},{0},{0}};

		OrtsvektorSonne = new Matrizen(sonne);

		double[][] erde = new double[3][1];

		OrtsvektorErde = new Matrizen(erde); //Ortsvektor der Erde

		boolean first = true ; 

		double winkel = 2*Math.PI / 86164.1;//Drehwinkel also: 2Pi / siderischer Tag

		drehmatrix = Matrizen.drehmatrix(Erdachse, winkel); //Drehmatrix wird erstellt

		OrtsvektorErde.matrix[2][0] = 0; //Erde befindet sich immer in der x-y-Ebene

		int zeitdifferenz = 0;

		differenz = new ArrayList<Integer>();		

		for (int days = 0; days < 366; days++) {//Simulation startet mit Wert 1 für den ersten Tag, anstatt für den 0., also 366 Tage um das ganze Jahr abzubilden

			for (int sec = 0; sec < 86400; sec++) {//1 Tag = 86400s

				OrtNeu = OrtAlt.addieren(vAlt.multiplizieren(1)); //Neuer Ort wird berechnet

				vNeu = vAlt.addieren(a.multiplizieren(1)); //Neue Geschwindigkeit wird berechnet

				a.matrix[0][0] = -1 * G * ( mSonne * ( OrtNeu.matrix[0][0] + E ) ) / Math.pow( ( OrtNeu.matrix[0][0] + E ) * ( OrtNeu.matrix[0][0] + E ) + OrtNeu.matrix[1][0] * OrtNeu.matrix[1][0], 1.5);
				a.matrix[1][0] = -1 * G * ( mSonne * OrtNeu.matrix[1][0] ) / Math.pow( ( OrtNeu.matrix[0][0] + E ) * ( OrtNeu.matrix[0][0] + E ) + OrtNeu.matrix[1][0] * OrtNeu.matrix[1][0], 1.5);
				//Neue Beschleunigung wird berechnet

				OrtAlt.matrix[0][0] = OrtNeu.matrix[0][0];
				OrtAlt.matrix[1][0] = OrtNeu.matrix[1][0];
				vAlt.matrix[0][0] = vNeu.matrix[0][0];
				vAlt.matrix[1][0] = vNeu.matrix[1][0];//Daten werden übertragen

				OrtsvektorErde.matrix[0][0] = OrtAlt.matrix[0][0];
				OrtsvektorErde.matrix[1][0] = OrtAlt.matrix[1][0]; //Ortsvektor-Erde wird initialisiert

				VerbindungOSOE = OrtsvektorErde.subtrahieren(OrtsvektorSonne); //Verbindungsvektor Sonne-Erde wird berechnet

				if(first == true) {//Nur im ersten Durchlauf erfüllt

					kreuzprodukt = Erdachse.kreuzprodukt(VerbindungOSOE); //Berechnet das Kreuzprodukt zwischen der Erdachse und OSOE
					skalarproduktAns = Matrizen.skalarprodukt(VerbindungOSOE, kreuzprodukt); //Initialisiert das SkalarproduktAns um einen Vergleich zu ermöglichen
					first = false;
				}

				else if(first == false) {//Wird ab der 2. Runde durchgeführt

					skalarprodukt = Matrizen.skalarprodukt(VerbindungOSOE, kreuzprodukt); //Berechnet das aktuelle Skalarprodukt

					if (skalarproduktAns > 0 && skalarprodukt < 0) {//Orthogonalitäts-Nachweis

						zeitdifferenz = sec*(-1);//Sec ist aufsummierte Differenz zwischen wahren und mittleren Sonnetag
						//hier wurde -1 angefügt, da VZ falsch war

						if(zeitdifferenz < -43200) {//Ab mehr als 1/2 Tag vorgehen wird es auf nachgehen umgestellt
							differenz.add(zeitdifferenz + 86400);//Ergebnis wird eingetragen
						}
						else {
							differenz.add(zeitdifferenz);//Ergebnis wird eingetragen
						}

						skalarproduktAns = skalarprodukt; //Skalarprodukt wird für Vergleich gesichert
					}
					else {
						skalarproduktAns = skalarprodukt;//Skalarprodukt wird für Vergleich gesichert
					}
				}
				kreuzprodukt = drehmatrix.multiplizieren(kreuzprodukt); //Erdrotation -> Kreuzprodukt wird um die Erdachse gedreht
			}
		}
		return differenz;
	}

	public static void main(String[] args) {

		double[][] erdachse = {{-0.3877533558525311}, {0.08878643839567496}, {0.917477140522918}}; //normierte Erdachse

		Matrizen Erdachse = new Matrizen(erdachse);

		ArrayList<Integer> ergebnisse = erdumlaufbahnBerechnen(Erdachse); //Datenpunkte werden übertragen

		Fourier.eingabe = new double[ergebnisse.size()];

		for (int i = 0; i < ergebnisse.size(); i++) {

			Fourier.eingabe[i] = (double) ergebnisse.get(i);

			System.out.println(ergebnisse.get(i));
		}//Ergebnisse werden in ein Array geschrieben

		Fourier.koeffizientenBerechnen(); 
		Fourier.ausgeben(); //Gleichung wird berechnet
	}
}

