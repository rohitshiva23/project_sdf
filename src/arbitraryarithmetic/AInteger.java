/* This is the AInteger class in a package called arbitrarayarithmetic.
 * It is being used for arbitrary arithmetic precision on integers.
 * The integers are being represented as strings.
 */

package arbitraryarithmetic;

public class AInteger {
    private String value;

    // a instance variable introduced for knowing sign

    private boolean isnegative;

    // default constructor initialises to "0"
    public AInteger() {
        this.value = "0";
        this.isnegative = false;
    }

    // constructor to instantiate

    public AInteger(String s) {

        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("Input string should not be null or empty");
        }
        s= s.trim();

        // removing sign

        if (s.charAt(0) == '-') {
            isnegative = true;
            s = s.substring(1);
        } 
        else {
            isnegative = false;
        }

        if (s.length() == 0) {
            throw new IllegalArgumentException("Invalid input :'-' is not a valid number.");
        }

        // checks each character if it is a digit
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("Invalid input : " + s);
            }
        }

        s = remove_front_zeros(s);  // removing leading zeros
        if (s.equals("0")) {
            isnegative = false;   // 0 cannot be negative
        }
        this.value = isnegative ? "-" + s : s;
    }
    
    public AInteger(AInteger obj) {
        this.value = obj.value;
        this.isnegative = obj.isnegative;
}

    public static AInteger parse(String s) {
    return new AInteger(s);
}

    // Getter methods 

    public String get_value() {
    return this.value;
}
    public boolean get_isnegative(){
        return this.isnegative;
    }

// Helper functions 

// Method removes leading zeros from string

public static String remove_front_zeros(String s) {
    boolean isnegative = s.length() > 0 && s.charAt(0) == '-';
    if (isnegative) {
        s = s.substring(1);
    }
    // skip over all front zeros except last digit
    int i = 0;
    while (i < s.length() - 1 && s.charAt(i) == '0') i++;
    s = s.substring(i);

    if (s.equals("0")) {
        isnegative = false;
    }

    return isnegative ? "-" + s : s;
}

// Method adds zeros to the shorter string of the two to make them of equal length  

public static String[] padzeros(String a, String b) {
    int len1 = a.length();
    int len2 = b.length();

    // pad a if it shorter
    while (len1 < len2) {
        a = "0" + a;
        len1++;
    }

    // pad b if it shorter
    while (len2 < len1) {
        b = "0" + b;
        len2++;
    }
    return new String[]{a, b};
}

// Method returns true if a is numerically smaller than b

public static boolean isSmaller(String a, String b) {
    a = remove_front_zeros(a);
    b = remove_front_zeros(b);

    int len1 = a.length();
    int len2 = b.length();

// simply giving based on length of the two
    if (len1 < len2) return true;
    if (len1 > len2) return false;

 // checking digit by digit for equal length ones from leftmost  
    for (int i = 0; i < len1; i++) {
        if (a.charAt(i) < b.charAt(i)) return true;
        if (a.charAt(i) > b.charAt(i)) return false;
    }

    return false; 
}

// Method to add strings simply based on magnitude

public static String addstr(String a, String b) {
    String[] paddednum = padzeros(a, b);
    a = paddednum[0];
    b = paddednum[1];

    int carry = 0;
    String result = "";

    // coverts charcters to digits from rightmost to left of string
    for (int i = a.length() - 1; i >= 0; i--) {
        int digit1 = a.charAt(i) - '0';    // coverts char into integers
        int digit2 = b.charAt(i) - '0';
    
    // actual addition logic
        int sum = digit1 + digit2 + carry;
        int digit = sum % 10;
        carry = sum / 10;

        result = digit + result;
    }
// if any extra digit is left at end 
    if (carry > 0) {
        result = carry + result;
    }

    return result;
}

// Method to subtract strings based on digit by digit math

public static String subtractstr(String a, String b) {
    String[] paddednum = padzeros(a, b);
    a = paddednum[0];
    b = paddednum[1];

    String result = "";
    int borrow = 0;

    // iterates from rightmost character of string
    for (int i = a.length() - 1; i >= 0; i--) {
        int digit1 = a.charAt(i) - '0' - borrow; // covert them into integers
        int digit2 = b.charAt(i) - '0';

        if (digit1 < digit2) {     // we borrow a one from next digit and add it
            digit1 += 10;
            borrow = 1;
        } else {
            borrow = 0;
        }

        int digit = digit1 - digit2;
        result = digit + result;  // append each digit to string
    }

    return remove_front_zeros(result);
}

// Main  arithmetic operations

// ADDITION

public AInteger add(AInteger other) {
    // first remove sign 

    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

 // If both numbers are of same sign 
    if (this.isnegative == other.isnegative) {
        String sum = addstr(amod, bmod) ;        // we simply add their magnitudes
        AInteger result = new AInteger(sum);

        // overriding sign as initally sign would always be +
        result.isnegative = this.isnegative && !sum.equals("0");   // assign sign of either one 
        result.value = result.isnegative ? "-" + sum : sum;        // changing value based on sign
        return result;
    } 
 // If both numbers are of opposite sign

    else {
        boolean aisBigger = !isSmaller(amod, bmod);  // returns true if a is bigger in magnitude 
        String diff = aisBigger ? subtractstr(amod, bmod) : subtractstr(bmod, amod);   // subtract smaller one from larger

        AInteger result = new AInteger(diff);

        // overriding the sign 
        if (diff.equals("0")) {
            result.isnegative = false;
        } else {
            result.isnegative = aisBigger ? this.isnegative : other.isnegative;      // assign sign of larger one
            result.value = result.isnegative ? "-" + diff : diff;
        }
        return result;
    }
}

// SUBTRACTION

public AInteger subtract(AInteger other) {
    // first remove sign
    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    // both have same sign

    if (this.isnegative == other.isnegative) {
        boolean aisBigger = !isSmaller(amod, bmod);
        String diff;

        // subtract smaller one from larger one 

        if (aisBigger) {
            diff = subtractstr(amod, bmod);
        } else {
            diff = subtractstr(bmod, amod);
        }

        AInteger result = new AInteger(diff);

        if (diff.equals("0")) {
            result.isnegative = false;
        } else {
            result.isnegative = aisBigger ? this.isnegative : !this.isnegative; // assign sign of a if is bigger reverse if not 
        }

        result.value = result.isnegative ? "-" + diff : diff; // value gets added the sign
        return result;
    }

    // both have opposite sign
    else {
        // add them first numerically

        String sum = addstr(amod, bmod);
        AInteger result = new AInteger(sum);

        // overrride sign by assigning sign of a 
        result.isnegative = this.isnegative;
        result.value = result.isnegative ? "-" + sum : sum ;
        return result ;
    }
}

// Helper functions for multiplication

// Multiplies a string with a single digit 

public static String digit_wise_multiply(String num, char digit_char) {

    int digit = digit_char - '0'; // Converts the digit_char to its integer value
    int carry = 0;
    String result = "";

    for (int i = num.length() - 1; i >= 0; i--) {

        int current = num.charAt(i) - '0'; 
        int product  = current * digit + carry; // multiply from leftmost digit of string

        int result_digit = product % 10; 
        carry = product / 10;           

        result = result_digit + result; 
    }

    if (carry > 0) {
        result = carry + result;     // append final digit if leftover
    }

    return remove_front_zeros(result);
}


public static String multiplystr(String a, String b) {
   
    a = remove_front_zeros(a);
    b = remove_front_zeros(b);

    if (a.equals("0") || b.equals("0")) return "0"; // one of the inputs is zero

    String result = "0";       
    int zeros_to_align = 0;         

    
    for (int i = b.length() - 1; i >= 0; i--) {

        char digit = b.charAt(i); 
        String partial_product = digit_wise_multiply(a, digit); // multiply entire a by each digit of b
 
        for (int j = 0; j < zeros_to_align; j++) {
            partial_product += "0";                         // multiplying partial products by factor of ten
        }

        result = addstr(result, partial_product);  // adding the partial products to final result using addstr logic
        
        zeros_to_align ++;                         // adds zero as goes from right to left as place value increases
    }

    return remove_front_zeros(result);
}

// MULTIPLICATION 
   
public AInteger multiply(AInteger other) {
    
    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    
    String product = multiplystr(amod, bmod);
    AInteger result = new AInteger(product);

    boolean result_zero = product.equals("0");

    // adding the sign infront of result 

    // assign - if both have opposite signs
    if ((this.isnegative != other.isnegative) && !result_zero) {
        result.isnegative = true;
        result.value = "-" + product;
    } 
    // assign + if same sign
    else {
        result.isnegative = false;
        result.value = product;
    }

    return result;
}

// Helper for Division 

public static String dividestr(String dividend, String divisor) {

    // Check for division by zero
    if (divisor.equals("0")) {
        throw new ArithmeticException("Division by zero");
    }

    // If dividend is smaller than divisor, quotient is 0
    if (isSmaller(dividend, divisor)) {
        return "0";
    }

    String quotient = "";
    String current = "";

    int index = 0;

    while (index < dividend.length()) {
        current += dividend.charAt(index); // bring down each char of dividend
        
        current = remove_front_zeros(current);

        if (current.equals("") || isSmaller(current, divisor)) {   // if current is smaller than dividend add zero to quotient
            quotient += "0";
        } 
        else {
            int count = 0;
            while (!isSmaller(current, divisor)) {
                current = AInteger.subtractstr(current, divisor); // subtract divisor from current till it becomes larger than it division as repeated subtraction
                current = remove_front_zeros(current);
                count++;                                        // gives the digit to append the quotient
            }
            quotient += (char)(count + '0'); 
        }
        index++;
    }

    quotient = remove_front_zeros(quotient); // appending it as a char
    return quotient;
}

// DIVISION

public AInteger divide(AInteger other) {

    // Handle division by zero
    if (other.value.equals("0")) {
        throw new ArithmeticException("Division by zero");
    }

    // Remove signs from both numbers
    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    // Calculate the magnitude of the quotient
    String result_str = dividestr(amod, bmod);

    // assign - if signs are opposite and + if same sign
    boolean result_negative = (this.isnegative != other.isnegative) && !result_str.equals("0");

    if (result_negative) {
        result_str = "-" + result_str;
    }

    return new AInteger(result_str);
}

}

