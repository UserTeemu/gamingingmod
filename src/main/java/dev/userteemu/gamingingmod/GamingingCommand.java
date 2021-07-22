package dev.userteemu.gamingingmod;

import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class GamingingCommand extends Command {
    public GamingingCommand() {
        super("gamingingmod");
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(GamingingMod.INSTANCE.config.gui());
    }
}
