package is.illuminati.block.spyros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.NumberFormat;
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
import com.idega.util.LocaleUtil;
import com.idega.util.text.TextSoap;

public class Test extends AbstractModule {

	@Override
	protected void configure() {
	}

	public static void main(String[] args) {
		File file = new File(
				"/Users/laddi/Desktop/Spyros/17.1.2013 18-54-12.tcx");

		Injector injector = Guice.createInjector(new Test());
		TcxParser parser = injector.getInstance(CommonsDigesterTcxParser.class);
		try {
			List<Activity> activities = parser.parse(new FileInputStream(file));
			
			NumberFormat format = NumberFormat.getInstance(LocaleUtil.getIcelandicLocale());
			format.setMaximumFractionDigits(1);
			
			DateFormat dateFormat = DateFormat.getInstance();

			for (Activity activity : activities) {
				System.out.println("Start time: " + dateFormat.format(activity.getStartTime().toDate()));
				System.out.println("Distance: " + format.format(activity.getDistance().getValue(Length.Unit.Kilometer)) + " km");
				
				Duration duration = activity.getNetDuration();
				long hours = duration.getStandardHours();
				long minutes = duration.getStandardMinutes() - (hours * 60);
				long seconds = duration.getStandardSeconds() - (minutes * 60) - (hours * 60 * 60);
				
				System.out.println("Net duration: " + TextSoap.addZero((int) hours) + ":" + TextSoap.addZero((int) minutes) + ":" + TextSoap.addZero((int) seconds));
				System.out.println("Average speed: " + format.format(activity.getSpeed().getValue(Unit.KilometersPerHour)) + " km/h");
				System.out.println("Average pace: " + activity.getSpeed().toPace() + " m/km");
				System.out.println("Heart rate: " + activity.getAverageHeartRate() + "/" + activity.getMaximumHeartRate() + " bpm");
				
				System.out.println("---------------------------------");
				
				ImmutableList<Lap>laps = activity.getLaps();
				for (Lap lap : laps) {
					System.out.println("Lap start time: " + dateFormat.format(lap.getStartTime().toDate()));

					duration = lap.getNetDuration();
					hours = duration.getStandardHours();
					minutes = duration.getStandardMinutes() - (hours * 60);
					seconds = duration.getStandardSeconds() - (minutes * 60) - (hours * 60 * 60);
					
					System.out.println("Net lap duration: " + TextSoap.addZero((int) hours) + ":" + TextSoap.addZero((int) minutes) + ":" + TextSoap.addZero((int) seconds));
					
					Length distance = lap.getDistance();
					Speed speed = Speed.createSpeedInMetersPerSecond(distance, duration);
					
					System.out.println("Lap distance: " + format.format(distance.getValue(Length.Unit.Kilometer)) + " km");
					System.out.println("Average lap speed: " + format.format(speed.getValue(Unit.KilometersPerHour)) + " km/h");
					System.out.println("Average lap pace: " + speed.toPace() + " m/km");
					System.out.println("Lap heart rate: " + lap.getAverageHeartRate() + "/" + lap.getMaximumHeartRate() + " bpm");

					System.out.println("---------------------------------");
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