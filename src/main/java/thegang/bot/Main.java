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

package thegang.bot;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
// import java.util.Random;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import thegang.bot.config.Config;
import thegang.bot.crono.MainScheduler;
import thegang.bot.objects.Birthday;

public class Main {
    // private final Random random = new Random();
    
    public static List<Birthday> birthdays = new ArrayList<Birthday>();
    private static JDA jda;

    

    private Main() throws IOException {
        Config config = new Config(new File("botconfig.json"));

        CommandManager manager = new CommandManager();
        Listener listener = new Listener(manager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        EmbedUtils.setEmbedBuilder(() -> 
            new EmbedBuilder()
            .setColor(getRandomColor())
            .setTimestamp(Instant.now())
            .setFooter(Constants.NAME)
        );
   
        try {
            logger.info("Booting");

            jda = JDABuilder.createDefault(config.getString("token"))
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .addEventListeners(listener).build();

            jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching("anime"));
            // jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.streaming("cool vibes", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                

        } catch (Exception e) {
            e.printStackTrace();
        }

        MainScheduler daily = new MainScheduler();
		try {
			daily.start();
		} catch (SchedulerException e) {
			
			e.printStackTrace(); 
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
        setBirthdates();
        
    }

    public static JDA getJDA(){
        return jda;
    }
    private Color getRandomColor(){
        // float r = random.nextFloat();
        // float g = random.nextFloat();
        // float b = random.nextFloat();
        
        // return new Color(r,g,b);
        return new Color(255, 0, 0);
    }

    private static void setBirthdates(){

        Config config = Config.getInstance();

        JSONArray bdays = config.getJSONArray("birthdays");

        for(int i = 0; i < bdays.length(); i++){
            JSONObject bday = bdays.getJSONObject(i);
            JSONObject date = bday.getJSONObject("date");
            
            int year = date.getInt("year");
            int month = date.getInt("month");
            int day = date.getInt("day");

            String name = bday.getString("name");
            String discordId = bday.getString("discord_id");

            if(discordId.equals("null")){
                birthdays.add(new Birthday(LocalDate.of(year, month, day), name));
            } else {
                birthdays.add(new Birthday(LocalDate.of(year, month, day), name, discordId));
            }

        }
        

    }


    
}
