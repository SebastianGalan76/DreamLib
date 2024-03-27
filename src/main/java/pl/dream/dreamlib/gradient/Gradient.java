package pl.dream.dreamlib.gradient;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gradient {
    private final static String GRADIENT_PATTERN = "<&#([0-9A-Fa-f]{6})>([^<]+)</&#([0-9A-Fa-f]{6})>";

    /**
     * Replaces RGB gradient patterns in the provided text with interpolated colors.
     * Pattern: <&#RRGGBB>TEXT</&#RRGGBB>
     *
     * @param text The text to fix RGB gradients.
     * @param interpolator The interpolator to use for color interpolation.
     * @return The text with RGB gradients replaced by interpolated colors.
     */
    public static String fixRGB(String text, Interpolator interpolator){
        Pattern regex = Pattern.compile(GRADIENT_PATTERN);
        Matcher matcher = regex.matcher(text);

        StringBuilder stringBuilder = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            String firstColor = matcher.group(1);
            String gradientText = matcher.group(2);
            String secondColor = matcher.group(3);

            stringBuilder.append(text.substring(lastIndex, matcher.start()));

            Color from = new Color(Integer.parseInt(firstColor, 16));
            Color to = new Color(Integer.parseInt(secondColor, 16));

            gradientText = rgb(gradientText, from, to, interpolator);
            stringBuilder.append(gradientText);

            lastIndex = matcher.end();
        }

        stringBuilder.append(text.substring(lastIndex));
        return stringBuilder.toString();
    }

    public static String rgb(String str, Color from, Color to, Interpolator interpolator) {
        // interpolate each component separately
        final double[] red = interpolator.interpolate(from.getRed(), to.getRed(), str.length());
        final double[] green = interpolator.interpolate(from.getGreen(), to.getGreen(), str.length());
        final double[] blue = interpolator.interpolate(from.getBlue(), to.getBlue(), str.length());

        final StringBuilder builder = new StringBuilder();

        // create a string that matches the input-string but has
        // the different color applied to each char
        for (int i = 0; i < str.length(); i++) {
            builder.append(ChatColor.of(new Color(
                            (int) Math.round(red[i]),
                            (int) Math.round(green[i]),
                            (int) Math.round(blue[i]))))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }
    public static String hsv(String str, Color from, Color to, Interpolator interpolator) {
        // returns a float-array where hsv[0] = hue, hsv[1] = saturation, hsv[2] = value/brightness
        final float[] hsvFrom = Color.RGBtoHSB(from.getRed(), from.getGreen(), from.getBlue(), null);
        final float[] hsvTo = Color.RGBtoHSB(to.getRed(), to.getGreen(), to.getBlue(), null);

        final double[] h = interpolator.interpolate(hsvFrom[0], hsvTo[0], str.length());
        final double[] s = interpolator.interpolate(hsvFrom[1], hsvTo[1], str.length());
        final double[] v = interpolator.interpolate(hsvFrom[2], hsvTo[2], str.length());

        final StringBuilder builder = new StringBuilder();

        for (int i = 0 ; i < str.length(); i++) {
            builder.append(ChatColor.of(Color.getHSBColor((float) h[i], (float) s[i], (float) v[i]))).append(str.charAt(i));
        }
        return builder.toString();
    }

    /**
     *  Checks if the provided text contains a gradient pattern.
     *  Pattern: <&#RRGGBB>TEXT</&#RRGGBB>
     *
     * @param text the text to check.
     * @return true if the provided text contains a gradient pattern, otherwise false.
     */
    public static boolean hasGradient(String text){
        Pattern regex = Pattern.compile(GRADIENT_PATTERN);
        Matcher matcher = regex.matcher(text);

        return matcher.find();
    }
}
