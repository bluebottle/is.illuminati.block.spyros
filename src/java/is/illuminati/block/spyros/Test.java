package is.illuminati.block.spyros;

import is.illuminati.block.spyros.garmin.model.Activity;
import is.illuminati.block.spyros.garmin.model.Lap;
import is.illuminati.block.spyros.garmin.model.Length;
import is.illuminati.block.spyros.garmin.model.Speed.Unit;
import is.illuminati.block.spyros.garmin.parser.TcxParser;
import is.illuminati.block.spyros.garmin.parser.digester.CommonsDigesterTcxParser;
import is.illuminati.block.spyros.garmin.parser.digester.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.idega.util.LocaleUtil;

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
				System.out.println("Net duration: " + DurationFormatUtils.formatDuration(activity.getNetDuration().getMillis(), "H:mm:ss"));
				System.out.println("Average speed: " + format.format(activity.getSpeed().getValue(Unit.KilometersPerHour)) + " km/h");
				System.out.println("Average pace: " + activity.getSpeed().toPace() + " m/km");
				System.out.println("Heart rate: " + activity.getAverageHeartRate() + "/" + activity.getMaximumHeartRate() + " bpm");
				
				System.out.println("---------------------------------");
				
				ImmutableList<Lap>laps = activity.getLaps();
				for (Lap lap : laps) {
					System.out.println("Lap start time: " + dateFormat.format(lap.getStartTime().toDate()));
					System.out.println("Net lap duration: " + DurationFormatUtils.formatDuration(lap.getNetDuration().getMillis(), "H:mm:ss"));
					System.out.println("Lap distance: " + format.format(lap.getDistance().getValue(Length.Unit.Kilometer)) + " km");
					System.out.println("Average lap speed: " + format.format(lap.getSpeed().getValue(Unit.KilometersPerHour)) + " km/h");
					System.out.println("Average lap pace: " + lap.getSpeed().toPace() + " m/km");
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