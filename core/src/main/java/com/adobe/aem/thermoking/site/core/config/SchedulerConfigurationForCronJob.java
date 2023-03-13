package com.adobe.aem.thermoking.site.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Thermoking - Scheduler Configuration for CronJob", description = "Sling scheduler configuration for CronJob")
public @interface SchedulerConfigurationForCronJob {

    @AttributeDefinition(name = "Scheduler name", description = "Name of the scheduler", type = AttributeType.STRING)
    public String schedulerName() default "Custom Sling Scheduler ConfigurationForCronJob";

    @AttributeDefinition(name = "Cron Expression", description = "Cron expression used by the scheduler", type = AttributeType.STRING)
    public String cronExpression() default "0/20 * * * * ?"; // runs every 10 seconds
}
