//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
import java.util.*;

public class Answer {   
    public static int[] answer(int[][] m) { 

        // Your code goes here.
        StateNode[] stateNodes = new StateNode[m.length];
        for(int i = 0; i < m.length; i++) {
            stateNodes[i] = new StateNode(m[i]);
        }
        
        boolean[] isTerminalState = new boolean[m.length];
        int numTerminalStates = 0;
        for(int i = 0; i < stateNodes.length; i++) {
            if( stateNodes[i].isTerminal() ) {
                isTerminalState[i] = true;
                numTerminalStates++;
            }
        }
        

        Fraction[] endStateProbability = new Fraction[stateNodes.length];
        for(int i = 0; i < endStateProbability.length; i++) {
            endStateProbability[i] = new Fraction(0,1);
        }
        int currState = 0;
        Fraction factor;
        Fraction currFactor = new Fraction(1,1);
        Queue<Integer> stateQueue = new LinkedList<Integer>();
        Queue<Fraction> factorQueue = new LinkedList<Fraction>();
        stateQueue.add(currState);
        factorQueue.add(currFactor);

        while( !stateQueue.isEmpty() && !factorQueue.isEmpty() ) {
            currState = stateQueue.remove();
            currFactor = factorQueue.remove();
            if( !isTerminalState[currState] && !currFactor.lessThan(0.005) ) {
                for(int i = 0; i < stateNodes.length; i++) {
                    factor = stateNodes[currState].getTransformChance( i );
                    if( !factor.isZero() ) {
                        factor = factor.getCopy();
                        factor.times(currFactor);
                        if( !isTerminalState[i] ) {
                            stateQueue.add(i);
                            factorQueue.add( factor );
                        } else {
                            endStateProbability[i].plus(factor);
                        }
                    }
                }
            }
        }


        int denom = 1;
        int hsetIndex = 0;
        Fraction[] helperSet = new Fraction[numTerminalStates];
        for(int i = 0; i < isTerminalState.length; i++){
            if( isTerminalState[i] ) {
                helperSet[hsetIndex] =  endStateProbability[i];
                denom = Fraction.lcm( denom, helperSet[hsetIndex].getDenominator() );
                hsetIndex++;
            }
        }
        
        int multiple = 1;
        int[] resultSet = new int[numTerminalStates + 1];
        for( int i = 0; i < resultSet.length-1; i++) { 
            multiple = denom / helperSet[i].getDenominator();
            resultSet[i] = helperSet[i].getNumerator() * multiple;
        }
        resultSet[resultSet.length-1] = denom;
        
        return resultSet;
    }
}
class Fraction{
    private int numerator = 0;
    private int denominator = 1;

    public Fraction(int num, int denom){
        if(denom != 0) {
            numerator = num;
            denominator = denom;
        }
    }

    public Fraction getCopy() {
        return new Fraction(numerator, denominator);
    }

    public boolean lessThan(double ratio){
        double value = ((double) numerator / ( (double) denominator));
        return ratio > value;
    }

    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }
    public boolean isZero() {
        return numerator == 0;
    }

    public void plus( Fraction b ) {
        numerator = denominator * b.getNumerator() + numerator * b.getDenominator();
        denominator = denominator * b.getDenominator();

        reduce();        
    }

    public void times( Fraction b ) {
        numerator *= b.getNumerator();
        denominator *= b.getDenominator();

        reduce();
    }

    public void reduce() {
        int gcd = gcm(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    public static int gcm(int a, int b) {
        return b == 0 ? a : gcm(b, a % b);
    }
    public static int lcm(int a, int b){
        return (a * b) / gcm(a, b);
    }
}
class StateNode{
    private int denominator = 0;
    private int[] numerators = null;
    private Fraction[] transformChance= null;
    private static Fraction zeroFraction;
    static {
        zeroFraction = new Fraction(0, 1); 
    };
    
    public StateNode(int[] nextStateMap) {
        int length = nextStateMap.length;
        numerators = new int[length];
        transformChance = new Fraction[length];
        for(int i = 0; i < length; i++ ){
            numerators[i] = nextStateMap[i];
        }
        
        processMap();
    } 

    private void processMap() {
        denominator = 0;
        for(int i : numerators) {
            denominator += i;
            if( i < 0 ) {
                denominator = 0; 
                return;
            }
        }

        if( !isTerminal() ) {
            for(int i = 0; i < numerators.length; i++) {
                transformChance[i] = new Fraction(numerators[i], denominator);
            }
        }
    }

    public boolean isTerminal() {
        return denominator == 0;
    }

    public Fraction getTransformChance(int state) {
        if( state < 0 || state > numerators.length || isTerminal() ) {
            return zeroFraction;
        }
        return transformChance[state];
    }
}