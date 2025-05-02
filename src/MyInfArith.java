import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: Minimum 4 arguments are to be passed");
            return;
        }

        String type = args[0];
        String operation = args[1];
        String num1 = args[2];
        String num2 = args[3];

        try {
            if (type.equals("int")) {
                do_integer_operation(operation, num1, num2);
            } 
            else if (type.equals("float")) {
                do_float_operation(operation, num1, num2);
            } 
            else {
                System.out.println("Invalid type. Use 'int' or 'float'.");
            }
        } catch (ArithmeticException e) {
            System.out.println("Division by zero error");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private static void do_integer_operation(String operation, String num1, String num2) {
        AInteger a = new AInteger(num1);
        AInteger b = new AInteger(num2);
        AInteger result = null;

        switch (operation) {
            case "add":
                result = a.add(b);
                break;
            case "sub":
                result = a.subtract(b);
                break;
            case "mul":
                result = a.multiply(b);
                break;
            case "div":
                result = a.divide(b);
                break;
            default:
                System.out.println("Invalid operation for int type.");
                return;
        }

        System.out.println(result.get_value());
    }

    private static void do_float_operation(String operation, String num1, String num2) {
        AFloat a = new AFloat(num1);
        AFloat b = new AFloat(num2);
        AFloat result = null;

        switch (operation) {
            case "add":
                result = a.add(b);
                break;
            case "sub":
                result = a.subtract(b);
                break;
            case "mul":
                result = a.multiply(b);
                break;
            case "div":
                result = a.divide(b);  
                break;
            default:
                System.out.println("Invalid operation for float type.");
                return;
        }

        System.out.println(result.get_value());
    }
}

