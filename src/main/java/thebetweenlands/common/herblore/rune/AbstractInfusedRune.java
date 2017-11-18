package thebetweenlands.common.herblore.rune;

import net.minecraft.util.math.MathHelper;
import thebetweenlands.api.herblore.aspect.Aspect;
import thebetweenlands.api.herblore.aspect.AspectContainer;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.IInfusedRune;
import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneMark;

public abstract class AbstractInfusedRune implements IInfusedRune {
	protected final IAspectType type;

	protected IRuneChain chain;
	protected int slot;

	protected AspectContainer aspects = new AspectContainer();

	public AbstractInfusedRune(IAspectType type) {
		this.type = type;
	}

	@Override
	public void cleanup() {
		
	}
	
	@Override
	public void setChain(IRuneChain chain, int slot) {
		this.chain = chain;
		this.slot = slot;

		//Make sure buffer has the correct size after setting chain
		this.updateBufferSize();
	}

	protected void updateBufferSize() {
		int bufferSize = this.getBufferSize();
		if(this.aspects.get(this.type) > bufferSize) {
			this.aspects.set(this.type, bufferSize);
		}
	}

	@Override
	public boolean canActivate(IRuneMark[] marks) {
		IRuneMark<?, ?>[] requirements = this.getRequiredRuneMarks();
		if(marks.length == requirements.length) {
			for(int i = 0; i < requirements.length; i++) {
				if(!requirements[i].isApplicable(marks[i]) || !marks[i].isValid()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int drain(int amount, boolean conversion) {
		return this.aspects.drain(this.type, conversion ? MathHelper.floor(amount * this.getFillRatio()) : amount);
	}

	@Override
	public int fill(int amount, boolean conversion) {
		if(conversion) {
			amount = MathHelper.floor(amount * this.getFillRatio());
		}
		int diff = this.getBufferSize() - this.aspects.get(this.type);
		amount = Math.min(amount, diff);
		this.aspects.add(this.type, amount);
		return amount;
	}

	@Override
	public Aspect getAspect() {
		return this.aspects.getAspect(this.type);
	}
}
