package org.usfirst.frc.team3668.cameraCalibrate;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3668.cameraCalibrate.GripPipeline;

public class Main {

	public static void main(String[] args) {
		GripPipeline gripPipeline = new GripPipeline();
		int totalContourWidth = 0;
		int counter = 0;
		Mat image;
		image = Imgcodecs.imread("C:\\Users\\Trobots\\Documents\\camera calibrate pictures\\calibrate10.jpeg");
		gripPipeline.process(image);

		for (MatOfPoint contour : gripPipeline.filterContoursOutput()) {

			Rect boundingBox = Imgproc.boundingRect(contour);
//			Imgproc.rectangle(image, new Point(boundingBox.x - 2, boundingBox.y - 2),
//					new Point(boundingBox.x + boundingBox.width + 2,
//							boundingBox.y + boundingBox.height + 2),
//					new Scalar(255, 255, 255), 2);
			totalContourWidth = totalContourWidth + boundingBox.width;
			counter++;
			System.out.println(boundingBox.width);
		}
		System.out.println(2.544834 + 77.97764*Math.pow(Math.E,(-0.03725993*(totalContourWidth/counter))));

	}

}
