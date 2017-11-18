package thebetweenlands.common.herblore.aspect.type;

import net.minecraft.util.ResourceLocation;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.common.lib.ModInfo;

public class AspectDayuniis implements IAspectType {
	@Override
	public String getName() {
		return "Dayuniis";
	}

	@Override
	public String getType() {
		return "Mind";
	}

	@Override
	public String getDescription() {
		return "Has effect on the player's mind and on how senses work. Could be positive, or negative (think nausea/schizophrenia).";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(ModInfo.ID, "textures/items/strictly_herblore/misc/aspect_dayuniis.png");
	}
}
