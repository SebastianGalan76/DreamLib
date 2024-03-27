package pl.dream.dreamlib.gradient;

@FunctionalInterface
public interface Interpolator {

    double[] interpolate(double from, double to, int max);
}