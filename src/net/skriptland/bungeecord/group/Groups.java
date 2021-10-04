package net.skriptland.bungeecord.group;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author MrCubee
 * @since 1.0
 */
public class Groups {

    private final static Set<Group> GROUP_LIST = new HashSet<Group>();

    public static Group getPlayerGroup(final ProxiedPlayer player) {
        for (Group group : GROUP_LIST) {
            if (group.contains(player))
                return group;
        }
        return null;
    }

    public static Group getPlayerGroup(final UUID playerUUID) {
        for (Group group : GROUP_LIST) {
            if (group.contains(playerUUID))
                return group;
        }
        return null;
    }

    public static Group getGroupByName(final String name) {
        int hash = Objects.hash(name);

        for (Group group : GROUP_LIST) {
            if (group.hashCode() == hash)
                return group;
        }
        return null;
    }

    public static boolean addPlayerGroup(final Group group, final ProxiedPlayer player) {
        return group != null && player != null && group.addPlayer(player);
    }

    public static boolean addPlayerGroup(final Group group, final UUID playerUUID) {
        return group != null && playerUUID != null && group.addPlayer(playerUUID);
    }

    public static boolean removePlayerGroup(final ProxiedPlayer player) {
        boolean status = false;

        for (Group group : GROUP_LIST)
            status |= group.removePlayer(player);
        return status;
    }

    public static boolean removePlayerGroup(final UUID uuid) {
        boolean status = false;

        for (Group group : GROUP_LIST)
            status |= group.removePlayer(uuid);
        return status;
    }

    public static boolean removeGroup(final Group group) {
        return group != null && GROUP_LIST.remove(group);
    }

    public static Group createGroup(final ProxiedPlayer owner, final String groupName) {
        Group result;

        if (owner == null || groupName == null)
            return null;
        removePlayerGroup(owner);
        result = new Group(owner, groupName);
        return GROUP_LIST.add(result) ? result : null;
    }

    public static Group createGroup(final UUID ownUuid, final String groupName) {
        Group result;

        if (ownUuid == null || groupName == null)
            return null;
        removePlayerGroup(ownUuid);
        result = new Group(ownUuid, groupName);
        return GROUP_LIST.add(result) ? result : null;
    }
}
