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
// import java.util.Random;

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

public class Main {
    // private final Random random = new Random();

    private Main() throws IOException {
        Config config = new Config(new File("botconfig.json"));

        CommandManager manager = new CommandManager();
        Listener listener = new Listener(manager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        EmbedUtils.setEmbedBuilder(() -> new EmbedBuilder().setColor(getRandomColor()).setTimestamp(Instant.now()));
   
        try {
            logger.info("Booting");

           JDA jda = JDABuilder.createDefault(config.getString("token"))
           .enableIntents(GatewayIntent.GUILD_MEMBERS)
           .addEventListeners(listener).build();

           jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching("anime"));
            

           
         

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
    private Color getRandomColor(){
        // float r = random.nextFloat();
        // float g = random.nextFloat();
        // float b = random.nextFloat();
        
        // return new Color(r,g,b);
        return new Color(255, 0, 0);
    }
    
}
