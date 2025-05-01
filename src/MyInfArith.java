import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java MyInfArith [int|float] [add|sub|mul|div] num1 num2");
            return;
        }

        String type = args[0].toLowerCase();
        String operation = args[1].toLowerCase();
        String num1 = args[2];
        String num2 = args[3];

        try {
            if (type.equals("int")) {
                handleIntegerOperation(operation, num1, num2);
            } 
            else if (type.equals("float")) {
                handleFloatOperation(operation, num1, num2);
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

    private static void handleIntegerOperation(String operation, String num1, String num2) {
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
                System.out.println("Invalid operation for int type. Use add, sub, mul, or div.");
                return;
        }

        System.out.println(result.get_value());
    }

    private static void handleFloatOperation(String operation, String num1, String num2) {
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
                result = a.divide(b);  // Now dividing floats is properly implemented
                break;
            default:
                System.out.println("Invalid operation for float type. Use add, sub, mul, or div.");
                return;
        }

        System.out.println(result.get_value());
    }
}

