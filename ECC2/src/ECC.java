import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.util.*;
// TODO: Auto-generated Javadoc

public class ECC {

    /** The curve. */
    private Curve curve;

    /** The generator. */
    private Point generator;

    /** The public key. */
    private Point publicKey;

    /** The private key. */
    private BigInteger privateKey;

    /**
     * Instantiates a new ecc.
     */
    public ECC()
    {}

    /**
     * Instantiates a new ecc.
     *
     * @param c the c
     * @param x the x
     * @param y the y
     * @param nA the n a
     */
    public ECC(Curve c, BigInteger x, BigInteger y, BigInteger nA) {

        curve = c;
        generator = new Point(curve, x, y);
        privateKey = nA;
        publicKey = generator.Multiply(privateKey);
    }

    /**
     * Encrypt.
     *
     * @param plain the plain
     * @param k the k
     * @return the point[]
     */
    public Point[] encrypt(Point plain, BigInteger k) {		
        Point[] result = new Point[2];
        result[0] = generator.Multiply(k);
        result[1] = plain.Add(publicKey.Multiply(k));
        return result;
    }

    /**
     * Decrypt.
     *
     * @param cipher the cipher
     * @return the point
     */
    public Point decrypt(Point[] cipher) {

        Point sub = cipher[0].Multiply(privateKey);

        return cipher[1].Subtract(sub);
    }

    /**
     * Read input, process input and write ouput
     *
     * @throws FileNotFoundException the file not found exception
     */
    public  void Process() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File("input.txt"));
        FileOutputStream out=new FileOutputStream("output.txt");
        PrintWriter output=new PrintWriter(out,true);//auto flush

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

        output.println(ecc.publicKey.toString());
        output.println(nA);
        C[0] = new Point(mc, new BigInteger(String.valueOf(c11)), new BigInteger(String.valueOf(c12)));
        C[1] = new Point(mc, new BigInteger(String.valueOf(c21)), new BigInteger(String.valueOf(c22)));
        Point P = new Point(mc, new BigInteger(String.valueOf(m1)), new BigInteger(String.valueOf(m2)));

        Point[] cipher = ecc.encrypt(P, bK);
        output.println(cipher[0].toString() + " " + cipher[1].toString());

        Point recover = ecc.decrypt(C);
        output.println(recover.toString());

        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO Auto-generated catch block

        output.close();

    }
    /**
     * The Class Curve.
     */
    public class Curve {

        // The parameters of an EC.
        /** The p. */
        private BigInteger p;

        /** The a. */
        private BigInteger a;

        /** The b. */
        private BigInteger b;

        /**
         * Gets p.
         *
         * @return the p
         */
        public BigInteger getP() {
            return p;
        }

        /**
         * Gets a.
         *
         * @return the a
         */
        public BigInteger getA() {
            return a;
        }

        /**
         * Instantiates a new curve.
         *
         * @param prime the prime
         * @param myA the my a
         * @param myB the my b
         */
        public Curve(BigInteger prime, BigInteger myA, BigInteger myB) {
            p = prime;
            a = myA;
            b = myB;
        }

        /**
         * Copy constructor.
         *
         * @param copy the copy
         */
        public Curve(Curve copy) {
            p = new BigInteger(copy.p.toString());
            a = new BigInteger(copy.a.toString());
            b = new BigInteger(copy.b.toString());	
        }

        /**
         *All three components must be equal for the curves to be the same.
         *
         * @param other the other
         * @return true, if successful
         */
        public boolean equals(Curve other) {
            return p.equals(other.p) && a.equals(other.a) && b.equals(other.b);
        }

    }
    /**
     * The Class Point.
     */
    public class Point {

        /** The x. */
        private BigInteger x;

        /** The y. */
        private BigInteger y;

        /** The curve. */
        private Curve curve;

        /**
         * Instantiates a new point.
         *
         * @param c the c
         * @param myX the my x
         * @param myY the my y
         */
        public Point(Curve c, BigInteger myX, BigInteger myY) {
            x = myX;
            y = myY;
            curve = c;
        }

        // Copy constructor.
        /**
         * Copy constructor.
         *
         * 
         */
        public Point(Point copy) {
            x = new BigInteger(copy.x.toString());
            y = new BigInteger(copy.y.toString());
            curve = new Curve(copy.curve);
        }

        // Returns 0. Not sure if this is the proper way to store the "origin".
        /**
         * Instantiates a new point.
         *
         * @param c the c
         */
        public Point(Curve c) {
            curve = c;
            x = BigInteger.ZERO;
            y = BigInteger.ZERO;
        }

        /**
         * Equals.
         *
         * @param other the other
         * @return true, if successful
         */
        public boolean Equals(Point other) {
            return x.equals(other.x) && y.equals(other.y) && curve.equals(other.curve);
        }

        /**
         * Mirror.
         *
         * @param other the other
         * @return true, if successful
         */
        public boolean Mirror(Point other) {
            return x.equals(other.x) && curve.equals(other.curve) && y.equals(other.curve.getP().subtract(other.y));
        }

        /**
         *  
         *
         * @return the the negative of this point
         */
        public Point Negate() {

            BigInteger newY = curve.getP().subtract(y);
            return new Point(curve, x, newY);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return   x + " "+ y;
        }

        /**
         * Adds this to other and returns the answer
         *
         * @param other the other
         * @return the point
         */
        public Point Add(Point other) {

            // Can't add points on different curves.
            if (!curve.equals(other.curve)) return null;

            if (this.Equals(other)) {

                // We need these to calculate lambda.
                BigInteger three = new BigInteger("3");
                BigInteger two = new BigInteger("2");
                BigInteger temp = new BigInteger(x.toString());

                // Splitting up the calculation of lambda into all of these steps...
                BigInteger lambda = temp.modPow(two, curve.getP());
                lambda = three.multiply(lambda);
                lambda = lambda.add(curve.getA());
                BigInteger den = two.multiply(y);
                lambda = lambda.multiply(den.modInverse(curve.getP()));

                // Once we have lambda, just plug into these equations.
                BigInteger newX = lambda.multiply(lambda).subtract(x).subtract(x).mod(curve.getP());
                BigInteger newY = (lambda.multiply(x.subtract(newX))).subtract(y).mod(curve.getP());
                return new Point(curve, newX, newY);

            }

            // Returns the origin...not sure if my origin is correct.
            else if (this.Mirror(other)) {
                return new Point(curve);
            }

            // Standard case.
            else {

                // We need these to calculate lambda.
                BigInteger three = new BigInteger("3");
                BigInteger two = new BigInteger("2");
                BigInteger temp = new BigInteger(x.toString());

                // Lambda's a bit easier here...
                BigInteger lambda = other.y.subtract(y);
                BigInteger den = other.x.subtract(x);
                lambda = lambda.multiply(den.modInverse(curve.getP()));

                // This calculation is roughly the same as above.
                BigInteger newX = lambda.multiply(lambda).subtract(x).subtract(other.x).mod(curve.getP());
                BigInteger newY = (lambda.multiply(x.subtract(newX))).subtract(y).mod(curve.getP());
                return new Point(curve, newX, newY);			

            }
        }

        /**
         * multiply this point by factor.
         *
         * @param factor the factor
         * @return the point
         */
        public Point Multiply(BigInteger factor) {

            BigInteger two = new BigInteger("2");
            System.out.println("private key"+factor);
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

        /**
         * Subtraction is just adding the negative.
         *
         * @param other the other
         * @return the point
         */
        public Point Subtract(Point other) {
            other = other.Negate();
            return this.Add(other);
        }

    }
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        try {
            ECC ec = new ECC(); 
            ec.Process();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}