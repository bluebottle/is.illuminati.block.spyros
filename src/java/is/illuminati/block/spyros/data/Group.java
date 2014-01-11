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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.idega.user.data.bean.User;

@Entity
@Table(name = Group.ENTITY_NAME)
public class Group implements Serializable {

	protected static final String ENTITY_NAME = "spyros_group";
	private static final String JOIN_TABLE = "spyros_group_users";
	
	private static final long serialVersionUID = -8351087655995775588L;

	private static final String COLUMN_GROUP_ID = "group_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_CREATED_DATE = "created_date";
	private static final String COLUMN_OWNER = "owner_id";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_GROUP_ID)
	private Long id;
	
	@Column(name = COLUMN_NAME, length = 255)
	private String name;
	
	@Column(name = COLUMN_DESCRIPTION, length = 4000)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLUMN_CREATED_DATE)
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = COLUMN_OWNER)
	private User owner;
	
	@ManyToMany
	@JoinTable(
			name = JOIN_TABLE,
			joinColumns = {
				@JoinColumn(name = COLUMN_GROUP_ID, referencedColumnName = COLUMN_GROUP_ID)
			},
			inverseJoinColumns = {
				@JoinColumn(name = "user_id", referencedColumnName = "ic_user_id")
			}
	)
	private List<User> users;
	
}