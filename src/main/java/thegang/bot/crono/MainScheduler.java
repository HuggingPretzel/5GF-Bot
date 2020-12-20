/**
 *      Copyright 2020 Daniel Sanchez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package thegang.bot.crono;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MainScheduler extends SimpleScheduleBuilder {

    public void start() throws SchedulerException {

        JobDetail dailyAction = JobBuilder.newJob(DailyAction.class).build();
        Trigger dailyTrigger = TriggerBuilder.newTrigger().withIdentity("DailyTrigger").startNow().withSchedule(dailyAtHourAndMinute(00, 01)).build();

        Scheduler s = StdSchedulerFactory.getDefaultScheduler();

        s.start();
        s.scheduleJob(dailyAction, dailyTrigger);
    }

}