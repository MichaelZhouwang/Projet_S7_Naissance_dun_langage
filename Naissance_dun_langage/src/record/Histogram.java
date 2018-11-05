package record;

import record.standardLibraries.*;

public class Histogram {

	double[] result;

	// Create a new histogram.
	public Histogram(double[] vResult) {
		result = vResult;
	}

	// draw (and scale) the histogram.
	public void draw() {
		// to leave a little border
		// StdDraw.setCanvasSize(500, 100);
		// StdDraw.setYscale(-1, 1);
		StdStats.plotBars(result);
		StdDraw.text(1, 0.9, "Testing");
		// StdStats.plotLines(result);
		// StdStats.plotPoints(result);
	}
	
}
