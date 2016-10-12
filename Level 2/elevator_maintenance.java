//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
//https://www.google.com/foobar/?eid=sZXxV6PEO8a6eY74hqAK&usg=AG3vBD0yl65N9g1D5yEH8GBmkRSUB2_64A


public class elevator_maintenance {
    /*
        Elevator Maintenance
        ====================

        You've been assigned the onerous task of elevator maintenance - ugh! It wouldn't be so bad, except that all the elevator documentation has been lying in a disorganized pile at the bottom of a filing cabinet for years, and you don't even know what elevator version numbers you'll be working on. 

        Elevator versions are represented by a series of numbers, divided up into major, minor and revision integers. New versions of an elevator increase the major number, e.g. 1, 2, 3, and so on. When new features are added to an elevator without being a complete new version, a second number named "minor" can be used to represent those new additions, e.g. 1.0, 1.1, 1.2, etc. Small fixes or maintenance work can be represented by a third number named "revision", e.g. 1.1.1, 1.1.2, 1.2.0, and so on. The number zero can be used as a major for pre-release versions of elevators, e.g. 0.1, 0.5, 0.9.2, etc (Commander Lambda is careful to always beta test her new technology, with her loyal henchmen as subjects!).

        Given a list of elevator versions represented as strings, write a function answer(l) that returns the same list sorted in ascending order by major, minor, and revision number so that you can identify the current elevator version. The versions in list l will always contain major numbers, but minor and revision numbers are optional. If the version contains a revision number, then it will also have a minor number.

        For example, given the list l as ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"], the function answer(l) would return the list ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]. If two or more versions are equivalent but one version contains more numbers than the others, then these versions must be sorted ascending based on how many numbers they have, e.g ["1", "1.0", "1.0.0"]. The number of elements in the list l will be at least 1 and will not exceed 100.

        Languages
        =========

        To provide a Python solution, edit solution.py
        To provide a Java solution, edit solution.java

        Test cases
        ==========

        Inputs:
            (string list) l = ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"]
        Output:
            (string list) ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]

        Inputs:
            (string list) l = ["1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"]
        Output:
            (string list) ["0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"]

        Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
    */

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