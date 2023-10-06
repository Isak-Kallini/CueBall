package discordBot.commands;

import discordBot.utils.SelectEmbed;
import discordBot.utils.TeamRoleEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class TeamRole extends Command{
    private SelectEmbed embed;

    public TeamRole(){
        super.name = "teamrole";
        super.desc = "get a role for your team";
        embed = new TeamRoleEmbed();
    }

    public void setRole(StringSelectInteractionEvent event){
        embed.select(event);
    }

    public void run(SlashCommandInteractionEvent event) {
        embed.initial(event);
    }


    public void next(ButtonInteractionEvent event){
        embed.next(event);
    }

    public void previous(ButtonInteractionEvent event){
        embed.previous(event);
    }

    public void last(ButtonInteractionEvent event){
        embed.last(event);
    }

    public void first(ButtonInteractionEvent event){
        embed.first(event);
    }
}
