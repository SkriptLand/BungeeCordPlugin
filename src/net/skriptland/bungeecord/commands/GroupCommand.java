package net.skriptland.bungeecord.commands;

import fr.mrcubee.langlib.Lang;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.skriptland.bungeecord.group.Group;
import net.skriptland.bungeecord.group.Groups;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author MrCubee
 * @since 1.0
 */
public class GroupCommand extends Command {

    public GroupCommand() {
        super("group");
    }

    /** Create new team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group create" arguments)
     */
    private void create(ProxiedPlayer commandSender, String[] args) {
        Group group;

        if (args == null) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /group create <name>");
            return;
        }
        group = Groups.createGroup(commandSender, args[0]);
        if (group == null) {
            commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.create.error.name", "&cThe name is already taken.", true));
            return;
        }
        commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.create.success", "&aThe %s group has been created.", true, group.getName()));
    }

    /** Remove current team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group remove" arguments)
     */
    private void remove(ProxiedPlayer commandSender, String[] args) {
        Group group = Groups.getPlayerGroup(commandSender);

        if (group == null) {
            commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.remove.error.must_in_group", "&cYou must be in a group.", true));
            return;
        }
        if (!Groups.removeGroup(group)) {
            commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.remove.error.must_be_owner", "&cYou must be the owner of your group.", true));
            return;
        }
        commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.remove.success", "&aThe %s group has been deleted.", true, group.getName()));
    }

    /** Invite another player in current team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group remove" arguments)
     */
    private void invite(ProxiedPlayer commandSender, String[] args) {
        /* TODO Complete this method for invite another player in current team. */
    }

    /** Join player's team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group join" arguments)
     */
    private void join(ProxiedPlayer commandSender, String[] args) {
        /* TODO Complete this method for join player's team. */
    }

    /** Leave/Remove current team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group leave" arguments)
     */
    private void leave(ProxiedPlayer commandSender, String[] args) {
        if (!Groups.removePlayerGroup(commandSender)) {
            commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.leave.error.must_in_group", "&cYou must be in a group.", true));
            return;
        }
        commandSender.sendMessage(Lang.getMessage(commandSender, "command.group.leave.success", "&aYou left the group.", true));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String category;
        String[] categoryArguments = null;
        Method method;

        if (args.length < 1)
            return;
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(Lang.getMessage("command.must_be_player","&cYou must be a Player for execute this command.", true));
            return;
        }
        category = args[0].toLowerCase();
        if (args.length > 1) {
            categoryArguments = new String[args.length - 1];
            System.arraycopy(args, 1, categoryArguments, 0, categoryArguments.length);
        }
        try {
            method = GroupCommand.class.getDeclaredMethod(category, ProxiedPlayer.class, String[].class);
            method.invoke(this, (ProxiedPlayer) commandSender, categoryArguments);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            commandSender.sendMessage(Lang.getMessage(commandSender, "command.error.subcommand_not_found", "&cError", true));
        }
    }
}
