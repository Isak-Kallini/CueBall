package discordBot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Ping extends Command{

    public Ping(){
        super.name = "ping";
        super.desc = "test command";
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {
        event.reply("pong").queue();
    }
}
