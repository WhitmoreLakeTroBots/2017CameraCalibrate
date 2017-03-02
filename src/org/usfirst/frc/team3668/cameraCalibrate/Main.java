package org.usfirst.frc.team3668.cameraCalibrate;

import java.io.Console;
import java.io.IOException;

import javax.swing.BoundedRangeModel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
	static GripPipeline gripPipeline;
	static FrameGrabber frameGrabber;
	static Mat image;
	static int imageCounter;

	public static void main(String[] args) {
		gripPipeline = new GripPipeline();
		frameGrabber = new FrameGrabber();
		// int character;
		frameGrabber.start(0, 640, 480);
		imageCounter = 1;
		image = null;
		String keyInput;
		Console console = System.console();
		// image = Imgcodecs.imread("C:\\Users\\Trobots\\Documents\\camera
		// calibrate pictures\\calibrate10.jpeg");

		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in)); // Here
		// you
		// declare
		// your
		// BufferedReader
		// object
		// and
		// instance
		// it.

		while (true) {
			keyInput = "";
			keyInput = console.readLine("Enter s to save image, p to process, b to do both, x to end: ");

			if (keyInput.startsWith("p")) {
				processImage(false);
			} else if (keyInput.startsWith("s")) {
				processImage(true);
			} else if (keyInput.startsWith("x")) {
				break;

			}
			if (image != null){
				image.release();
			}
		}

	}

	public static void processImage(boolean saveImage) {

		image = frameGrabber.getLastImage();
		if (image != null) {
			int totalContourWidth = 0;
			int counter = 0;
			gripPipeline.process(image);
			for (MatOfPoint contour : gripPipeline.filterContoursOutput()) {

				Rect boundingBox = Imgproc.boundingRect(contour);
				// Imgproc.rectangle(image, new Point(boundingBox.x - 2,
				// boundingBox.y - 2),
				// new Point(boundingBox.x + boundingBox.width + 2,
				// boundingBox.y + boundingBox.height + 2),
				// new Scalar(255, 255, 255), 2);
				totalContourWidth = totalContourWidth + boundingBox.width;
				counter++;
				System.out.println(boundingBox.width);
				
			}
			if (counter == 0) {
				System.out.println("Contour not found");
			} else {
				System.out.print("Distance to target ");
				System.out
						.println(2.544834 + 77.97764 * Math.pow(Math.E, (-0.03725993 * (totalContourWidth / counter))));
				
			}
			if (saveImage) {
				Imgcodecs.imwrite(
						"C:\\temp\\visionSample" + imageCounter + ".jpeg",
						image);
				imageCounter++;
			}
		}
	}

}
