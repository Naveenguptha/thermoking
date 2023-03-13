package com.adobe.aem.thermoking.site.core.schedulers;

//there is two ways to write scheduler 1.implements RunnableInterface 2.implements JobInterface
//hear am using Job Interface. (runnable interface and job interface both are same... but runnable interface have run method.
//job Interface have execute method.

//(hear i have write one cofiguration for crone experssion. using configuration author can change the crone expression.

import com.adobe.aem.thermoking.site.core.config.SchedulerConfigurationForCronJob;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(immediate = true, service = Job.class)
@Designate(ocd = SchedulerConfigurationForCronJob.class)
public class SchedulerImplementationwithJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerImplementationwithJob.class);

    private int schedulerJobId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(SchedulerConfigurationForCronJob config) {
        addSchedulerJob(config);
        schedulerJobId = config.schedulerName().hashCode();
    }

    @Deactivate
    protected void deactivate(SchedulerConfigurationForCronJob config) {
        removeSchedulerJob();
    }

    private void removeSchedulerJob() {
        scheduler.unschedule(String.valueOf(schedulerJobId));
    }

    private void addSchedulerJob(SchedulerConfigurationForCronJob config) {

        ScheduleOptions in = scheduler.EXPR("0 03 17 1/1 * ? *");
        Map<String, Serializable> inMap = new HashMap<>();
        inMap.put("country", "IN");
        inMap.put("url", "www.in.com");
        in.config(inMap);

        scheduler.schedule(this, in);
        ScheduleOptions de = scheduler.EXPR("0 04 17 1/1 * ? *");
        Map<String, Serializable> deMap = new HashMap<>();
        deMap.put("country", "DE");
        deMap.put("url", "www.de.com");
        de.config(deMap);
        scheduler.schedule(this, de);

        ScheduleOptions us = scheduler.EXPR("0 05 17 1/1 * ? *");
        Map<String, Serializable> usMap = new HashMap<>();
        usMap.put("country", "US");
        usMap.put("url", "www.us.com");
        us.config(usMap);
        scheduler.schedule(this, us);
    }

    @Override
    public void execute(JobContext jobContext) {
        LOG.info("\n =======> COUNTRY {} : URL {} ", jobContext.getConfiguration().get("country"),
                jobContext.getConfiguration().get("url"));
    }
}
