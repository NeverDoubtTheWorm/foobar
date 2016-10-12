//Welcome to foobar version 53-10-g85713ac-beta (2016-09-28-19:53+0000)
public class answer {
    public static String answer(String s) { 

        // Your code goes here.
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            if( c >= 'a' && c <= 'z' ) {
                c = (char)((int)('z' - c + 'a')); 
            }
            sb.append(c);
        }
        
        return sb.toString();
    }
}