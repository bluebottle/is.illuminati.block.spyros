package is.illuminati.block.spyros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import nl.iljabooij.garmintrainer.model.Activity;
import nl.iljabooij.garmintrainer.model.Lap;
import nl.iljabooij.garmintrainer.model.Length;
import nl.iljabooij.garmintrainer.model.Speed;
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
				"/Users/laddi/Desktop/Spyros/30.7.2013 17-40-33.tcx");

		Injector injector = Guice.createInjector(new Test());
		TcxParser parser = injector.getInstance(CommonsDigesterTcxParser.class);
		try {
			List<Activity> activities = parser.parse(new FileInputStream(file));

			for (Activity activity : activities) {
				System.out.println("Start time: " + activity.getStartTime().toString());
				System.out.println("Distance: " + activity.getDistance().getValue() + "m");
				
				Duration duration = activity.getNetDuration();
				long hours = duration.getStandardHours();
				long minutes = duration.getStandardMinutes() - (hours * 60);
				long seconds = duration.getStandardSeconds() - (minutes * 60) - (hours * 60 * 60);
				
				System.out.println("Net duration: " + TextSoap.addZero((int) hours) + ":" + TextSoap.addZero((int) minutes) + ":" + TextSoap.addZero((int) seconds));
				System.out.println("Average speed: " + activity.getSpeed().getValue(Unit.KilometersPerHour) + " km/h");
				System.out.println("Average pace: " + activity.getSpeed().toPace());
				
				System.out.println("---------------------------------");
				
				Length previousDistance = null;
				ImmutableList<Lap>laps = activity.getLaps();
				for (Lap lap : laps) {
					System.out.println("Lap start time: " + lap.getStartTime().toString());

					duration = lap.getNetDuration();
					hours = duration.getStandardHours();
					minutes = duration.getStandardMinutes() - (hours * 60);
					seconds = duration.getStandardSeconds() - (minutes * 60) - (hours * 60 * 60);
					
					System.out.println("Net lap duration: " + TextSoap.addZero((int) hours) + ":" + TextSoap.addZero((int) minutes) + ":" + TextSoap.addZero((int) seconds));
					
					Length distance = previousDistance != null ? lap.getDistance().substract(previousDistance) : lap.getDistance();
					Speed speed = Speed.createSpeedInMetersPerSecond(distance, duration);
					
					System.out.println("Lap distance: " + distance.getValue() + "m");
					System.out.println("Average lap speed: " + speed.getValue(Unit.KilometersPerHour) + " km/h");
					System.out.println("Average lap pace: " + speed.toPace());

					System.out.println("---------------------------------");
					
					previousDistance = lap.getDistance();
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