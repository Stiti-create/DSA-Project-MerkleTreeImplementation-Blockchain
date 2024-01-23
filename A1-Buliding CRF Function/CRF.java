import HelperClasses.Pair;
import HelperClasses.sha256;

public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/

    public Pair<String, String> FindCollDeterministic() {

        return null;
    }

    public void FindCollRandomized() {
        
        String attempt_filename = "FindCollRandomizedAttempts.txt";
        String outcome_filename = "FindCollRandomizedOutcome.txt";

        
    }
}