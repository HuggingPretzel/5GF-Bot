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

package thegang.bot.objects;

import java.time.LocalDate;

public class Birthday {

    private LocalDate date;
    private String person;
    private String discordId = null;

    public Birthday(LocalDate date, String person){
        this.date = date;
        this.person = person;
    }

    public Birthday(LocalDate date, String person, String discordId){
        this.date = date;
        this.person = person;
        this.discordId = discordId;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public String getPerson(){
        return this.person;
    }

    public String getDiscordId(){
        return this.discordId;
    }
    
    
}