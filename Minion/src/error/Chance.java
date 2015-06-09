package error;

import java.util.ArrayList;
import java.util.Random;
 
/**
 * Alle Wahrscheinlichkeiten für die Datenbank
 * @author Kevin Heid
 *
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
     */
    private static final void initGenerator() {
        seed = generator.nextLong();
        generator.setSeed(seed);
    }
     
    /**
     * Initialisierung des Randomgenerators
     */
    private static final void initGenerator(long seed) {
        Chance.seed = seed;
        generator.setSeed(seed);
    }
     
    /**
     * @return Der zuletzt benutzte Seed
     */
    public static final long getLastUsedSeed() {
        return seed;
    }
     
    /**
     * Berechnet ob eine Wahrscheinlichkeit eintritt
     * @param chance Die Chance, 0 <= Chance <= 1
     * @return Gott ist TRUE gnädig
     */
    public static final boolean godGiven(double chance) {
        initGenerator();
        return(generator.nextDouble() <= chance);
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @return Der Zufallswert
     */
    public static final double getRand(int inf, int sup) {
        initGenerator();
        return ((sup-inf+1)*generator.nextDouble()+inf);
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus, deteministisch
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @param seed der zu benutztende Seed
     * @return Der Zufallswert
     */
    public static final double getRand(int inf, int sup, long seed) {
        initGenerator(seed);
        return ((sup-inf)*generator.nextDouble()+inf);
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @return Der Zufallswert
     */
    public static final int getRandInt(int inf, int sup) {
        initGenerator();
        return (int)Math.floor(((sup-inf + 1)*generator.nextDouble()+inf));
    }
     
    /**
     * Wählt eine Zufallszahl im Intervall aus, deteministisch
     * @param inf Untere Grenze
     * @param sup Obere Grenze
     * @param seed der zu benutztende Seed
     * @return Der Zufallswert
     */
    public static final int getRandInt(int inf, int sup, long seed) {
        initGenerator(seed);
        return (int)Math.floor(((sup-inf + 1)*generator.nextDouble()+inf));
    }
     
    /**
     * Gibt eine Zufallszahl z, 0 <= z <= 1
     * @return Die Zufallszahl
     */
    public static final double getRand() {
        initGenerator();
        return generator.nextDouble();
    }
     
    /**
     * Gibt eine Zufallszahl z, 0 <= z <= 1, deterministisch
     * @param seed der zu benutztende Seed
     * @return Die Zufallszahl
     */
    public static final double getRand(long seed) {
        initGenerator();
        return generator.nextDouble();
    }
     
    /**
     * Gibt eine Zufallszahl z
     * @return Die Zufallszahl
     */
    public static final int getRandInt() {
        initGenerator();
        return generator.nextInt();
    }
     
    /**
     * Gibt eine Zufallszahl z, deterministisch
     * @param seed der zu benutzende Seed
     * @return Die Zufallszahl
     */
    public static final int getRandInt(long seed) {
        initGenerator(seed);
        return generator.nextInt();
    }
     
    /**
     * Zufallsauswahl aus Array mit Gewichtung
     * @param keys Die Schlüssel aus welchen ausgewählt wird
     * @param props die Gewichtungen zu den Schlüsseln
     * @return Der Zufällige Schlüssel
     */
    public static <T> T chooseWeighted(T[] keys, double[] probs){
        //summing up all the probabilities
        double probSum = 0;
        for(int i = 0; i < probs.length; i++){
            probSum += probs[i];
        }
        //creating an array with the relative probabilities
        double[] relProbs = new double[probs.length];
        for(int i = 0; i < relProbs.length; i++){
            relProbs[i] = probs[i]/probSum;
        }
        //making an array with intervals for later calculation
        double[] accProb = new double[probs.length];
        accProb[0] = relProbs[0];
        for(int i = 1; i < accProb.length; i++){
            accProb[i] = accProb[i-1] + relProbs[i];
        }
        //random number
        initGenerator();
        double rand = generator.nextDouble();
        T result = null;
        for(int i = 0; i < accProb.length; i++){
            if(rand < accProb[i]){
                result = keys[i];
                break;
            }
            else{
                continue;
            }
        }
        return result;
    }
     
    /**
     * Zufallsauswahl aus Array mit Gewichtung
     * @param keys Die Schlüssel aus welchen ausgewählt wird
     * @param props die Gewichtungen zu den Schlüsseln
     * @return Der Zufällige Schlüssel
     */
    public static <T> T chooseWeighted(ArrayList<T> keys, ArrayList<Double> probs){
        //summing up all the probabilities
        double probSum = 0;
        for(int i = 0; i < probs.size(); i++){
            probSum += probs.get(i);
        }
         
        //creating an array with the relative probabilities
        double[] relProbs = new double[probs.size()];
        for(int i = 0; i < relProbs.length; i++){
            relProbs[i] = probs.get(i)/probSum;
        }
         
        //making an array with intervals for later calculation
        double[] accProb = new double[probs.size()];
        accProb[0] = relProbs[0];
        for(int i = 1; i < accProb.length; i++){
            accProb[i] = accProb[i-1] + relProbs[i];
        }
         
        //random number
        initGenerator();
        double rand = generator.nextDouble();
        T result = null;
        for(int i = 0; i < accProb.length; i++){
            if(rand < accProb[i]){
                result = keys.get(i);
                break;
            }
        }
        return result;
    }
}