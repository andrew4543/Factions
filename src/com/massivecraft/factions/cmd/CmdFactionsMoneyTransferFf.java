package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.cmd.req.ReqBankCommandsEnabled;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.arg.ARDouble;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivecore.money.Money;
import com.massivecraft.massivecore.util.Txt;

import org.bukkit.ChatColor;


public class CmdFactionsMoneyTransferFf extends FactionsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsMoneyTransferFf()
	{
		// Aliases
		this.addAliases("ff");

		// Args
		this.addArg(ARDouble.get(), "amount");
		this.addArg(ARFaction.get(), "faction");
		this.addArg(ARFaction.get(), "faction");

		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.MONEY_F2F.node));
		this.addRequirements(ReqBankCommandsEnabled.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		double amount = this.readArg();
		Faction from = this.readArg();
		Faction to = this.readArg();
		
		boolean success = Econ.transferMoney(msender, from, to, amount);

		if (success && MConf.get().logMoneyTransactions)
		{
			Factions.get().log(ChatColor.stripColor(Txt.parse("%s transferred %s from the faction \"%s\" to the faction \"%s\"", msender.getName(), Money.format(amount), from.describeTo(null), to.describeTo(null))));
		}
	}
	
}
