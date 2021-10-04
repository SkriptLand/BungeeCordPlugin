package net.skriptland.bungeecord.group;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

/**
 * @author MrCubee
 * @since 1.0
 */
public class Group {

    private String name;
    private UUID owner;
    private final Set<OfflineMember> offlineMembers;
    private final Set<ProxiedPlayer> members;

    protected Group(UUID ownerUUID, String name) {
        this.name = name;
        this.owner = ownerUUID;
        this.offlineMembers = new HashSet<OfflineMember>();
        this.members = new HashSet<ProxiedPlayer>();
        this.offlineMembers.add(new OfflineMember(ownerUUID));
    }

    protected Group(ProxiedPlayer player, String name) {
        this.name = name;
        this.owner = player.getUniqueId();
        this.offlineMembers = new HashSet<OfflineMember>();
        this.members = new HashSet<ProxiedPlayer>();
        this.members.add(player);
    }

    public String getName() {
        return this.name;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public ProxiedPlayer getPlayerOwner() {
        return BungeeCord.getInstance().getPlayer(this.owner);
    }

    public Set<ProxiedPlayer> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }

    public Set<OfflineMember> getOfflineMembers() {
        return Collections.unmodifiableSet(this.offlineMembers);
    }

    public boolean contains(final ProxiedPlayer player) {
        return player != null && this.members.contains(player);
    }

    public boolean contains(final UUID playerUUID) {
        if (playerUUID == null)
            return false;
        else if (this.offlineMembers.contains(playerUUID))
            return true;
        for (final ProxiedPlayer proxiedPlayer : this.members) {
            if (proxiedPlayer.getUniqueId().equals(playerUUID))
                return true;
        }
        return false;
    }

    public boolean connect(final ProxiedPlayer player) {
        if (player == null || !this.offlineMembers.remove(player))
            return false;
        this.members.add(player);
        return true;
    }

    public boolean disconnect(final ProxiedPlayer player) {
        if (player == null || !this.members.remove(player))
            return false;
        this.offlineMembers.add(new OfflineMember(player));
        return true;
    }

    public boolean addPlayer(final ProxiedPlayer player) {
        return player != null && !connect(player) && this.members.add(player);
    }

    public boolean addPlayer(final UUID playerUUID) {
        ProxiedPlayer proxiedPlayer;

        if (playerUUID == null)
            return false;
        proxiedPlayer = BungeeCord.getInstance().getPlayer(playerUUID);
        if (proxiedPlayer == null)
            return this.offlineMembers.add(new OfflineMember(playerUUID));
        return addPlayer(proxiedPlayer);
    }

    public boolean removePlayer(final ProxiedPlayer player) {
        if (player == null)
            return false;
        if (player.getUniqueId().equals(this.owner))
            return Groups.removeGroup(this);
        return this.members.remove(player) || this.offlineMembers.remove(player);
    }

    public boolean removePlayer(final UUID playerUUID) {
        boolean status = false;

        if (playerUUID == null)
            return false;
        if (playerUUID.equals(this.owner))
            return Groups.removeGroup(this);
        for (ProxiedPlayer proxiedPlayer : this.members) {
            if (playerUUID.equals(proxiedPlayer.getUniqueId())) {
                this.members.remove(proxiedPlayer);
                status = true;
                break;
            }
        }
        status |= this.offlineMembers.remove(playerUUID);
        return status;
    }

    public int countMember() {
        return this.members.size() + this.offlineMembers.size();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Group && object.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

}
