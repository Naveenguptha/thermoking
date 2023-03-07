package com.adobe.aem.thermoking.site.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//there is two ways to write scheduler 1.implements RunnableInterface 2.implements JobInterface
// hear am using Runnable Interface. (runnable interface and job interface both are same... but runnable interface have run method.
// job Interface have execute method.

//(hear i have write one cofiguration for crone experssion. using configuration author can change the crone expression.
import com.adobe.aem.thermoking.site.core.config.SchedulerConfigurationForCronJob;

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = SchedulerConfigurationForCronJob.class)
public class ThermokingScheduler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ThermokingScheduler.class);

    private int schedulerId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(SchedulerConfigurationForCronJob config) {
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(SchedulerConfigurationForCronJob config) {
        removeScheduler();
    }

    protected void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    protected void addScheduler(SchedulerConfigurationForCronJob config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(schedulerId));
        // scheduleOptions.canRunConcurrently(true);
        scheduler.schedule(this, scheduleOptions);
    }

    @Override
    public void run() {
        LOG.info("\n ====> RUN METHOD");
    }
}
