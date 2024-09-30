import vn.edu.tdtu.*;

class Program {
    public static void main(String[] args) {
        int[] a = {3, 1, 4, -2, 7};
        int[] b = {12, 31, -17};

        ArrayOutput.print(a);
        ArrayOutput.print(b);

        int[] c = ArrayHandler.merge(a, b);
        ArrayHandler.sort(c);
        ArrayOutput.print(c);
        ArrayOutput.write(c, "output");
    }
}