package thebetweenlands.common.herblore.aspect.type;

import net.minecraft.util.ResourceLocation;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.common.lib.ModInfo;

public class AspectFreiwynn implements IAspectType {
	@Override
	public String getName() {
		return "Freiwynn";
	}

	@Override
	public String getType() {
		return "Vision";
	}

	@Override
	public String getDescription() {
		return "Alters the player's vision. (In combination with other properties. So for example when you combine health with vision, you would be able to spot mobs their health.)";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(ModInfo.ID, "textures/items/strictly_herblore/misc/aspect_freiwynn.png");
	}
}
