package net.gtaun.shoebill.example.vm;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerCommandEvent;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.example.vm.dialog.VehicleListDialog;
import net.gtaun.shoebill.example.vm.dialog.VehicleManagerDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManager.HandlerEntry;
import net.gtaun.util.event.EventManager.HandlerPriority;

@SuppressWarnings("unused")
public class PlayerManager
{
	private Shoebill shoebill;
	private EventManager eventManager;
	private Collection<HandlerEntry> eventHandlerEntries;
	
	
	public PlayerManager(Shoebill shoebill, EventManager eventManager)
	{
		this.shoebill = shoebill;
		this.eventManager = eventManager;
		this.eventHandlerEntries = new LinkedList<>();

		eventHandlerEntries.add(eventManager.addHandler(PlayerConnectEvent.class, playerEventHandler, HandlerPriority.NORMAL));
		eventHandlerEntries.add(eventManager.addHandler(PlayerDisconnectEvent.class, playerEventHandler, HandlerPriority.NORMAL));
		eventHandlerEntries.add(eventManager.addHandler(PlayerCommandEvent.class, playerEventHandler, HandlerPriority.NORMAL));
	}
	
	public void uninitialize()
	{
		for (HandlerEntry entry : eventHandlerEntries) entry.cancel();
	}
	
	private PlayerEventHandler playerEventHandler = new PlayerEventHandler()
	{
		@Override
		public void onPlayerConnect(PlayerConnectEvent event)
		{
			
		}
		
		@Override
		public void onPlayerDisconnect(PlayerDisconnectEvent event)
		{
			
		}
		
		@Override
		public void onPlayerCommand(PlayerCommandEvent event)
		{
			Player player = event.getPlayer();
			
			String command = event.getCommand();
			String[] splits = command.split(" ", 2);
			
			String operation = splits[0].toLowerCase();
			Queue<String> args = new LinkedList<>();
			
			if (splits.length > 1)
			{
				String[] argsArray = splits[1].split(" ");
				args.addAll(Arrays.asList(argsArray));
			}
			
			switch (operation)
			{
			case "/vm":
				new VehicleManagerDialog(player, shoebill, eventManager).show();
				break;
			}
		}
	};
}
