package org.iglooproject.jpa.more.business.task.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.bindgen.Bindable;
import org.iglooproject.commons.util.CloneUtils;
import org.iglooproject.jpa.business.generic.model.GenericEntity;
import org.iglooproject.jpa.more.business.task.util.TaskResult;
import org.iglooproject.jpa.more.business.task.util.TaskStatus;
import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Bindable
public class QueuedTaskHolder extends GenericEntity<Long, QueuedTaskHolder> {

	private static final long serialVersionUID = 3926959721176678607L;

	public static final String NAME = "name";
	public static final String NAME_SORT = "nameSort";

	public static final String QUEUE_ID = "queueId";

	public static final String TASK_TYPE = "taskType";

	public static final String CREATION_DATE = "creationDate";
	public static final String TRIGGERING_DATE = "triggeringDate";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";

	public static final String STATUS = "status";

	public static final String RESULT = "result";

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	@SuppressWarnings("squid:S1845") // attribute name differs only by case on purpose
	private String name;

	@Column(nullable = true)
	private String queueId;

	@Column(nullable = false)
	private String taskType;

	@Column(nullable = false)
	private Date creationDate;

	private Date triggeringDate = null;

	private Date startDate = null;

	private Date endDate = null;

	@Column(name = "optLock")
	private int version;

	@Column(nullable = false)
	private String serializedTask;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@SuppressWarnings("squid:S1845") // attribute name differs only by case on purpose
	private TaskStatus status;
	
	@Column
	@Enumerated(EnumType.STRING)
	@SuppressWarnings("squid:S1845") // attribute name differs only by case on purpose
	private TaskResult result;

	@Column
	private String stackTrace;

	@Column
	private String report;

	protected QueuedTaskHolder() {
	}

	public QueuedTaskHolder(String name, String queueId, String taskType, String serializedTask) {
		super();
		setName(name);
		setQueueId(queueId);
		setTaskType(taskType);
		setSerializedTask(serializedTask);
		setStatus(TaskStatus.TO_RUN);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getCreationDate() {
		return CloneUtils.clone(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = CloneUtils.clone(creationDate);
	}

	public Date getTriggeringDate() {
		return CloneUtils.clone(triggeringDate);
	}

	public void setTriggeringDate(Date triggeringDate) {
		this.triggeringDate = CloneUtils.clone(triggeringDate);
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

	public void setEndDate(Date completionDate) {
		this.endDate = CloneUtils.clone(completionDate);
	}

	public String getSerializedTask() {
		return serializedTask;
	}

	public void setSerializedTask(String serializedTask) {
		this.serializedTask = serializedTask;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public TaskResult getResult() {
		return result;
	}

	public void setResult(TaskResult result) {
		this.result = result;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	@JsonIgnore
	public void resetExecutionInformation() {
		setResult(null);
		setStackTrace(null);
		setReport(null);
	}
	
	@JsonIgnore
	public void updateExecutionInformation(TaskExecutionResult executionResult, ObjectMapper objectMapper)
			throws JsonProcessingException {
		if (executionResult != null) {
			setResult(executionResult.getResult());
			setStackTrace(executionResult.getStackTrace());
			setReport(objectMapper.writeValueAsString(executionResult.getReport()));
		}
	}

	@Override
	public String getNameForToString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
		return new ToStringCreator(this)
				.append("id", getId())
				.append("name", getName())
				.append("creationDate", (getCreationDate() != null) ? dateFormat.format(getCreationDate()) : null)
				.append("startDate", (getStartDate() != null) ? dateFormat.format(getStartDate()) : null)
				.append("completionDate", (getEndDate() != null) ? dateFormat.format(getEndDate()) : null)
				.toString();
	}

	@Override
	public String getDisplayName() {
		return getName();
	}
}
