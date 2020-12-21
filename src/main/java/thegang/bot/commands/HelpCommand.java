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

package thegang.bot.commands;

import java.util.List;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thegang.bot.CommandManager;
import thegang.bot.Constants;
import thegang.bot.objects.ICommand;

public class HelpCommand implements ICommand {
    CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.size() < 1) {

            manager.getCommands();

            EmbedBuilder builder = EmbedUtils.getDefaultEmbed();
            builder.setTitle("List of Commands");
            builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            builder.addField("Prefix", Constants.PREFIX, false);

            String text = "";
            for (ICommand command : manager.getCommands()) {
                text += command.getInvoke() + "\n";
            }

            builder.addField("Commands", text, false);

            event.getChannel().sendMessage(builder.build()).queue();

        } else {
            String name = args.get(0);
            if (args.size() > 1) {
                name = String.join(" ", args);
            }

            try {
                ICommand command = manager.getCommand(name);

                event.getChannel().sendMessage("Command help for `" + name + "`\n" + command.getHelp())
                        .queue();

            } catch (NullPointerException e) {
                event.getChannel().sendMessage("Command `" + name + "` does not exist.\nUse " + Constants.PREFIX
                        + "help to get list of commands.").queue();

            }
        }

    }

    @Override
    public String getHelp() {
        return "Sends the list of available commands.\nAdd command name after to see specific info of a command";
    }

    @Override
    public String getInvoke() {
        return "help";
    }

    @Override
    public int getType() {
        return 0;
    }

}
