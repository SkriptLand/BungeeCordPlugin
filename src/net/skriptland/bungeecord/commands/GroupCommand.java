package net.skriptland.bungeecord.commands;

import fr.mrcubee.langlib.Lang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

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
        /* TODO Complete this method for create player's team. */
    }

    /** Remove current team subcommand.
     * @param commandSender Player that executes this command.
     * @param args Command arguments (without "/group remove" arguments)
     */
    private void remove(ProxiedPlayer commandSender, String[] args) {
        /* TODO Complete this method for remove current player's team. */
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
        /* TODO Complete this method for leave/remove current player's team. */
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
