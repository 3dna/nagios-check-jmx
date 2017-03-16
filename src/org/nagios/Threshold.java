package org.nagios;

/**
 * Created by jeremy on 3/16/17.
 */
public class Threshold {
    private String threshold;
    private boolean thresholdDouble;
    private boolean thresholdInverted;
    private double min = 0.0d;
    private double max = Double.POSITIVE_INFINITY;

    public Threshold(String threshold) {
        this.threshold = threshold;
        if (threshold.startsWith("@"))
            thresholdInverted = true;
        if (threshold.contains("."))
            thresholdDouble = true;
        if (threshold.contains(":")) {
            String[] ends = threshold.split(":");
            if (ends.length == 1) {
                min = Double.parseDouble(ends[0]);
            } else {
                if (ends[0] == "~") {
                    min = Double.NEGATIVE_INFINITY;
                } else {
                    min = Double.parseDouble(ends[0]);
                }
                max = Double.parseDouble(ends[1]);
            }
        } else {
            max = Double.parseDouble(threshold);
        }
    }

    public boolean isAlert(Double value) {
        boolean outsideRange = (value < min || value > min);
        if (thresholdInverted)
            return !outsideRange;
        return outsideRange;
    }

    public boolean isThresholdDouble() {
        return thresholdDouble;
    }
}
