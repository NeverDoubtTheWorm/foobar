//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
public class answer{
    public static int answer(int total_lambs) { 

        // Your code goes here.
        return mostStingy(total_lambs) - mostGenerous(total_lambs);
    }
    public static int mostGenerous( int total_lambs ) {
        if( total_lambs < 1) {
            return 0;
        }
        int numHenchmen = 1;
        int lambAllowance = 1;
        int currentLambs = total_lambs - lambAllowance;
        
        while (currentLambs > 0) {
            lambAllowance *= 2;
            currentLambs -= lambAllowance;;
            if( currentLambs >= 0 ) {
                numHenchmen++;
            }
        }
        
        return numHenchmen;
    }
    public static int mostStingy( int total_lambs ) {
        if( total_lambs < 1) {
            return 0;
        }
        // #notAFibonacci
        int numHenchmen = 1;
        int lastLastAllowance = 1;
        int currentLambs = total_lambs - lastLastAllowance;
        if( currentLambs < 1) {
            return numHenchmen;
        }
        int lastAllowance= 1;
        currentLambs -= lastAllowance;
        numHenchmen++;
        
        int lambAllowance = 0;
        
        while (currentLambs > 0) {
            lambAllowance = lastAllowance + lastLastAllowance;
            currentLambs -= lambAllowance;
            if( currentLambs >= 0 ) {
                numHenchmen++;
                lastLastAllowance = lastAllowance;
                lastAllowance = lambAllowance;
            }
        }
        
        return numHenchmen;
    }
} 