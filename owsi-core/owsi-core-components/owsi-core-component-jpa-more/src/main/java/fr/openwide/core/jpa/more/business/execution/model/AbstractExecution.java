package fr.openwide.core.jpa.more.business.execution.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.bindgen.Bindable;
import org.hibernate.annotations.Type;

import fr.openwide.core.commons.util.CloneUtils;
import fr.openwide.core.jpa.business.generic.model.GenericEntity;

@MappedSuperclass
@Bindable
public abstract class AbstractExecution<E extends GenericEntity<Integer, E>, ET extends IExecutionType> extends GenericEntity<Integer, E> {
	private static final long serialVersionUID = 6026078483841894077L;

	@GeneratedValue
	@Id
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;
	
	@Column
	@Type(type = "org.hibernate.type.StringClobType")
	private String logOutput;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ET executionType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ExecutionStatus executionStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	protected AbstractExecution() {
		super();
	}

	public AbstractExecution(ET executionType) {
		super();
		setExecutionType(executionType);
		setStartDate(new Date());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ET getExecutionType() {
		return executionType;
	}

	public void setExecutionType(ET executionType) {
		this.executionType = executionType;
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
	}

	public Date getStartDate() {
		return CloneUtils.clone(startDate);
	}

	public void setStartDate(Date startDate) {
		this.startDate = CloneUtils.clone(startDate);
	}

	public Date getEndDate() {
		return CloneUtils.clone(endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = CloneUtils.clone(endDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogOutput() {
		return logOutput;
	}

	public void setLogOutput(String logOutput) {
		this.logOutput = logOutput;
	}

	@Override
	public String getNameForToString() {
		return executionType != null ? executionType.toString() : "";
	}

	@Override
	public String getDisplayName() {
		return getNameForToString();
	}
}
