import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(File file) throws FileNotFoundException, IOException {
        String str_in;

        BufferedReader input = new BufferedReader(new FileReader(file));
        str_in = input.readLine();
        
        str_in = str_in.replace("-", "+-");

        if (str_in.charAt(0) == '+') {
            str_in = str_in.substring(1);
        }

        String[] myArray = str_in.split("\\+");
        HashMap<Integer, Double> polys = new HashMap<>();
        int maxDegree = 0;
        int terms = myArray.length;
        
        for (String term : myArray) {
            double coeff;
            int exp;

            if (term.indexOf('x') == -1) {
                coeff = Double.parseDouble(term);
                exp = 0;
            }
            else {
                String[] parts = term.split("x");
                
                if (parts.length == 0 || parts[0].isEmpty()) {
                    coeff = 1.0;
                    exp = 1;
                } else if (parts.length == 1 && parts[0].equals("-")) {
                    coeff = -1.0;
                    exp = 1;
                } else if (parts.length == 1) {
                    coeff = Double.parseDouble(parts[0]);
                    exp = 1;
                } else {
                    coeff = Double.parseDouble(parts[0]);
                    exp = Integer.parseInt(parts[1]);  
                }
                
                maxDegree = Math.max(maxDegree, exp);
            }

            polys.put(exp, polys.getOrDefault(exp, 0.0) + coeff);
        }

        double[] coeff_list = new double[terms];
        int[] exp_list = new int[terms];

        int num = 0;
        for (int idx = 0; idx < terms; idx++) {
            while (polys.containsKey(num) == false) {
                num++;

                if (num > maxDegree) {
                    break;
                }
            }
            
            coeff_list[idx] = polys.get(num);
            exp_list[idx] = num;
            num++;
        }

        this.coefficients = coeff_list;
        this.exponents = exp_list;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial other) {
        HashMap<Integer, Double> map = new HashMap<>();
        int maxDegree = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            map.put(this.exponents[i], this.coefficients[i]);
            maxDegree = Math.max(maxDegree, this.exponents[i]);
        }
        for (int i = 0; i < other.coefficients.length; i++) {
            if (map.containsKey(other.exponents[i])) {
                map.put(other.exponents[i], map.get(other.exponents[i]) + other.coefficients[i]);
            } else {
                map.put(other.exponents[i], other.coefficients[i]);
            }
            maxDegree = Math.max(maxDegree, other.exponents[i]);
        }

        double[] tempCoefficients = new double[maxDegree + 1];
        int[] tempExponents = new int[maxDegree + 1];
        for (int i = 0; i < tempCoefficients.length; i++) {
            tempCoefficients[i] = 0;
        }

        int num = 0;
        for (int idx = 0; idx < tempCoefficients.length; idx++) {
            while (map.containsKey(num) == false) {
                num++;
                if (num > maxDegree) {
                    break;
                }
            }

            tempCoefficients[idx] = map.get(num);
            tempExponents[idx] = num;
            num++;
        }

        int len = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            if (tempCoefficients[i] != 0) {
                len++;
            }
        }

        double[] resultCoefficients = new double[len];
        int[] resultExponents = new int[len];

        int idx = 0;
        for (int i = 0; i < tempCoefficients.length; i++) {
            if (tempCoefficients[i] != 0) {
                resultExponents[idx] = tempExponents[i];
                resultCoefficients[idx] = tempCoefficients[i];
                idx++;
            }
        }

        return new Polynomial(resultCoefficients, resultExponents);
    }

    public double evaluate(double x) {
        double result = 0;
        int len = this.coefficients.length;

        for (int i = 0; i < len; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0.0;
    }

    public Polynomial multiply(Polynomial other) {
        HashMap<Integer, Double> map = new HashMap<>();
        int maxDegree = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int newExp = this.exponents[i] + other.exponents[j];
                double newCoeff = this.coefficients[i] * other.coefficients[j];

                if (map.containsKey(newExp)) {
                    map.put(newExp, map.get(newExp) + newCoeff);
                } else {
                    map.put(newExp, newCoeff);
                }

                maxDegree = Math.max(maxDegree, newExp);
            }
        }

        double[] tempCoefficients = new double[maxDegree + 1];
        int[] tempExponents = new int[maxDegree + 1];

        for (int i = 0; i < tempCoefficients.length; i++) {
            tempCoefficients[i] = 0;
        }

        int num = 0;
        for (int idx = 0; idx < tempCoefficients.length; idx++) {
            while (map.containsKey(num) == false) {
                num++;
                if (num > maxDegree) {
                    break;
                }
            }

            if (num <= maxDegree && map.containsKey(num)) {
                tempCoefficients[idx] = map.get(num);
                tempExponents[idx] = num;
                num++;
            }
        }

        int len = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            if (tempCoefficients[i] != 0) {
                len++;
            }
        }

        double[] resultCoefficients = new double[len];
        int[] resultExponents = new int[len];

        int idx = 0;
        for (int i = 0; i < tempCoefficients.length; i++) {
            if (tempCoefficients[i] != 0) {
                resultExponents[idx] = tempExponents[i];
                resultCoefficients[idx] = tempCoefficients[i];
                idx++;
            }
        }

        return new Polynomial(resultCoefficients, resultExponents);
    }

    public void saveToFile(String filename) throws FileNotFoundException {
        String result = "";

        int len = this.coefficients.length;

        for (int i = 0; i < len; i++) {
            double coeff = this.coefficients[i];
            int exp = this.exponents[i];

            // prob won't happen, but just in case
            if (coeff == 0) {
                continue;
            }
            
            if (exp == 0) {
                if (coeff < 0) {
                    result += coeff;
                } else {
                    result += "+" + coeff;
                }
            } else if (exp == 1) {
                if (coeff == -1) {
                    result += "-x";
                } else if (coeff < 0) {
                    result += coeff + "x";
                } else if (coeff == 1) {
                    result += "+x";
                } else {
                    result += "+" + coeff + "x";
                }
            } else {
                if (coeff == -1) {
                    result += "-x" + exp;
                } else if (coeff < 0) {
                    result += coeff + "x" + exp;
                } else if (coeff == 1) {
                    result += "+x" + exp;
                } else {
                    result += "+" + coeff + "x" + exp;
                }
            }
        }

        if (result.startsWith("+")) {
            result = result.substring(1);
        }

        PrintStream ps = new PrintStream(filename);
        ps.println(result);
        ps.close();
    }
}