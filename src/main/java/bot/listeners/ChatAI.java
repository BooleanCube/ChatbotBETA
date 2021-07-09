package bot.listeners;

import bot.Secrets;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

public class ChatAI extends ListenerAdapter {

    HashSet<Long> responseChannels = new HashSet<>();

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(responseChannels.contains(event.getChannel().getIdLong()) && !event.getMember().getUser().isBot()) {
            try {
                URL api = new URL("https://api.snowflakedev.cf/api/chatbot?message=" + event.getMessage().getContentRaw().replaceAll(" ", "%20"));
                HttpsURLConnection con = (HttpsURLConnection) api.openConnection();
                con.setRequestProperty("Authorization", Secrets.APIKEY);
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input = bf.readLine();
                String response = input.substring(12, input.length()-2);
                event.getChannel().sendMessage(response).queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("**Something went wrong!** Please try again later!").queue();
                return;
            }
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        responseChannels.add(778357890012151869L);
        responseChannels.add(781541775495266325L);
    }

}
