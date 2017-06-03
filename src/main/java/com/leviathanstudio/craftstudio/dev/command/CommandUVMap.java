package com.leviathanstudio.craftstudio.dev.command;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.common.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.dev.util.UVMapCreator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommandUVMap extends CommandBase {

	private static String name = "uvmap";
	private static String usage = "uvmap model";
	private static int permLevel = 0;
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return this.usage;
	}
	

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1){
			boolean succes = false;
			try{
				UVMapCreator uvc = new UVMapCreator(new ResourceLocation(args[0]));
				succes = uvc.createUVMap();
				if (!succes)
					throw new CommandException("Fail to write the file");
			}catch(CSResourceNotRegisteredException e){
				throw new CommandException("Model not registered");
			}
			if (!succes)
				throw new CommandException("Unknown error");
		}
	}
	
	@Override
	public int getRequiredPermissionLevel()
    {
        return this.permLevel;
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, GameRegistry.findRegistry(CSReadedModel.class).getKeys()) : Collections.<String>emptyList();
	}

}