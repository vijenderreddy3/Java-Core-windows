import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.util.*;
public class ECC {
    public static void main(String[] args) {

        try {
            ECC ec = new ECC(); 
            ec.Process();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ECC()
    {}
    private Point KeyGen;
    private Curve curve;
    private Point PUK;
    private BigInteger PRK;
    public ECC(Curve c, BigInteger x, BigInteger y, BigInteger nA) {
        curve = c;
        PRK = nA;
        PUK = KeyGen.Multiply(PRK);
        KeyGen = new Point(curve, x, y);
    }
    public Point[] encrypt(Point plain, BigInteger k) {		
        Point[] result = new Point[2];
        result[0] = KeyGen.Multiply(k);
        result[1] = plain.Add(PUK.Multiply(k));
        return result;
    }
    public Point decrypt(Point[] cipher_text) {
        Point sub = cipher_text[0].Multiply(PRK);
        return cipher_text[1].Subtract(sub);
    }
    public  void Process() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File("input.txt"));
        FileOutputStream out=new FileOutputStream("output.txt");
        PrintWriter output=new PrintWriter(out,true);
        int q = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();
        Curve mc = new Curve(new BigInteger(String.valueOf(q)), 
                new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)));
        int x = sc.nextInt();
        int y = sc.nextInt();
        int nA = sc.nextInt();
        BigInteger bx = new BigInteger(String.valueOf(x));
        BigInteger by = new BigInteger(String.valueOf(y));
        BigInteger bnA = new BigInteger(String.valueOf(nA));
        ECC ecc = new ECC(mc, bx, by, bnA);
        int m1 = sc.nextInt();
        int m2 = sc.nextInt();
        int k = sc.nextInt();
        BigInteger bK = new BigInteger(Integer.toString(k));
        int c11 = sc.nextInt();
        int c12 = sc.nextInt();
        int c21 = sc.nextInt();
        int c22 = sc.nextInt();
        sc.close();		
        Point[] C = new Point[2];
        output.println(ecc.PUK.toString());
        output.println(nA);
        C[0] = new Point(mc, new BigInteger(String.valueOf(c11)), new BigInteger(String.valueOf(c12)));
        C[1] = new Point(mc, new BigInteger(String.valueOf(c21)), new BigInteger(String.valueOf(c22)));
        Point P = new Point(mc, new BigInteger(String.valueOf(m1)), new BigInteger(String.valueOf(m2)));

        Point[] cipher_text = ecc.encrypt(P, bK);
        output.println(cipher_text[0].toString() + " " + cipher_text[1].toString());
        Point recover = ecc.decrypt(C);
        output.println(recover.toString());
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.close();
    }
    public class Curve {

        private BigInteger p;
        private BigInteger a;
        private BigInteger b;
        public BigInteger getP() {
            return p;
        }
        public BigInteger getA() {
            return a;
        }
        public Curve(BigInteger prime, BigInteger myA, BigInteger myB) {
            p = prime;
            a = myA;
            b = myB;
        }
        public Curve(Curve copy) {
            p = new BigInteger(copy.p.toString());
            a = new BigInteger(copy.a.toString());
            b = new BigInteger(copy.b.toString());	
        }
        public boolean equals(Curve other) {
            return p.equals(other.p) && a.equals(other.a) && b.equals(other.b);
        }
    }
    public class Point {
        private BigInteger x;
        private BigInteger y;
        private Curve curve;
        public Point(Curve c, BigInteger myX, BigInteger myY) {
            x = myX;
            y = myY;
            curve = c;
        }
        public Point(Point copy) {
            x = new BigInteger(copy.x.toString());
            y = new BigInteger(copy.y.toString());
            curve = new Curve(copy.curve);
        }
        public Point(Curve c) {
            curve = c;
            x = BigInteger.ZERO;
            y = BigInteger.ZERO;
        }
        public boolean Equals(Point other) {
            return x.equals(other.x) && y.equals(other.y) && curve.equals(other.curve);
        }
        public boolean Mirror(Point other) {
            return x.equals(other.x) && curve.equals(other.curve) && y.equals(other.curve.getP().subtract(other.y));
        }
        public Point Negate() {

            BigInteger newY = curve.getP().subtract(y);
            return new Point(curve, x, newY);
        }
        public String toString() {
            return   x + " "+ y;
        }
        public Point Add(Point other) {
            if (!curve.equals(other.curve)) return null;
            if (this.Equals(other)) {
                BigInteger three = new BigInteger("3");
                BigInteger two = new BigInteger("2");
                BigInteger temp = new BigInteger(x.toString());
                BigInteger lambda = temp.modPow(two, curve.getP());
                lambda = three.multiply(lambda);
                lambda = lambda.add(curve.getA());
                BigInteger den = two.multiply(y);
                lambda = lambda.multiply(den.modInverse(curve.getP()));
                BigInteger newX = lambda.multiply(lambda).subtract(x).subtract(x).mod(curve.getP());
                BigInteger newY = (lambda.multiply(x.subtract(newX))).subtract(y).mod(curve.getP());
                return new Point(curve, newX, newY);
            }
            else if (this.Mirror(other)) {
                return new Point(curve);
            }
            else {
                                BigInteger lambda = other.y.subtract(y);
                BigInteger den = other.x.subtract(x);
                lambda = lambda.multiply(den.modInverse(curve.getP()));
                BigInteger newX = lambda.multiply(lambda).subtract(x).subtract(other.x).mod(curve.getP());
                BigInteger newY = (lambda.multiply(x.subtract(newX))).subtract(y).mod(curve.getP());
                return new Point(curve, newX, newY);			
            }
        }
        public Point Multiply(BigInteger factor) {
            BigInteger two = new BigInteger("2");
            if (factor.equals(BigInteger.ONE))
                return new Point(this);
            if (factor.equals(two))
                return this.Add(this);
            if (factor.mod(two).equals(BigInteger.ZERO)) {
                Point sqrt = Multiply(factor.divide(two));
                return sqrt.Add(sqrt);
            }
            else {
                factor = factor.subtract(BigInteger.ONE);
                return this.Add(Multiply(factor));
            }
        }
        public Point Subtract(Point other) {
            other = other.Negate();
            return this.Add(other);
        }
    }   
}