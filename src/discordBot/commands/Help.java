package discordBot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Help extends Command{

    public Help(){
        super.name = "help";
        super.desc = "help about commands";
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {
        event.reply("**/teamrole**: get a role for your team.\n" +
                "**/setcolor**: only available for users with the captain role, change the color of your team role.").queue();
    }
}
