package is.illuminati.block.spyros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import nl.iljabooij.garmintrainer.model.Activity;
import nl.iljabooij.garmintrainer.model.Lap;
import nl.iljabooij.garmintrainer.model.TrackPoint;
import nl.iljabooij.garmintrainer.parser.TcxParser;
import nl.iljabooij.garmintrainer.parser.digester.CommonsDigesterTcxParser;
import nl.iljabooij.garmintrainer.parser.digester.ParseException;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Test extends AbstractModule {

	@Override
	protected void configure() {
	}

	public static void main(String[] args) {
		File file = new File(
				"/Users/laddi/Desktop/Spyros/9.1.2014 20-03-15.tcx");

		Injector injector = Guice.createInjector(new Test());
		TcxParser parser = injector.getInstance(CommonsDigesterTcxParser.class);
		try {
			List<Activity> activities = parser.parse(new FileInputStream(file));

			for (Activity activity : activities) {
				System.out.println("Start time: " + activity.getStartTime().toString());
				System.out.println("End time: " + activity.getEndTime().toString());
				System.out.println("Distance: " + activity.getDistance().getValue());
				System.out.println("Gross duration: " + activity.getGrossDuration().getStandardSeconds());
				System.out.println("Net duration: " + activity.getNetDuration().getStandardSeconds());
				System.out.println("Altitude gain: " + activity.getAltitudeGain().getValueInMeters() + "m");
				
				ImmutableList<Lap>laps = activity.getLaps();
				for (Lap lap : laps) {
					System.out.println("Lap start time: " + lap.getStartTime().toString());
					System.out.println("Lap end time: " + lap.getEndTime().toString());
					System.out.println("Lap gross duration: " + lap.getGrossDuration().getStandardSeconds());
					System.out.println("Lap net duration: " + lap.getNetDuration().getStandardSeconds());
				}
				
				/*ImmutableList<TrackPoint> points = activity.getTrackPoints();
				for (TrackPoint trackPoint : points) {
					System.out.println("Trackpoint: " + trackPoint.toString());
				}*/
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}
}