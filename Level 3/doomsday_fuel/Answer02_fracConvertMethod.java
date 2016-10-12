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
        

        double factor = 0;
        double currFactor = 1;
        int currState = 0;
        double[] endStateProbability = new double[stateNodes.length];
        Queue<Integer> stateQueue = new LinkedList<Integer>();
        Queue<Double> factorQueue = new LinkedList<Double>();
        stateQueue.add(currState);
        factorQueue.add(currFactor);
        while( !stateQueue.isEmpty() && !factorQueue.isEmpty() ) {
            currState = stateQueue.remove();
            currFactor = factorQueue.remove();
            if( !isTerminalState[currState] ) {
                for(int i = 0; i < stateNodes.length; i++) {
                    factor = stateNodes[currState].getTransformChance( i );
                    if( factor != 0 && (currFactor * factor) > 0.000001) {
                        if( !isTerminalState[i] ) {
                            stateQueue.add(i);
                            factorQueue.add(currFactor * factor);
                        } else {
                            endStateProbability[i] += currFactor * factor;
                        }
                    }
                }
            }
        }


        int denom = 1;
        int hsetIndex = 0;
        int[][] helperSet = new int[numTerminalStates][2];
        for(int i = 0; i < isTerminalState.length; i++){
            if( isTerminalState[i] ) {
                factor = endStateProbability[i];
                helperSet[hsetIndex] = FractionProcesser.GetFraction( factor );
                denom = FractionProcesser.lcm(denom, helperSet[hsetIndex][1]);
                hsetIndex++;
            }
        }
        
        int multiple = 1;
        int[] resultSet = new int[numTerminalStates + 1];
        for( int i = 0; i < resultSet.length-1; i++) { 
            multiple = denom / helperSet[i][1];
            resultSet[i] = helperSet[i][0] * multiple;
        }
        resultSet[resultSet.length-1] = denom;
        
        return resultSet;
    }
}
class FractionProcesser{
    public static int gcm(int a, int b) {
        return b == 0 ? a : gcm(b, a % b);
    }
    public static int lcm(int a, int b){
        return (a * b) / gcm(a, b);
    }
    public static int[] GetFraction(double input){
        double tolerance = 0.001;
        double p1 = 1;
        double q1 = 0;
        double p2 = 0;
        double q2 = 1;
    
        double b = input;
        do {
            double a = Math.floor(b);
            double aux = p1;
            p1 = a*p1+p2; 
            p2 = aux;
            aux = q1; 
            q1 = a*q1+q2; 
            q2 = aux;
            b = 1/(b-a);
        } while(Math.abs(input-p1/q1) > input*tolerance);
        
        return new int[] {(int)p1, (int)q1};
    }
}
class StateNode{
    private double denominator = 0;
    private double[] probabilityMap = null;
    
    public StateNode(int[] nextStateMap) {
        int length = nextStateMap.length;
        probabilityMap = new double[length];
        for(int i = 0; i < length; i++ ){
            probabilityMap[i] = nextStateMap[i];
        }
        
        processMap();
    } 

    private void processMap() {
        denominator = 0;
        for(double i : probabilityMap) {
            denominator += i;
            if( i < 0 ) {
                denominator = 0; 
                return;
            }
        }

        if( denominator < 1 ) {
            return;
        }

        for(int i = 0; i < probabilityMap.length; i++) {
            probabilityMap[i] /= denominator;
        }
    }

    public boolean isTerminal() {
        return denominator == 0;
    }

    public double getTransformChance(int state) {
        if( state < 0 || state > probabilityMap.length ) {
            return 0;
        } 

        return probabilityMap[state];
    }
}