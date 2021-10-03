package net.skriptland.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;
import net.skriptland.bungeecord.listeners.RegisterListeners;

public class SkriptLand extends Plugin {

    @Override
    public void onEnable() {
        getProxy().unregisterChannel("Bungeecord");
        RegisterListeners.register(getProxy().getPluginManager());
    }
}
