package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Send messages without formatting the chat colors
 */
public class Message {
    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param message The string we want to send to the player
     */
    public static void sendMessage(Player player,String message){
        player.sendMessage(message);
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param messageList The list of strings we want to send to the player
     */
    public static void sendMessage(Player player, List<String> messageList){
        for(String message:messageList){
            player.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param message The string we want to send to the sender
     */
    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(message);
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param messageList The list of strings we want to send to the sender
     */
    public static void sendMessage(CommandSender sender, List<String> messageList){
        for(String message:messageList){
            sender.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param message The string we want to send to all online players
     */
    public static void sendGlobalMessage(String message){
        for(Player player: Bukkit.getOnlinePlayers()){
            player.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param messageList The list of strings we want to send to all online players
     */
    public static void sendGlobalMessage(List<String> messageList){
        for(Player player: Bukkit.getOnlinePlayers()){
            for(String message:messageList){
                player.sendMessage(message);
            }
        }
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param message The string we want to broadcast
     */
    public static void sendBroadcast(String message){
        Bukkit.getServer().broadcastMessage(message);
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param messageList The list of strings we want to broadcast
     */
    public static void sendBroadcast(List<String> messageList){
        Server server = Bukkit.getServer();

        for(String message:messageList){
            server.broadcastMessage(message);
        }
    }
}
