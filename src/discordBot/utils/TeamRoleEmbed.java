package discordBot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TeamRoleEmbed implements SelectEmbed{
    private List<String> teams;
    private int current = 0;
    private int step = 24;


    public TeamRoleEmbed(){
        teams = new ArrayList<>();
        String[] teamsarray = {
                "Adult Swim", "Hellish Akuma", "Team Name", "HIGH PRIORITY", "≡GrenzLinge≡", "Ink Floyd", "69Süppchen!",
                "INKfinity", "69Suppe!", "INKlusive!-LK.AG", "88☆ミ", "Inku no mi", "Abyss Ink", "Insecure Connection",
                "Amatsu Legacy", "Jellyfish Jam", "Anenemies", "Kiwi Ice Cream", "Angelink", "Krill-ical Error",
                "ASC Niji O", "Low Quality", "Axolotl Anarchy!!", "Malibu Rising", "Bewitchers", "Mohnsaft Bande",
                "Big Bagel", "Off-Meta Squids", "Bish n' Chips", "Painted Knights", "Black Aurora", "PANDORA",
                "Black Squid Band", "Pearlescence", "Blaeksprutter", "Physalia", "Blåhaj Blast!!", "Red Dawn",
                "BlahajSlider", "Reignfall", "BLITZ WAVE", "Remedy", "C.R.A.B.", "Riptide", "Calamariachis",
                "RRRemix!!!", "Camp Flyfish", "SC Cyclones", "Camp Juice", "Shark Bait", "Card Sharks", "Shark Bytes",
                "Checkmate", "Shell-Outs", "Coral Mist", "Shipwreck'd", "Cosmic Slushie", "Silly Seaside Sharks!",
                "Crab Rave", "SODApods!!", "Curtain Call", "Soliloquy of the Stars", "Dark Matter",
                "SPARK: ATROVIRENS", "Deep Sea Dissonance", "Sparkling C", "distortion", "SplatDiamond Alpha",
                "DPU Ink Stew", "squid sushi", "Drum and Bass", "Squidmark", "DSM Vaquitas", "Squidphony",
                "Dubble Bubble", "STar X", "Einherjer Crew", "Starboard!", "Elegant Emerald", "Stardust Inksaders",
                "Eternal Eclipsa", "Tentacoolios", "Euphoric Vibe", "Tentashrimps", "Feel Good Ink",
                "The Breakfast Club", "Fried Calamari", "THULIUM", "Harmonic Convergence", "To Be Determined",
                "Hazard Level Minimum", "Valence", "Hell Dualies", "Wishiwashi", "Hoothoot Recruits", "autacle"
        };
        teams.addAll(List.of(teamsarray));
        teams.sort(null);
    }

    private String buildTable(){
        StringBuilder res = new StringBuilder();
        for(int i = current; i < current + step; i++){
            res.append(i + 1).append(": ").append(teams.get(i)).append("\n");
        }
        return res.toString();
    }

    private MessageEmbed buildEmbed(){
        MessageEmbed embed = (new EmbedBuilder()
                .setTitle("Select a team")
                .setDescription("```" + buildTable() + "```")
                .setFooter("Vieweing " + (current + 1) + " to " + (current + step))).build();
        return embed;
    }

    private void editMessage(ButtonInteractionEvent event){
        event.editComponents(
                ActionRow.of(StringSelectMenu.create("teamrole").addOptions(getOptionList()).build()),
                ActionRow.of(
                        Button.primary("teamrole start", "start"),
                        Button.primary("teamrole previous", "previous"),
                        Button.primary("teamrole next", "next"),
                        Button.primary("teamrole end", "end"))
        )
                .setEmbeds(buildEmbed()).queue();
        //event.editMessageEmbeds(buildEmbed()).queue();
    }

    @Override
    public void initial(SlashCommandInteractionEvent event) {
        event.replyEmbeds(buildEmbed()).addActionRow(
                StringSelectMenu.create("teamrole").addOptions(getOptionList()).build())
                        .addActionRow(
                Button.primary("teamrole start", "start"),
                Button.primary("teamrole previous", "previous"),
                Button.primary("teamrole next", "next"),
                Button.primary("teamrole end", "end")
        ).queue();
    }

    @Override
    public void select(StringSelectInteractionEvent event) {
        int teamnr = Integer.parseInt(event.getInteraction().getSelectedOptions().get(0).getValue());
        String teamName = teams.get(teamnr);
        Guild guild = event.getGuild();
        Optional<Role> role = guild.getRoles().stream().filter(r -> r.getName().equals(teamName)).findFirst();
        if(role.isEmpty()){
            role = Optional.of(guild.createRole().setName(teamName).complete());
        }
        AtomicReference<String> message = new AtomicReference<>("Set role ");
        Optional<Role> existingrole = event.getMember().getRoles().stream().filter(r -> teams.contains(r.getName())).findFirst();
        existingrole.ifPresent(r -> {
            guild.removeRoleFromMember(event.getUser(), r).queue();
            message.set("Removed " + r.getName() + " and added " + teams.get(teamnr));
        });

        guild.addRoleToMember(event.getUser(), role.get()).queue();
        event.reply(message.get()).queue();
        current = 0;
    }

    private List<SelectOption> getOptionList() {
        List<SelectOption> optionList = new ArrayList<>();
        for(int i = current; i < current + step; i++){
            optionList.add(SelectOption.of((i + 1) + " " + teams.get(i), Integer.toString(i)));
        }
        return optionList;
    }

    @Override
    public void next(ButtonInteractionEvent event) {
        current = Math.min(current + step, teams.size() - step);
        editMessage(event);
    }

    @Override
    public void previous(ButtonInteractionEvent event) {
        current = Math.max(current - step, 0);
        editMessage(event);
    }

    @Override
    public void last(ButtonInteractionEvent event) {
        current = teams.size() - step;
        editMessage(event);
    }

    @Override
    public void first(ButtonInteractionEvent event) {
        current = 0;
        editMessage(event);
    }
}
