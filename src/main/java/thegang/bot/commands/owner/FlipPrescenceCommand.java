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

package thegang.bot.commands.owner;

import java.util.List;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thegang.bot.config.Config;
import thegang.bot.objects.ICommand;

public class FlipPrescenceCommand implements ICommand{

	@Override
	public void handle(List<String> args, GuildMessageReceivedEvent event) {
        

        if(!event.getAuthor().getId().equals(Config.getInstance().get("owner"))) return;
        if(args.size() != 1) return;

        try{
            int num = Integer.parseInt(args.get(0));

            switch (num) {
                case 0:
                    event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching("anime"));
                    break;
                case 1:
                    event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.streaming("cool vibes", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                    break;
                default:
                    event.getChannel().sendMessage("No presence for this value").queue();
                    break;
            }

        } catch(NumberFormatException e){
            event.getChannel().sendMessage("Only send a number").queue();
        }

        
		
	}

	@Override
	public String getHelp() {
		return "Owner command to change presence";
	}

	@Override
	public String getInvoke() {
		return "flip";
	}

	@Override
	public int getType() {
		return 0;
	}
    
}
