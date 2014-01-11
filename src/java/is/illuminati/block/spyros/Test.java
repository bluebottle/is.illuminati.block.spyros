package is.illuminati.block.spyros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import nl.iljabooij.garmintrainer.model.Activity;
import nl.iljabooij.garmintrainer.model.Lap;
import nl.iljabooij.garmintrainer.model.Speed.Unit;
import nl.iljabooij.garmintrainer.parser.TcxParser;
import nl.iljabooij.garmintrainer.parser.digester.CommonsDigesterTcxParser;
import nl.iljabooij.garmintrainer.parser.digester.ParseException;

import org.joda.time.Duration;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.idega.util.text.TextSoap;

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
				System.out.println("Distance: " + activity.getDistance().getValue() + "m");
				
				Duration duration = activity.getGrossDuration();
				long hours = duration.getStandardHours();
				long minutes = duration.getStandardMinutes() - (hours * 60);
				long seconds = duration.getStandardSeconds() - (minutes * 60) - (hours * 60 * 60);
				
				System.out.println("Gross duration: " + TextSoap.addZero((int) hours) + ":" + TextSoap.addZero((int) minutes) + ":" + TextSoap.addZero((int) seconds));
				System.out.println("Average speed: " + activity.getSpeed().getValue(Unit.KilometersPerHour) + " km/h");
				System.out.println("Average pace: " + activity.getSpeed().toPace());
				
				System.out.println("---------------------------------");
				
				ImmutableList<Lap>laps = activity.getLaps();
				for (Lap lap : laps) {
					System.out.println("Lap start time: " + lap.getStartTime().toString());
					System.out.println("Lap gross duration: " + lap.getGrossDuration().getStandardSeconds() + "s");
					System.out.println("Lap distance: " + lap.getDistance().getValue() + "m");
					System.out.println("Average lap speed: " + lap.getSpeed().getValue(Unit.KilometersPerHour) + " km/h");
					System.out.println("Average lap pace: " + lap.getSpeed().toPace());
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