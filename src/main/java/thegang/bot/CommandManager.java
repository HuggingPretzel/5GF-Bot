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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import thegang.bot.objects.ICommand;
import thegang.bot.commands.GitHubCommand;
import thegang.bot.commands.UploadHelpCommand;

/**
 * CommandManager
 */
public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new UploadHelpCommand());
        addCommand(new GitHubCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }



    // public Collection<ICommand> getCommands(int type){
    //     Map<String, ICommand> newCommands = new HashMap<>();

    //     commands.forEach((k, v)->{
    //         if(v.getType() == type){
    //             newCommands.put(k, v);
    //         }
    //     });

    //     return newCommands.values();
    // }
    //

    public List<ICommand> getCommands(int type){
        List<ICommand> newCommands = new ArrayList<>();

        commands.forEach((k, v)->{
            if(v.getType() == type){
                newCommands.add(v);
            }
        });

        return newCommands;
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public int getType(ICommand command){
        return command.getType();
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        
        final String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");

        // for(String text: split){
        //     System.out.println(text);
        // }
        
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);

        }
    }
}