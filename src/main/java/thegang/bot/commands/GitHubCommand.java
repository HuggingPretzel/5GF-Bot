/**
 *      Copyright 2020 Daniel Sanchez
 * GitHubCommand
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

package thegang.bot.commands;

import java.util.List;

import thegang.bot.Constants;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thegang.bot.objects.ICommand;

public class GitHubCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed();

        builder.setDescription("5GF Github Page. Check it out").addField("Link", "https://github.com/daniel0294/5GF-Bot", false).setThumbnail("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png");

        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Sends github repo of the Discord Bot\n" + "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "github";
    }

    @Override
    public int getType() {
        return 3;
    }

}

    
