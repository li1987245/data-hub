package cn.credit.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.quartz.*;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;


@Data
public class TriggerEntity {
	@NotBlank
	private String name;
	private String group;
	private LocalDateTime fireTime;
	private String cron;

	public TriggerEntity setName(final String name) {
		this.name = name;
		return this;
	}

	public TriggerEntity setGroup(final String group) {
		this.group = group;
		return this;
	}

	public TriggerEntity setFireTime(final LocalDateTime fireTime) {
		this.fireTime = fireTime;
		return this;
	}

	public TriggerEntity setCron(final String cron) {
		this.cron = cron;
		return this;
	}

	private String buildName() {
		return StringUtils.isEmpty(name) ? UUID.randomUUID().toString() : name;
	}

	/**
	 * Convenience method for building a Trigger
	 * 
	 * @return the Trigger associated with this descriptor
	 */
	public Trigger buildTrigger() {
		// @formatter:off
		if (!StringUtils.isEmpty(cron)) {
			if (!CronExpression.isValidExpression(cron))
				throw new IllegalArgumentException("Provided expression " + cron + " is not a valid cron expression");
			return TriggerBuilder.newTrigger()
					.withIdentity(buildName(), group)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron)
							.withMisfireHandlingInstructionFireAndProceed()
							.inTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault())))
					.usingJobData("cron", cron)
					.build();
		} else if (!StringUtils.isEmpty(fireTime)) {
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("fireTime", fireTime);
			return TriggerBuilder.newTrigger()
					.withIdentity(buildName(), group)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withMisfireHandlingInstructionNextWithExistingCount())
					.startAt(Date.from(fireTime.atZone(ZoneId.systemDefault()).toInstant()))
					.usingJobData(jobDataMap)
					.build();
		}
		// @formatter:on
		throw new IllegalStateException("unsupported trigger descriptor " + this);
	}

	/**
	 * 
	 * @param trigger
	 *            the Trigger used to build this descriptor
	 * @return
	 */
	public static TriggerEntity buildDescriptor(Trigger trigger) {
		// @formatter:off
		return new TriggerEntity()
				.setName(trigger.getKey().getName())
				.setGroup(trigger.getKey().getGroup())
				.setFireTime((LocalDateTime) trigger.getJobDataMap().get("fireTime"))
				.setCron(trigger.getJobDataMap().getString("cron"));
		// @formatter:on
	}
}