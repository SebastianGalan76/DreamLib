package pl.dream.dreamlib.controller;

import org.bukkit.permissions.Permissible;
import org.jetbrains.annotations.NotNull;
import pl.dream.dreamlib.data.PlayerRank;

public class RankController {
    /**
     * Returns the highest player's rank or the default rank
     *
     * @param permissible The player
     * @return The highest player's rank
     */
    @NotNull
    public static PlayerRank getRank(Permissible permissible){
        PlayerRank rank = PlayerRank.getByName("default");

        for(PlayerRank r:PlayerRank.values()){
            if(permissible.hasPermission("dreamlib.rank."+r.name)){
               if(rank.power < r.power){
                   rank = r;
               }
            }
        }

        return rank;
    }


}
