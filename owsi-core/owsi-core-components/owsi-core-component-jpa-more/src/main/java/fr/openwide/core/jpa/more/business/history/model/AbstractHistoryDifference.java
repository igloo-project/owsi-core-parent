package fr.openwide.core.jpa.more.business.history.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.bindgen.Bindable;

import com.google.common.collect.Lists;

import fr.openwide.core.commons.util.collections.CollectionUtils;
import fr.openwide.core.jpa.more.business.history.model.atomic.HistoryDifferenceEventType;
import fr.openwide.core.jpa.more.business.history.model.embeddable.HistoryDifferencePath;
import fr.openwide.core.jpa.more.business.history.model.embeddable.HistoryValue;
import fr.openwide.core.jpa.more.util.fieldpath.model.FieldPath;

@MappedSuperclass
@Bindable
@Cacheable
@Access(AccessType.FIELD)
public abstract class AbstractHistoryDifference<HD extends AbstractHistoryDifference<HD, HL>, HL extends AbstractHistoryLog<HL, ?, HD>> extends AbstractHistoryElement<HD, HL> {
	
	private static final long serialVersionUID = 5651233323242057270L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional = true)
	private HL parentLog;

	@ManyToOne(optional = true)
	private HD parentDifference;
	
	@Embedded
	private HistoryDifferencePath path;
	
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private HistoryDifferenceEventType eventType;
	
	@Embedded
	private HistoryValue before;

	@Embedded
	private HistoryValue after;
	
	@OneToMany(mappedBy = "parentDifference", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderColumn
	private List<HD> differences = Lists.newArrayList();
	
	protected AbstractHistoryDifference() {
	}

	protected AbstractHistoryDifference(HistoryDifferencePath path, HistoryDifferenceEventType action, HistoryValue before, HistoryValue after) {
		init(path, action, before, after);
	}
	
	public void init(HistoryDifferencePath path, HistoryDifferenceEventType action, HistoryValue before, HistoryValue after) {
		this.path = path;
		this.eventType = action;
		this.before = before;
		this.after = after;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getNameForToString() {
		return String.valueOf(path);
	}

	@Override
	public String getDisplayName() {
		return toString();
	}

	@Override
	@Transient
	public HL getRootLog() {
		return parentDifference != null ? parentDifference.getRootLog() : parentLog;
	}
	
	@Override
	@Transient
	protected AbstractHistoryElement<?, ?> getParent() {
		if (parentDifference != null) {
			return parentDifference;
		} else {
			return parentLog;
		}
	}
	
	@Override
	@Transient
	public FieldPath getRelativePath() {
		return path.getPath();
	}
	
	public HL getParentLog() {
		return parentLog;
	}

	public void setParentLog(HL parentLog) {
		this.parentLog = parentLog;
	}

	public HD getParentDifference() {
		return parentDifference;
	}

	public void setParentDifference(HD parentDifference) {
		this.parentDifference = parentDifference;
	}

	public HistoryDifferencePath getPath() {
		return path;
	}
	
	public HistoryDifferenceEventType getEventType() {
		return eventType;
	}

	public HistoryValue getBefore() {
		if (before == null) {
			before = new HistoryValue();
		}
		return before;
	}

	public HistoryValue getAfter() {
		if (after == null) {
			after = new HistoryValue();
		}
		return after;
	}
	
	public List<HD> getDifferences() {
		return Collections.unmodifiableList(differences);
	}
	
	public void setDifferences(List<HD> differences) {
		CollectionUtils.replaceAll(this.differences, differences);
	}

}
