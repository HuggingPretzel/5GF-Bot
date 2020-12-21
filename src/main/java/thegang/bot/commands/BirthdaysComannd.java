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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import thegang.bot.Main;
import thegang.bot.objects.Birthday;
import thegang.bot.objects.ICommand;

public class BirthdaysComannd implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        List<Birthday> bdays = Main.birthdays;

        Collections.sort(bdays, new Comparator<Birthday>(){
            
            public int compare(Birthday b1, Birthday b2){
                LocalDate b1Date = b1.getDate();
                LocalDate b2Date = b2.getDate();
                if(b1Date.getMonthValue() == b2Date.getMonthValue()){
                    return b1Date.getDayOfMonth() - b2Date.getDayOfMonth();
                }

                return b1Date.getMonthValue() - b2Date.getMonthValue();
            }
        });

        String text = "";

        for (Birthday bday : bdays) {
            
            text += bday.getPerson() + " -- " + bday.getDate().getMonthValue() + "-" + bday.getDate().getDayOfMonth() + "\n";
        }

        event.getChannel().sendMessage(text).queue();

    }

    @Override
    public String getHelp() {
        return "Send the list of birthdays of the gang.";
    }

    @Override
    public String getInvoke() {
        return "birthdays";
    }

    @Override
    public int getType() {
        return 0;
    }
    
    // private List<Birthday> sortList(List<Birthday> bdays){

    //     int id = 0;

    // }
}
