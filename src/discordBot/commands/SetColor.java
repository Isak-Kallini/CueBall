package discordBot.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetColor extends Command{
    ArrayList<String> teams;
    public SetColor(){
        super.name = "setcolor";
        super.desc = "set the color of your team role";
        super.options.add(new OptionData(OptionType.STRING, "color", "color you want in hex").setRequired(true));
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
    }

    @Override
    public void run(SlashCommandInteractionEvent event) {
        String color = event.getOption("color", "#ffffff", OptionMapping::getAsString);
        Optional<Role> role = event.getMember().getRoles().stream().filter(r -> teams.contains(r.getName())).findFirst();
        Optional<Role> captainrole = event.getMember().getRoles().stream().filter(r -> r.getName().equals("captain")).findFirst();

        try {
            if(captainrole.isPresent()) {
                role.ifPresent(value -> value.getManager().setColor(Color.decode(color)).queue());
                event.reply("Set color to " + color).queue();
            }else{
                event.reply("Only team captain can change color").queue();
            }
        }catch (NumberFormatException e){
            event.reply("Hex code is invalid").queue();
        }
    }
}
