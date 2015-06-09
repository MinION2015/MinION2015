package error;

import java.util.ArrayList;
import java.util.Random;
 
/**
 * Alle Wahrscheinlichkeiten für die Datenbank
 * @author Kevin Heid
 * Class written by Kevin Heid (ComputerScience and Physics Bachelor student at UNiverity of Tuebingen)
 * changed by Friederike Hanssen
 * This class will calculate different random integers and doubles
 * Input:
 * Output: random number
 */
public class Chance {
    /**
     * Zufallszahlengenerator
     */
    private static final Random generator = new Random();
     
    /**
     * aktueller Seed
     */
    private static long seed;
     
    /**
     * Initialisierung des Randomgenerators
     * Initializing random generator
     */
    private static final void initGenerator() {
        seed = generator.nextLong();
        generator.setSeed(seed);
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus
     * Chooses rand double in intervall inf - sup
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @return Der Zufallswert
     */
    public static final double getRand(int inf, int sup) {
        initGenerator();
        return ((sup-inf+1)*generator.nextDouble()+inf);
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus
     * Chooses rand double in intervall inf - sup
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @return Der Zufallswert
     */
    public static final int getRandInt(int inf, int sup) {
        initGenerator();
        return (int)Math.floor(((sup-inf + 1)*generator.nextDouble()+inf));
    }
     

     
    /**
     * Gibt eine Zufallszahl z, 0 <= z <= 1
     * Returns rand double between 0,1
     * @return Die Zufallszahl
     */
    public static final double getRand() {
        initGenerator();
        return generator.nextDouble();
    }
     
    /**
     * Gibt eine Zufallszahl z
     * returns random int
     * @return Die Zufallszahl
     */
    public static final int getRandInt() {
        initGenerator();
        return generator.nextInt();
    }
     

}