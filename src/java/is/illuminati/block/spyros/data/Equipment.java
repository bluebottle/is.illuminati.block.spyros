package is.illuminati.block.spyros.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.idega.user.data.bean.User;

@Entity
@Table(name = Equipment.ENTITY_NAME)
public class Equipment implements Serializable {

	private static final long serialVersionUID = 6283399897898899195L;

	protected static final String ENTITY_NAME = "spyros_equipment";
	
	private static final String COLUMN_EQUIPMENT_ID = "equipment_id";
	private static final String COLUMN_OWNER = "owner_id";
	private static final String COLUMN_CREATED_DATE = "created_date";
	private static final String COLUMN_INITIAL_MILEAGE = "mileage";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_EQUIPMENT_ID)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_OWNER)
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_CREATED_DATE)
	private Date createdDate;
	
	@Column(name = COLUMN_INITIAL_MILEAGE)
	private Integer initialMileage;
}