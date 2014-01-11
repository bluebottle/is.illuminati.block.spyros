package is.illuminati.block.spyros.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Lap.ENTITY_NAME)
public class Lap implements Serializable {

	private static final long serialVersionUID = -313999175118361647L;
	
	protected static final String ENTITY_NAME = "spyros_lap";
	
	private static final String COLUMN_LAP_ID = "lap_id";
	private static final String COLUMN_ACTIVITY = "activity_id";
	private static final String COLUMN_DURATION = "duration";
	private static final String COLUMN_LENGTH = "length";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_LAP_ID)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_ACTIVITY)
	private Activity activity;
	
	@Column(name = COLUMN_DURATION)
	private Integer duration;
	
	@Column(name = COLUMN_LENGTH)
	private Integer length;
}