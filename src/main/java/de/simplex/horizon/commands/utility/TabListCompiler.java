package de.simplex.horizon.commands.utility;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class TabListCompiler {

    public void setTabListHeader(Audience player, Component header) {
        player.sendPlayerListHeader(header);
    }

    public void setTabListFooter(Audience player, Component footer) {
        player.sendPlayerListFooter(footer);
    }

    public void setTabListHeaderAndFooter(Audience player, Component header, Component footer) {
        player.sendPlayerListHeader(header);
        player.sendPlayerListFooter(footer);
    }
}
