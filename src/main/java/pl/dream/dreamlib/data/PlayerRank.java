package pl.dream.dreamlib.data;

import java.util.Arrays;

public enum PlayerRank {
    DEFAULT(0, "default"),
    PLAYER(1, "player"),
    VIP(1000, "vip"),
    SVIP(2000, "svip"),
    ELITE(5000, "elite"),
    SPONSOR(10000, "sponsor"),
    CHAT_MOD(100000, "chatmod"),
    HELPER(110000, "helper"),
    MODERATOR(250000, "moderator"),
    ADMIN(500000, "admin"),
    OWNER(1000000, "owner"),
    ;

    public final int power;
    public final String name;

    PlayerRank(int power, String name){
        this.power = power;
        this.name = name;
    }

    public static PlayerRank getByName(String name){
        return Arrays.stream(values()).filter(rank -> rank.name.equalsIgnoreCase(name)).findFirst().orElse(DEFAULT);
    }
}
