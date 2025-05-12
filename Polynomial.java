public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[0];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int length = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = 0;

            int this_len = this.coefficients.length;
            int other_len = other.coefficients.length;

            if (this_len > i) {
                result[i] += this.coefficients[i];
            }
            if (other_len > i) {
                result[i] += other.coefficients[i];
            }
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;
        int len = this.coefficients.length;

        for (int i = 0; i < len; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
}