package net.shadowmage.ancientwarfare.npc.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemCoin extends ItemBaseNPC {
	public ItemCoin() {
		super("coin");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		Arrays.stream(CoinMetal.values()).forEach(metal -> {
					ItemStack subItem = new ItemStack(this);
					subItem.setTagInfo("metal", new NBTTagString(metal.getName()));
					items.add(subItem);
				}
		);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + getMetalName(stack);
	}

	public static CoinMetal getMetal(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return CoinMetal.COPPER;
		}
		return CoinMetal.byName(getMetalName(stack));
	}

	private static String getMetalName(ItemStack stack) {
		//noinspection ConstantConditions
		return stack.hasTagCompound() ? stack.getTagCompound().getString("metal") : "";
	}

	public enum CoinMetal {
		GOLD("gold", 0xFFD700),
		SILVER("silver", 0xC0C0C0),
		COPPER("copper", 0xB87333);

		private String name;
		private int color;

		CoinMetal(String name, int color) {
			this.name = name;
			this.color = color;
		}

		public String getName() {
			return name;
		}

		private static Map<String, CoinMetal> values = new HashMap<>();

		static {
			Arrays.stream(values()).forEach(m -> values.put(m.getName(), m));
		}

		public static CoinMetal byName(String name) {
			return values.get(name);
		}

		public int getColor() {
			return color;
		}
	}
}
