package discordBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;

public class Main {
    public static SessionFactory factory;
    public static void initialize(SessionFactory hibernateFactory){
        factory = hibernateFactory;
        String token = null;
        try {
            token = Files.readString(Paths.get("token.txt")).trim();
        } catch (IOException e) {
            ErrorLogger.log(e);
        }

        JDA jda = JDABuilder.createLight(token, EnumSet.noneOf(GatewayIntent.class))
                .addEventListeners(new CommandHandler())
                .build();
        CommandListUpdateAction commands = jda.updateCommands();




        CommandHandler.init(commands);

        commands.queue();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            ErrorLogger.log(e);
        }
    }
}
