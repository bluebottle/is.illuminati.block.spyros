package is.illuminati.block.spyros.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.idega.user.data.bean.User;

@Entity
@Table(name = Activity.ENTITY_NAME)
public class Activity implements Serializable {

	private static final long serialVersionUID = 25829333774988500L;

	protected static final String ENTITY_NAME = "spyros_activity";
	
	private static final String COLUMN_ACTIVITY_ID = "activity_id";
	private static final String COLUMN_OWNER = "owner_id";
	private static final String COLUMN_DATE = "activity_date";
	private static final String COLUMN_DURATION = "duration";
	private static final String COLUMN_LENGTH = "length";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ACTIVITY_ID)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_OWNER)
	private User owner;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_DATE)
	private Date date;
	
	@Column(name = COLUMN_DURATION)
	private Long duration;
	
	@Column(name = COLUMN_LENGTH)
	private Long length;
	
	@OneToMany(mappedBy = "activity")
	private List<Lap> laps;
}