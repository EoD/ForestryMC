/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.plugins;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.IGuiHandler;

import forestry.api.core.PluginInfo;
import forestry.api.food.BeverageManager;
import forestry.api.food.IBeverageEffect;
import forestry.core.config.Config;
import forestry.core.config.Defaults;
import forestry.core.config.ForestryItem;
import forestry.core.interfaces.IOreDictionaryHandler;
import forestry.core.interfaces.ISaveEventHandler;
import forestry.core.items.ItemForestryFood;
import forestry.core.proxy.Proxies;
import forestry.core.utils.LiquidHelper;
import forestry.food.BeverageEffect;
import forestry.food.GuiHandlerFood;
import forestry.food.items.ItemAmbrosia;
import forestry.food.items.ItemBeverage;
import forestry.food.items.ItemBeverage.BeverageInfo;
import forestry.food.items.ItemInfuser;

@PluginInfo(pluginID = "Food", name = "Food", author = "SirSengir", url = Defaults.URL, description = "Adds food.")
public class PluginFood extends NativePlugin {

	@Override
	public boolean isAvailable() {
		return !Config.disableFood;
	}

	@Override
	public void preInit() {
		super.preInit();

		// Init seasoner
		BeverageManager.infuserManager = new ItemInfuser.MixtureManager();
		BeverageManager.ingredientManager = new ItemInfuser.IngredientManager();
	}

	@Override
	public void postInit() {
		super.postInit();

		ItemInfuser.initialize();
	}

	@Override
	protected void registerItems() {
		// / FOOD ITEMS
		ForestryItem.honeyedSlice.registerItem(new ItemForestryFood(8, 0.6f), "honeyedSlice");
		ForestryItem.beverage.registerItem(new ItemBeverage(
				new BeverageInfo("meadShort", "glass", 0xec9a19, 0xffffff, 1, 0.2f, true),
				new BeverageInfo("meadCurative", "glass", 0xc5feff, 0xffffff, 1, 0.2f, true)),
				"beverage");
		ForestryItem.ambrosia.registerItem(new ItemAmbrosia().setIsDrink(), "ambrosia");
		ForestryItem.honeyPot.registerItem(new ItemForestryFood(2, 0.2f).setIsDrink(), "honeyPot");

		// / SEASONER
		ForestryItem.infuser.registerItem(new ItemInfuser(), "infuser");

		// Mead
		ItemStack meadBottle = ForestryItem.beverage.getItemStack();
		((ItemBeverage) ForestryItem.beverage.item()).beverages[0].saveEffects(meadBottle, new IBeverageEffect[] { BeverageEffect.weakAlcoholic });

		LiquidHelper.getOrCreateLiquid(Defaults.LIQUID_MEAD);
		LiquidHelper.injectLiquidContainer(Defaults.LIQUID_MEAD, Defaults.BUCKET_VOLUME, meadBottle, new ItemStack(Items.glass_bottle));
	}

	@Override
	protected void registerBackpackItems() {
	}

	@Override
	protected void registerRecipes() {
		// INFUSER
		Proxies.common.addRecipe(ForestryItem.infuser.getItemStack(),
				"X", "#", "X",
				'#', Items.iron_ingot,
				'X', "ingotBronze");
	}

	@Override
	protected void registerCrates() {
	}

	@Override
	public IGuiHandler getGuiHandler() {
		return new GuiHandlerFood();
	}

	@Override
	public ISaveEventHandler getSaveEventHandler() {
		return null;
	}

	@Override
	public IOreDictionaryHandler getDictionaryHandler() {
		return null;
	}
}
