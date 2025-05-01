/*This is the AFloat class in a package called arbitraryarithmetic.
 * It is being used for arbitrary arithmetic p[recison on float numbers.
 * The floats are being represented as strings
 */
package arbitraryarithmetic;

public class AFloat {
    private String value;

    // an instance variable to keep sign
    private boolean isnegative;

    // default constructor initialises to "0.0"
    public AFloat() {
        this.value = "0.0";
        this.isnegative = false;
    }

    // constructor to instantiate
    public AFloat(String s) {

        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input should not be empty");
        }
    
        s = s.trim();

        if (s.equals("-")) {
            throw new IllegalArgumentException("Invalid input: '-' given");
        }
        // removing sign

        if (s.charAt(0) == '-') {
            isnegative = true;
            s = s.substring(1);
        }
    
        if (s.isEmpty() || s.equals(".")) {
            throw new IllegalArgumentException("Invalid input after removing '-'");
        }
        // checking if each character is a digit 
        int dot_count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                continue; 
            } 
            else if (c == '.') {
                dot_count++;
                if (dot_count > 1) {
                    throw new IllegalArgumentException("Invalid : multiple decimal points"); // more than one decimal 
                }
            } else {
                throw new IllegalArgumentException("Invalid character:  " + c);
            }
        }
    
        if (dot_count == 0) {
            s += ".0";
        }
    
        if (s.startsWith(".")) {
            s = "0" + s;
        }
        if (s.endsWith(".")) {
            s = s + "0";
        }
    
        s = remove_front_zeros(s);
        s = remove_end_zeros(s);
    
        if (s.equals("0.0")) {
            isnegative = false; 
        }
        // reassigning sign
        this.value = isnegative ? "-" + s : s;
    }
    
    // copy constructor 
    public AFloat(AFloat obj){
          this.value = obj.value;
          this.isnegative = obj.isnegative;
     }
      
    public static AFloat parse(String s){
            return new AFloat(s);
     }
     
     // getter methods
     public String get_value() {
        return this.value;
    }
    public boolean get_isnegative(){
        return this.isnegative;
    }

//  Helper Functions 

// Mehod to remove leading zeros
public static String remove_front_zeros(String s) {
    if (s == null || s.isEmpty()) return "0";

    boolean isnegative = s.startsWith("-");
    if (isnegative) {
        s = s.substring(1);
    }

    int i = 0;
    while (i < s.length() && (s.charAt(i) == '0')) {
        if (i + 1 < s.length() && s.charAt(i + 1) == '.') break; 
        i++;
    }

    s = s.substring(i);
    if (s.isEmpty()) s = "0.0";

    return isnegative ? "-" + s : s;
}

// method to remove trailing zeros 

public static String remove_end_zeros(String s) {
    if (s == null || s.isEmpty()) return "0.0";

    // adds a decimal point 
    if (!s.contains(".")) return s + ".0";

    int i = s.length() - 1;
    while (i > 0 && s.charAt(i) == '0') {
        i--;
    }

    if (s.charAt(i) == '.') {
        i--;
    }
    s = s.substring(0, i + 1);
    if (!s.contains(".")) {
        s += ".0";  
    }

    if (s.isEmpty() || s.equals("-")) return "0.0";
    return s;
}

// Method to align both strings to same length 
private static String[] pad_float(String a, String b) {
    if (!a.contains(".")) {
        a += ".0";
    }
    if (!b.contains(".")) {
        b += ".0";
    }
    // separate fractional and integer parts
    String[] a_parts = a.split("\\.");
    String[] b_parts = b.split("\\.");

    String int_part1 = a_parts[0];
    String frac_part1 = a_parts.length > 1 ? a_parts[1] : "0";
    String int_part2 = b_parts[0];
    String frac_part2 = b_parts.length > 1 ? b_parts[1] : "0";

    // adding leading zeros
    while (int_part1.length() < int_part2.length()) {
        int_part1 = "0" + int_part1;
    }
    while (int_part2.length() < int_part1.length()) {
        int_part2 = "0" + int_part2;
    }
    // adding trailing zeros
    while (frac_part1.length() < frac_part2.length()) {
        frac_part1 += "0";
    }
    while (frac_part2.length() < frac_part1.length()) {
        frac_part2 += "0";
    }

    String final_1 = int_part1 + frac_part1;
    String final_2 = int_part2 + frac_part2;

    // an array to store the padded strings and number of decimal places
    return new String[] {final_1, final_2, String.valueOf(frac_part1.length())};
}
 
private static String insert_decimalpoint(String s, int decimal_places) {
    if (s.length() <= decimal_places) {
        s = "0".repeat(decimal_places - s.length() + 1) + s;
    }
    int index = s.length() - decimal_places;
    return s.substring(0, index) + "." + s.substring(index);
}

// Method returns true if a  is smaller 
private static boolean is_smaller(String a, String b) {
    
    for (int i = 0; i < a.length(); i++) {
        if (a.charAt(i) < b.charAt(i)) return true;
        if (a.charAt(i) > b.charAt(i)) return false;
    }
    return false; 
}

// tMethod to truncate 

public static String truncate(String result) {
    if (result == null || result.isEmpty()) return "0.0";
    if (!result.contains(".")) return result + ".0";

    String[] parts = result.split("\\.");
    String intPart = parts[0];
    String fracPart = parts.length > 1 ? parts[1] : "";
    
    // if decimal part less than 30 appends zeros and if more than it truncates to first 30 digits.
    while (fracPart.length() < 30) fracPart += "0";
    if (fracPart.length() > 30) fracPart = fracPart.substring(0, 30);

    return intPart + "." + fracPart;
}

// MAIN ARITHMETIC OPERATIONS 

// ADDITION 

public AFloat add(AFloat other){
    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    String[] padded = pad_float(amod, bmod);
    String a_normal = padded[0];
    String b_normal= padded[1];
    int decimal_places = Integer.parseInt(padded[2]);

    String result_str;

    // Both are of same sign 

    if (this.isnegative == other.isnegative) {

        result_str = AInteger.addstr(a_normal, b_normal);
        result_str = insert_decimalpoint(result_str, decimal_places);
        result_str = remove_end_zeros(result_str);
        result_str = remove_front_zeros(result_str);
        result_str = truncate(result_str);

        String final_result_str = (this.isnegative ? "-" : "") + result_str;
        AFloat result = new AFloat(final_result_str);
        return result;

    } 
    // Both are of different sign

    else{
        boolean a_bigger = !is_smaller(a_normal, b_normal);
        result_str = a_bigger ?
            AInteger.subtractstr(a_normal, b_normal) :
            AInteger.subtractstr(b_normal, a_normal);

        result_str = insert_decimalpoint(result_str, decimal_places);
        result_str = remove_end_zeros(result_str);
        result_str = remove_front_zeros(result_str);
        result_str = truncate(result_str);

        boolean result_negative = result_str.equals("0.0") ? false :
                           (a_bigger ? this.isnegative : other.isnegative);
        String final_result_str = (result_negative ? "-" : "") + result_str;

        AFloat result = new AFloat(final_result_str);
        return result;

    }    
}   
   
// SUBTRACTION 

public AFloat subtract(AFloat other) {

    String amod= this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    String[] padded = pad_float(amod, bmod);
    String a_normal = padded[0];
    String b_normal = padded[1];
    int decimal_places = Integer.parseInt(padded[2]);

    String result_str;

// Both are of same sign 

    if (this.isnegative == other.isnegative) {
        boolean a_bigger = !is_smaller(a_normal, b_normal);

        result_str = a_bigger ?
            AInteger.subtractstr(a_normal, b_normal) :
            AInteger.subtractstr(b_normal, a_normal);

        result_str = insert_decimalpoint(result_str, decimal_places);
        result_str = remove_end_zeros(result_str);
        result_str = remove_front_zeros(result_str);
        result_str = truncate(result_str);

        boolean result_negative = result_str.equals("0.0") ? false :
                           (a_bigger ? this.isnegative : !other.isnegative);
        String final_result_str = (result_negative ? "-" : "") + result_str;

        AFloat result = new AFloat(final_result_str);
        return result;
    } 
    
// Both are of different sign 
    
    else {

        result_str = AInteger.addstr(a_normal, b_normal);
        result_str = insert_decimalpoint(result_str, decimal_places);
        result_str = remove_end_zeros(result_str);
        result_str = remove_front_zeros(result_str);
        result_str = truncate(result_str);

        String final_result_str = (this.isnegative ? "-" : "") + result_str;

        AFloat result = new AFloat(final_result_str);
        return result;

    }
}
  
private static String remove_decimal(String s) {
    return s.replace(".", "");
}

// MULTIPLICATION 

public AFloat multiply(AFloat other) {

    String amod = this.value.replace("-", "");
    String bmod = other.value.replace("-", "");

    String a_total = remove_decimal(amod);
    String b_total = remove_decimal(bmod);

    int decimal_places_a = amod.length() - amod.indexOf('.') - 1;
    int decimal_places_b = bmod.length() - bmod.indexOf('.') - 1;

    int total_decimal_places = decimal_places_a + decimal_places_b;

    String result_total = AInteger.multiplystr(a_total, b_total);

    String result_with_decimal = insert_decimalpoint(result_total, total_decimal_places);

    result_with_decimal = remove_end_zeros(result_with_decimal);
    result_with_decimal = remove_front_zeros(result_with_decimal);
    result_with_decimal = truncate(result_with_decimal);

    boolean result_negative = (this.isnegative != other.isnegative) && !result_with_decimal.equals("0.0");
    String final_result = (result_negative ? "-" : "") + result_with_decimal;

    AFloat result = new AFloat(final_result);
    return result;
}

// DIVISION 

public AFloat divide(AFloat other) {
    if (other.value.equals("0.0")) {
        throw new ArithmeticException("Division by zero");
    }

    String a = this.value.replace("-", "");
    String b = other.value.replace("-", "");

    // Remove decimal and track digits after decimal
    int dec_a = a.length() - a.indexOf('.') - 1;
    int dec_b = b.length() - b.indexOf('.') - 1;
    String dividend = a.replace(".", "");
    String divisor = b.replace(".", "");

    // Append zeros for precision instead of hardcoded 50
    int desired_precision = 60;
    for (int i = 0; i < desired_precision; i++) {
        dividend += "0";
    }

    // Calculate correct decimal position
    int result_decimal_places = dec_a - dec_b + desired_precision;

    // Perform integer division
    String result_raw = AInteger.dividestr(dividend, divisor);

    // Insert decimal using existing helper
    String result = insert_decimalpoint(result_raw, result_decimal_places);
    
    // Use existing cleanup methods
    result = remove_end_zeros(result);
    result = remove_front_zeros(result);
    result = truncate(result);

    boolean isNegative = this.isnegative != other.isnegative && !result.equals("0.0");
    if (isNegative) result = "-" + result;
    return new AFloat(result);
}

}