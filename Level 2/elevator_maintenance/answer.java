//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
public class answer{
    public static String[] answer(String[] l) { 
        if( l == null || l.length == 0 ) {
            return null;
        }
        // Your code goes here.
        String[] sortedList = QuickSort(l, 0, l.length-1);
        
        return sortedList;
    }
    public static String[] QuickSort(String[] output, int low, int high ) {
        int i = low;
        int j = high;
        String pivot = output[low + (high-low) / 2 ];
        String temp;
        while( i <= j ){
            while( 0 > compareVersions(output[i], pivot) ) {
                i++;
            }
            while( 0 < compareVersions(output[j], pivot) ) {
                j--;
            }
            if( i <= j ) {
                temp = output[j];
                output[j] = output[i];
                output[i] = temp;
                i++;
                j--;
            }
        }
        
        if( i < high ){
            output = QuickSort(output, i, high);
        }
        if( j > low ){
            output = QuickSort(output, low, j);
        }
        return output;
    }
    
    public static int compareVersions(String a, String b){
        if( a.equals(b) ) {
            return 0;
        }
        String[] first = a.split("\\.");
        String[] second = b.split("\\.");
        int test = Integer.parseInt(first[0]) - Integer.parseInt(second[0]);
        if( test != 0 ) {
            return (test > 0)?1:-1;
        }
        
        int firstLength = first.length;
        int secondLength = second.length;
        if( firstLength == 1 || secondLength == 1 ) {
            return ( firstLength > secondLength )? 1:-1;
        }
        
        test = Integer.parseInt(first[1]) - Integer.parseInt(second[1]);
        if( test != 0 ) {
            return (test > 0)?1:-1;
        }
        
        if( firstLength == 2 || secondLength == 2 ) {
            return ( firstLength > secondLength )? 1:-1;
        }
        
        test = Integer.parseInt(first[2]) - Integer.parseInt(second[2]);
        if( test != 0 ) {
            return (test > 0)?1:-1;
        }
        
        return 0;
    }
}