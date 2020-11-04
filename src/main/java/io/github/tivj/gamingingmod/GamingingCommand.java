package io.github.tivj.gamingingmod;

import club.sk1er.mods.core.ModCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class GamingingCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "gamingingmod";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        ModCore.getInstance().getGuiHandler().open(GamingingMod.INSTANCE.config.gui());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
