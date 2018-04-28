package cn.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.quartz.*;

import java.util.*;


@Data
public class JobEntity {
	@NotBlank
	private String name;
	private String group;
	private Map<String, Object> data = new LinkedHashMap<>();
	@JsonProperty("triggers")
	private List<TriggerEntity> triggerEntities = new ArrayList<>();

	public JobEntity setName(final String name) {
		this.name = name;
		return this;
	}

	public JobEntity setGroup(final String group) {
		this.group = group;
		return this;
	}

	public JobEntity setData(final Map<String, Object> data) {
		this.data = data;
		return this;
	}

	public JobEntity setTrigger(TriggerEntity trigger) {
		this.triggerEntities.add(trigger);
		return this;
	}

	public JobEntity setTriggerEntities(final List<TriggerEntity> triggerEntities) {
		this.triggerEntities = triggerEntities;
		return this;
	}

	/**
	 * Convenience method for building Triggers of Job
	 * 
	 * @return Triggers for this JobDetail
	 */
	@JsonIgnore
	public Set<Trigger> buildTriggers() {
		Set<Trigger> triggers = new LinkedHashSet<>();
		for (TriggerEntity triggerEntity : triggerEntities) {
			triggers.add(triggerEntity.buildTrigger());
		}

		return triggers;
	}

	/**
	 * Convenience method that builds a JobDetail
	 * 
	 * @return the JobDetail built from this descriptor
	 */
	public JobDetail buildJobDetail(Class<? extends Job> cls) {
		// @formatter:off
		JobDataMap jobDataMap = new JobDataMap(getData());
		return JobBuilder.newJob(cls)
                .withIdentity(getName(), getGroup())
                .usingJobData(jobDataMap)
                .build();
		// @formatter:on
	}
}