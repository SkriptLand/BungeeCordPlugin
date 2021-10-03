package net.skriptland.bungeecord;

import fr.mrcubee.langlib.Lang;
import net.md_5.bungee.api.plugin.Plugin;
import net.skriptland.bungeecord.listeners.RegisterListeners;

public class SkriptLand extends Plugin {

    @Override
    public void onEnable() {
        Lang.setDefaultLang("EN_us");
        getProxy().unregisterChannel("Bungeecord");
        RegisterListeners.register(getProxy().getPluginManager());
    }
}
