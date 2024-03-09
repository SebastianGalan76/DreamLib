package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    public static void sendMessage(@NotNull Player player,@NotNull String message){
        player.sendMessage(message);
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param messageList The list of strings we want to send to the player
     */
    public static void sendMessage(@NotNull Player player, @NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

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
    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message){
        sender.sendMessage(message);
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param messageList The list of strings we want to send to the sender
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

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
    public static void sendGlobalMessage(@NotNull String message){
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
    public static void sendGlobalMessage(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

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
    public static void sendBroadcast(@NotNull String message){
        Bukkit.getServer().broadcastMessage(message);
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param messageList The list of strings we want to broadcast
     */
    public static void sendBroadcast(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

        Server server = Bukkit.getServer();
        for(String message:messageList){
            server.broadcastMessage(message);
        }
    }
}
