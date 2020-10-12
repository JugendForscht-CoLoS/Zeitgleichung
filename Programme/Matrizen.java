import java.lang.reflect.Array;

/**
 * Klasse zum Rechnen mit Matrizen und Vektoren
 * @author Jonas Riemann & Tim Jaeger
 * @version 2.0*/
public class Matrizen {

	/**
	 * Matrix in Form eines zweidimensionalen Arrays*/
	public double[][] matrix;

	/**
	 * initialisiert die Instanzvariable matrix
	 * @param matrix die zu übergebene Matrix*/
	public Matrizen(double[][]matrix) {
		this.matrix = matrix;
	}
    
    /**
     * Methode zum Visualisiert der Instanz-Matrix
     * @throws IllegalArgumentException Wenn die InstanzVariable nicht initialisiert wurde
     * @return Gibt einen String der visualisierten Matrix zurück*/
    public String print() {

        String s = "";

        if (matrix == null) {
            throw new IllegalArgumentException("Matrix existiert nicht!");
        }
        int reihe = Array.getLength(matrix);
        int spalte = Array.getLength(matrix[0]);

        for(int row = 0; row < reihe; row++) {
            for(int column = 0; column < spalte; column++) {
                s = s + "[ " + Double.toString(matrix[row][column]) + " ]";
            }
            s = s + "\n";
        }
        return s;
    }
    
    /**
     * Methode zum Addieren der Instanz-Matrix mit einer übergebenen Matrix
     * @param matrix2 2.Summand
     * @throws IllegalArgumentException Wenn die Matrizen nicht der selben Ordnung sind
     * @return Gibt die Summe-Matrix zurück*/
    public Matrizen addieren(Matrizen matrix2) {

        int matrixRow = Array.getLength(matrix);
        int matrixColumn = Array.getLength(matrix[0]);
        int matrix2Row = Array.getLength(matrix2.matrix);
        int matrix2Column = Array.getLength(matrix2.matrix[0]);

        if (matrixColumn != matrix2Column | matrixRow != matrix2Row) {
            throw new IllegalArgumentException("Die zu addierenden Matrizen müssen der selben Ordnung sein!");
        }
        double [][] summe = new double [matrixRow][matrixColumn];

        for (int Column = 0; Column < matrixColumn; Column++) {
            for (int Row = 0; Row < matrixRow; Row++) {
                summe [Row][Column] = matrix [Row][Column] + matrix2.matrix [Row][Column];
            }
        }
        Matrizen Summe = new Matrizen(summe);
        return Summe;
    }

	/**
	 * Methode zum Subtrahieren der Instanz-Matrix durch eine übergebene Matrix
	 * @param matrix2 Subtrahent der Subtraktion
	 * @throws IllegalArgumentException Wenn die Matrizen nicht der selben Ordnung sind
	 * @return Gibt die Differenz-Matrix zurück*/
	public Matrizen subtrahieren(Matrizen matrix2) {

		int matrixRow = Array.getLength(matrix); 
		int matrixColumn = Array.getLength(matrix[0]); 
		int matrix2Row = Array.getLength(matrix2.matrix); 
		int matrix2Column = Array.getLength(matrix2.matrix[0]);

		if (matrixColumn != matrix2Column | matrixRow != matrix2Row) {
			throw new IllegalArgumentException("Die zu addierenden Matrizen müssen der selben Ordnung sein!");
		}
		double [][] differenz = new double [matrixRow][matrixColumn];

		for (int Column = 0; Column < matrixColumn; Column++) {
			for (int Row = 0; Row < matrixRow; Row++) {
				differenz [Row][Column] = matrix [Row][Column] - matrix2.matrix [Row][Column];
			}
		}
		Matrizen Differenz = new Matrizen(differenz);
		return Differenz;
    }
    
    /**
     * Methode zum Multiplizieren der Instanz-Matrix mit einer übergebenen Matrix
     * @param matrix2 2.Faktor-Matrix
     * @throws IllegalArgumentException Wenn eine Matrizenmultiplikation nicht möglich ist
     * @return Gibt die Produkt-Matrix zurück*/
    public Matrizen multiplizieren(Matrizen matrix2) {

        int matrixRow = Array.getLength(matrix);
        int matrixColumn = Array.getLength(matrix[0]);
        int matrix2Row = Array.getLength(matrix2.matrix);
        int matrix2Column = Array.getLength(matrix2.matrix[0]);

        double[][] produkt = new double[matrixRow][matrix2Column];

        if(matrixColumn != matrix2Row) {
            throw new IllegalArgumentException("Matrizenen können nicht multiplitziert werden!");
        }
        int Elemente = matrixRow * matrix2Column;

        double zwischenProdukt = 0;
        int matrix3row = 0;
        int matrix3column = 0;

        for (int index = 0; index < Elemente; index++ ) {

            for (int einzelElement = 0; einzelElement < matrixColumn; einzelElement++) {
                zwischenProdukt = matrix[matrix3row][einzelElement] * matrix2.matrix[einzelElement][matrix3column] + zwischenProdukt;
            }
            produkt[matrix3row][matrix3column] = zwischenProdukt;

            zwischenProdukt = 0;

            if (matrix3column < matrix2Column) {
                matrix3column++;
            }
            if (matrix3column >= matrix2Column) {
                matrix3column = 0;
                matrix3row++;
            }
        }
        Matrizen matrix3 = new Matrizen(produkt);
        return matrix3;
    }

	/**
	 * Methode zum Multiplizieren der Instanz-Matrix mit einem Skalar
	 * @param Skalar das übergebene Skalar
	 * @return Gibt die Produkt-Matrix zurück*/
	public Matrizen multiplizieren(double Skalar) {

		int matrixRow = Array.getLength(matrix); 
		int matrixColumn = Array.getLength(matrix[0]); 

		double [][] produkt = new double [matrixRow][matrixColumn];

		for (int Column = 0; Column < matrixColumn; Column++) {
			for (int Row = 0; Row < matrixRow; Row++) {
				produkt [Row][Column] = matrix [Row][Column] * Skalar;
			}
		}
		Matrizen Produkt = new Matrizen(produkt);
		return Produkt;
	}
    
    /**
     * Methode zum Dividieren der Instanz-Matrix durch ein Skalar
     * @param Skalar Das übergebene Skalar
     * @return Gibt die Quotient-Matrix zurück*/
    public Matrizen dividieren(double Skalar) {

        int matrixRow = Array.getLength(matrix);
        int matrixColumn = Array.getLength(matrix[0]);

        double [][] quotient = new double [matrixRow][matrixColumn];

        for (int Column = 0; Column < matrixColumn; Column++) {
            for (int Row = 0; Row < matrixRow; Row++) {
                quotient [Row][Column] = matrix [Row][Column] / Skalar;
            }
        }
        Matrizen Quotient = new Matrizen(quotient);
        return Quotient;
    }
    
    /**
     *Methode zum erstellen einer Rotationsmatrix
     *@param Achse normierte Drehachse
     *@param Winkel Drehwinkel
     *@return Drehmatrix
     */
    public static Matrizen drehmatrix(Matrizen Achse, double Winkel) {

        double cos = Math.cos(Winkel);
        double sin = Math.sin(Winkel);

        double n1 = Achse.matrix[0][0];
        double n2 = Achse.matrix[1][0];
        double n3 = Achse.matrix[2][0];

        double[][]drehMatrix = {{n1*n1*(1-cos) + cos, n1*n2*(1-cos) - n3*sin, n1*n3*(1-cos) + n2*sin},
                {n2*n1*(1-cos) + n3*sin, n2*n2*(1-cos) + cos, n2*n3*(1-cos) - n1*sin},
                {n3*n1*(1-cos) - n2*sin, n3*n2*(1-cos) + n1*sin, n3*n3*(1-cos) + cos}};
        
        Matrizen drehmatrix = new Matrizen(drehMatrix);
        
        return drehmatrix;
    }

	/**
	 * Methode zum Berechnen des Betrags des Instanz-Vektors
	 * @throws IllegalArgumentException Wenn der Instanz-Vektor keine 3x1 oder 2x1 Matrix ist
	 * @return Gibt den Betrag des Instanz-Vektors zurück*/
	public double vektorBetrag() {

		int dimension = Array.getLength(matrix);
		int isavector = Array.getLength(matrix[0]);

		double Betrag = 0;

		if (isavector != 1) {
			throw new IllegalArgumentException("Matrizen müssen Vektoren sein!");
		}
		if (dimension < 2 | dimension > 3) {
			throw new IllegalArgumentException("Methode nur für 2. und 3. Dimension definiert!");
		}
		if (dimension == 2) {
			Betrag = Math.sqrt(Math.pow( matrix[0][0], 2) + Math.pow( matrix[1][0], 2));
		}
		else if (dimension == 3) {
			Betrag = Math.sqrt(Math.pow( matrix[0][0], 2) + Math.pow( matrix[1][0], 2) + Math.pow( matrix[2][0], 2));
		}
		return Betrag;
	}
    
    /**
     * Methode zum Berechnen des Skalarprodukts zweier Vektoren des R3
     * @param v1 1.Vektor
     * @param v2 2.Vektor
     * @throws IllegalArgumentException Wenn eines der übergebenen Matrizen-Objeket keine 3x1 Matrix ist
     * @return Gibt das Skalarprodukt zurück*/
    public static double skalarprodukt(Matrizen v1, Matrizen v2){

        if(Array.getLength(v1.matrix[0]) != 1 && Array.getLength(v1.matrix) != 3 || Array.getLength(v2.matrix[0]) != 1 && Array.getLength(v2.matrix) != 3){
            throw new IllegalArgumentException("Der Vektor hat nicht 3 Dimensionen oder er ist kein Vektor!");
        }

        double sp = v1.matrix[0][0] * v2.matrix[0][0] + v1.matrix[1][0] * v2.matrix[1][0] + v1.matrix[2][0] * v2.matrix[2][0];
        return sp;
    }
    
    /**
     *Methode zum Berechnen des Skalarprodukts zweier Vektoren des R2
     *@param v1 1.Vektor
     *@param v2 2.Vektor
     *@throws IllegalArgumentException Wenn eines der übergebenen Matrizen-Objeket keine 2x1 Matrix ist
     *@return Gibt das Skalarprodukt zurück
     */
    public static double skalarprodukt2(Matrizen v1, Matrizen v2){

        if(Array.getLength(v1.matrix[0]) != 1 && Array.getLength(v1.matrix) != 2 || Array.getLength(v2.matrix[0]) != 1 && Array.getLength(v2.matrix) != 2){
            throw new IllegalArgumentException("Der Vektor hat nicht 2 Dimensionen oder er ist kein Vektor!");
        }

        double sp = v1.matrix[0][0] * v2.matrix[0][0] + v1.matrix[1][0] * v2.matrix[1][0];
        return sp;
    }
    
    /**
     * Methode zum Berechnen des Kreuzprodukts des Instanz-Vektors mit einem übergebenen Vektor aus
     * @param vektor 2.Vektor
     * @throws IllegalArgumentException Wenn einer der beiden Vektoren keine 3x1 Matrix ist
     * @return Gibt das Kreuzprodukt zurück*/
    public Matrizen kreuzprodukt(Matrizen vektor) {

        int dimension = Array.getLength(vektor.matrix);
        int referenz = Array.getLength(matrix);
        int isavector = Array.getLength(vektor.matrix[0]);
        int vektorreferenz = Array.getLength(matrix[0]);

        if (isavector != 1 | vektorreferenz != 1) {
            throw new IllegalArgumentException("Matrizen müssen Vektoren sein!");
        }
        if (dimension != 3 | referenz != 3) {
            throw new IllegalArgumentException("Methode kreuzprodukt ist nur für die 3. Dimension definiert!");
        }
        double [][] produkt = {{matrix[1][0] * vektor.matrix[2][0] - matrix[2][0] *vektor.matrix[1][0]},
                {matrix[2][0] * vektor.matrix[0][0] - matrix[0][0] *vektor.matrix[2][0]},
                {matrix[0][0] * vektor.matrix[1][0] - matrix[1][0] *vektor.matrix[0][0]}};
        Matrizen Produkt = new Matrizen(produkt);
        return Produkt;
    }

}

