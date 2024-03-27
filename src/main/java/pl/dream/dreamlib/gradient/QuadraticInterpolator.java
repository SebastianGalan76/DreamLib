package pl.dream.dreamlib.gradient;

public class QuadraticInterpolator implements Interpolator {
    private final boolean mode;

    public QuadraticInterpolator(boolean mode) {
        this.mode = mode;
    }

    // mode == true: starts of "slow" and becomes "faster", see the orange curve
    // mode == false: starts of "fast" and becomes "slower", see the yellow curve
    private double[] quadratic(double from, double to, int max) {
        final double[] results = new double[max];
        if (mode) {
            double a = (to - from) / (max * max);
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + from;
            }
        } else {
            double a = (from - to) / (max * max);
            double b = - 2 * a * max;
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + b * i + from;
            }
        }
        return results;
    }

    @Override
    public double[] interpolate(double from, double to, int max) {
        return quadratic(from, to, max);
    }
}
