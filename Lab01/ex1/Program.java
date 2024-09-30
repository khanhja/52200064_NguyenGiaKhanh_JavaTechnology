import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Program {
    public static void main(String[] args) {
        if (args.length != 3)
            System.out.print("Invalid expression");
        else {
            String data = String.join(" ", args);
            if (isValidInput(data)) {
                int firstSpace = data.indexOf(' ');
                double x = Double.parseDouble(data.substring(0, firstSpace));
                double y = Double.parseDouble(data.substring(firstSpace+3, data.length()));

                if (data.contains("+"))
                    System.out.print(x+y);
                else if (data.contains("-"))
                    System.out.print(x-y);
                else if (data.contains("x"))
                    System.out.print(x*y);
                else if (data.contains("/"))
                    System.out.print(x/y);
                else if (data.contains("^"))
                    System.out.print(Math.pow(x, y));
                else
                    System.out.println("Unsupported operator");
            }
            else
                System.out.print("Invalid expression");
        }
    }

    public static boolean isValidInput(String data) {
        // Input must follow this format: <number> <operator> <number>
        String regex = "^\\d+(\\.\\d+)?\\s.\\s\\d+(\\.\\d+)?$";
        Pattern patt = Pattern.compile(regex);
        Matcher matc = patt.matcher(data);

        return matc.matches(); 
    }
}