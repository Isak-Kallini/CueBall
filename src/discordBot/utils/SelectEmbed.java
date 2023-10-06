package discordBot.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface SelectEmbed {
    void initial(SlashCommandInteractionEvent event);
    void select(StringSelectInteractionEvent event);
    void next(ButtonInteractionEvent event);
    void previous(ButtonInteractionEvent event);
    void last(ButtonInteractionEvent event);
    void first(ButtonInteractionEvent event);
}
