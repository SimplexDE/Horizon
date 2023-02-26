package de.simplex.horizon.listeners;

import de.simplex.horizon.commands.utility.RankAssigning;
import de.simplex.horizon.commands.utility.TabListCompiler;
import de.simplex.horizon.horizon.Horizon;
import de.simplex.horizon.methods.Scoreboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.user.track.UserDemoteEvent;
import net.luckperms.api.event.user.track.UserPromoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankListener {
    public RankListener(LuckPerms luckPerms) {
        EventBus eventBus = luckPerms.getEventBus();

        eventBus.subscribe(Horizon.getHorizon(), UserPromoteEvent.class, this::onUserPromote);
        eventBus.subscribe(Horizon.getHorizon(), UserDemoteEvent.class, this::onUserDemote);
        eventBus.subscribe(Horizon.getHorizon(), NodeMutateEvent.class, this::onNodeMutate);
    }

    private void onUserPromote(UserPromoteEvent event) {
        Player p = Bukkit.getPlayer(event.getUser().getUniqueId());
        RankAssigning.assignRank(p);
        Scoreboard.updateRank(p);
    }

    private void onUserDemote(UserDemoteEvent event) {
        Player p = Bukkit.getPlayer(event.getUser().getUniqueId());
        RankAssigning.assignRank(p);
        Scoreboard.updateRank(p);
    }


    private void onNodeMutate(NodeMutateEvent event) {

        TabListCompiler tc = new TabListCompiler();

        for (Player p : Bukkit.getOnlinePlayers()) {
            RankAssigning.assignRank(p);
            Scoreboard.updateRank(p);
            final Component header = MiniMessage.miniMessage().deserialize("<newline>  <rainbow>ʜᴏʀɪᴢᴇɴ ᴍʏsᴇʀᴠᴇʀ</rainbow>  <newline> <grey>Du befindest dich auf <yellow>" + p.getWorld().getName() + "<grey>.<newline>");
            final Component footer = MiniMessage.miniMessage().deserialize("<newline><green>" + Bukkit.getOnlinePlayers().size() + "<grey>/<red>" + Bukkit.getMaxPlayers());
            tc.setTabListHeaderAndFooter(Horizon.adventure().players());
        }
    }
}
