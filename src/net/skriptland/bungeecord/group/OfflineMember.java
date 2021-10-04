package net.skriptland.bungeecord.group;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author MrCubee
 * @since 1.0
 */
public final class OfflineMember {

    private final String lastUserName;
    private final UUID uuid;

    protected OfflineMember(UUID uuid) {
        this.lastUserName = null;
        this.uuid = uuid;
    }

    protected OfflineMember(ProxiedPlayer proxiedPlayer) {
        this.lastUserName = proxiedPlayer.getName();
        this.uuid = proxiedPlayer.getUniqueId();
    }

    public String getLastUserName() {
        return this.lastUserName;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object object) {
        int hash = hashCode();

        return (object instanceof OfflineMember && object.hashCode() == hash)
        || (object instanceof ProxiedPlayer && Objects.hashCode(((ProxiedPlayer) object).getUniqueId()) == hash)
        || (object instanceof UUID && Objects.hashCode((UUID) object) == hash);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.uuid);
    }
}
