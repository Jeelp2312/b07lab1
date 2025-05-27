import java.io.*;
public class Driver {
    public static void main(String [] args) throws IOException {
        Polynomial p0 = new Polynomial();
        System.out.println("p0.evaluate(2) = " + p0.evaluate(2));  // Expected: 0.0

        double[] c1 = {6, -2, 5};
        int[] e1 = {0, 1, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        System.out.println("p1.evaluate(2) = " + p1.evaluate(2));  // Expected: 6 - 4 + 40 = 42.0

        double[] c2 = {1, 3};
        int[] e2 = {1, 2};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial sum = p1.add(p2);
        System.out.println("sum.evaluate(1) = " + sum.evaluate(1));  // Expected: 6 - 2 + 5 + 1 + 3 = 13.0

        Polynomial r = new Polynomial(new double[]{-1, 1}, new int[]{0, 1}); 
        System.out.println("r.hasRoot(1) = " + r.hasRoot(1));  // Expected: true

        Polynomial product = p1.multiply(p2);
        System.out.println("product.evaluate(1) = " + product.evaluate(1));  // Expected: (6(1) - 2(1) + 5(1^3)) * (1(1) + 3(1^2)) = 6 - 2 + 5 = 9, then 9 * (1 + 3) = 36.0

        PrintWriter writer = new PrintWriter("poly.txt");
        writer.println("4.5-2.25x+3.1x2-0.1x4");
        writer.close();
        Polynomial fromFile = new Polynomial(new File("poly.txt"));
        System.out.println("fromFile.evaluate(1) = " + fromFile.evaluate(1));  // Expected: 4.5 - 2.25 + 3.1 - 0.1 = 5.25

        fromFile.saveToFile("poly_saved.txt");
        Polynomial loaded = new Polynomial(new File("poly_saved.txt"));
        System.out.println("loaded.evaluate(1) = " + loaded.evaluate(1));  // Expected: 5.25

        System.out.println("All tests done.");
    }
}