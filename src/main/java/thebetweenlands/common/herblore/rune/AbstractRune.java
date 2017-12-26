package thebetweenlands.common.herblore.rune;

import java.util.Optional;

import net.minecraft.util.math.MathHelper;
import thebetweenlands.api.herblore.aspect.Aspect;
import thebetweenlands.api.herblore.aspect.AspectContainer;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.IRune;
import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.api.herblore.rune.RuneType;

public abstract class AbstractRune implements IRune {
	protected final IAspectType type;

	protected IRuneChain chain;
	protected int slot;

	protected AspectContainer aspects = new AspectContainer();

	public AbstractRune(IAspectType type) {
		this.type = type;
	}

	@Override
	public boolean branch() {
		//Currently only predicate runes should branch off
		return this.getType() == RuneType.PREDICATE;
	}

	@Override
	public IRuneChain getChain() {
		return this.chain;
	}

	@Override
	public int getChainSlot() {
		return this.slot;
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
	public boolean canActivate(IRuneMarkContainer marks) {
		IRuneMarkContainer requirements = this.getRequiredRuneMarks();
		return requirements.isApplicable(marks);
	}

	@Override
	public int drain(int amount, boolean conversion) {
		int drained = this.aspects.drain(this.type, conversion ? MathHelper.floor(amount * this.getFillRatio()) : amount);
		if(conversion) {
			return MathHelper.floor(drained / this.getFillRatio());
		}
		return drained;
	}

	@Override
	public int fill(int amount, boolean conversion) {
		int input = amount;
		if(conversion) {
			amount = MathHelper.floor(amount * this.getFillRatio());
		}
		int diff = this.getBufferSize() - this.aspects.get(this.type);
		amount = Math.min(amount, diff);
		this.aspects.add(this.type, amount);
		if(conversion) {
			return amount = MathHelper.floor(amount / this.getFillRatio()) + (amount > 0 ? MathHelper.floor(input * this.getFillRatio()) : 0);
		}
		return amount;
	}

	@Override
	public Aspect getAspect() {
		return this.aspects.getAspect(this.type);
	}

	@Override
	public IRuneMarkContainer generateRuneMarks(Optional<IRuneMarkContainer> marks) {
		if(this.getType() == RuneType.MARK) {
			return this.provideRuneMarks(marks);
		}
		return RuneMarkContainer.EMPTY;
	}

	/**
	 * @see IRune#generateRuneMarks(Optional)
	 * @param marks
	 * @return
	 */
	protected IRuneMarkContainer provideRuneMarks(Optional<IRuneMarkContainer> marks) {
		return RuneMarkContainer.EMPTY;
	}
}
