//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
import java.util.Random;
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
        
        int[] stateCounts = new int[stateNodes.length];
        
        int currentState;
        int depthLimit;
        boolean success;
        int numSimulations = 17777777;
        double completeSims = 0;
        Random rand = new Random();
        for(int i = 0; i < numSimulations; i++){
            depthLimit = 10;
            currentState = 0;
            success = true;
            while( !isTerminalState[currentState] ) {
                if( --depthLimit < 0 ) {
                    success = false;
                    break;
                }
                currentState = stateNodes[currentState].nextState(rand);
            }
            if( success ) {
                stateCounts[ currentState ]++;
                completeSims++;
            }
        }
        
        double factor;
        int denom = 1;
        int hsetIndex = 0;
        int[][] helperSet = new int[numTerminalStates][2];
        for(int i = 0; i < isTerminalState.length; i++){
            if( isTerminalState[i] ) {
                factor = ((double) stateCounts[i]) / (completeSims);
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
    private int denominator = 0;
    public int[] numerators = null;
    
    public StateNode(int[] nextStateMap) {
        int length = nextStateMap.length;
        numerators = new int[length];
        for(int i = 0; i < length; i++ ){
            numerators[i] = nextStateMap[i];
        }
        
        setDenominator();
    } 
    
    public int nextState(Random random) {
        int rand = random.nextInt(denominator);
        for( int i = 0; i < numerators.length; i++ ) {
            if( rand < numerators[i] ) {
                return i;
            }
            if( numerators[i] < 1) {
                continue;
            }
            rand -= numerators[i];
        }
        return -1;
    }
    public int getDenominator(){
        return denominator;
    }
    public boolean isTerminal() {
        return denominator == 0;
    }
    private void setDenominator() {
        int sum = 0;
        for(int i : numerators) {
            sum += i;
            if( i < 0 ) {
                denominator = 0; 
                return;
            }
        }
        denominator = sum;
    }
}