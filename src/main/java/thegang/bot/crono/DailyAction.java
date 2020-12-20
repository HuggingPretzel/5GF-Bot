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

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import thegang.bot.Main;
import thegang.bot.imagedraw.CreateBirthdayImage;
import thegang.bot.objects.Birthday;

public class DailyAction implements Job {

    List<Birthday> Birthdays;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Running");
        
        List<Birthday> birthdays = Main.birthdays;

        Birthday birthday = checkDay(birthdays);

        if(birthday == null) return;

        User user = null;
        JDA jda = Main.getJDA();
        //Server
        Guild server = jda.getGuildById("524355952044933161");
        TextChannel channel = server.getTextChannelById("579830550806659072");

        //Test
        // Guild server = jda.getGuildById("374386835327287306");
        // TextChannel channel = server.getTextChannelById("374386835327287308");
        String text = "@everyone wish " + birthday.getPerson() + " a very Happy Birthday!";

        if(birthday.getDiscordId() != null){
            
            user = jda.retrieveUserById(birthday.getDiscordId()).complete();
        
            text = "@everyone wish " + birthday.getPerson() + " (" + user.getAsMention() + ") a very Happy Birthday!";
        } 

        File file = CreateBirthdayImage.start(user, birthday.getPerson());

        if(file == null){
            channel.sendMessage(text).queue();
        } else {
            channel.sendFile(file, file.getName()).append(text).queue();
        }

    }



    private Birthday checkDay(List<Birthday> birthdays){


        for (Birthday birthday : birthdays) {
            int month = birthday.getDate().getMonth().getValue();
            int day = birthday.getDate().getDayOfMonth();

            int thisMonth = LocalDate.now().getMonth().getValue();
            int thisDay = LocalDate.now().getDayOfMonth();

            if(month == thisMonth && day == thisDay){
                return birthday;
            }
            
        }
        return null;
    }
}
