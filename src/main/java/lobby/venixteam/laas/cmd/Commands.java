package lobby.venixteam.laas.cmd;

import lobby.venixteam.laas.Main;
import lobby.venixteam.laas.cmd.lobby.BuilderModeCommand;
import lobby.venixteam.laas.cmd.lobby.SetLobbyCommand;

public class Commands {

    public static Commands instanceCommands;

    public static Commands getInstanceCommands() {
        Main.getInstance().getCommand("setlobby").setExecutor(new SetLobbyCommand());
        Main.getInstance().getCommand("build").setExecutor(new BuilderModeCommand());
        return instanceCommands;
    }
}
