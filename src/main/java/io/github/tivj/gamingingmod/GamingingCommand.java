package io.github.tivj.gamingingmod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.modcore.api.ModCoreAPI;

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
        ModCoreAPI.getGuiUtil().openScreen(GamingingMod.INSTANCE.config.gui());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
