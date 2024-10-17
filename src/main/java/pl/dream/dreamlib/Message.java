package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public static void sendMessage(@NotNull Player player,@Nullable String message){
        if(message != null){
            player.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param messageList The list of strings we want to send to the player
     */
    public static void sendMessage(@NotNull Player player, @Nullable List<String> messageList){
        if(messageList == null || messageList.isEmpty()){
            return;
        }

        messageList.forEach(player::sendMessage);
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param message The string we want to send to the sender
     */
    public static void sendMessage(@NotNull CommandSender sender, @Nullable String message){
        if(message != null){
            sender.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param messageList The list of strings we want to send to the sender
     */
    public static void sendMessage(@NotNull CommandSender sender, @Nullable List<String> messageList){
        if(messageList == null || messageList.isEmpty()){
            return;
        }

        messageList.forEach(sender::sendMessage);
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param message The string we want to send to all online players
     */
    public static void sendGlobalMessage(@Nullable String message){
        if(message != null){
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(message);
            });
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param messageList The list of strings we want to send to all online players
     */
    public static void sendGlobalMessage(@Nullable List<String> messageList){
        if(messageList == null || messageList.isEmpty()){
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            sendMessage(player, messageList);
        });
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param message The string we want to broadcast
     */
    public static void sendBroadcast(@Nullable String message){
        if(message != null){
            Bukkit.getServer().broadcastMessage(message);
        }
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param messageList The list of strings we want to broadcast
     */
    public static void sendBroadcast(@Nullable List<String> messageList){
        if(messageList == null || messageList.isEmpty()){
            return;
        }

        messageList.forEach(Message::sendBroadcast);
    }
}
