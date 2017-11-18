package thebetweenlands.common.herblore.rune;

import javax.annotation.Nullable;

import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.rune.IInfusedRune;
import thebetweenlands.api.herblore.rune.IRune;
import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.RuneType;

public class RuneChain implements IRuneChain {
	protected IInfusedRune[] catalystRunes;
	protected IInfusedRune[] effectRunes;
	protected IInfusedRune[] allRunes;

	protected int currentEffectRune = 0;

	protected boolean active = false;

	protected float runeActivationCooldown = 1;

	@Override
	public void cleanup() {
		for(IInfusedRune rune : this.allRunes) {
			rune.cleanup();
		}
	}

	@Override
	public void update() {
		for(IInfusedRune rune : this.allRunes) {
			rune.update();
		}

		if(this.isActive()) {
			this.updateActiveChain();
		}
	}

	@Override
	public void activate() {
		if(this.effectRunes.length == 0) {
			return;
		}
		this.runeActivationCooldown = 1;
		this.active = true;
		this.currentEffectRune = 0;
	}

	protected void updateActiveChain() {
		this.runeActivationCooldown--;

		while(this.isActive() && Math.abs(this.runeActivationCooldown) >= 0.001F) {
			IInfusedRune pendingRune = this.effectRunes[this.currentEffectRune];

			if(pendingRune.getAspect().amount >= pendingRune.getCost()) {

				//TODO: Rune activation logic

				pendingRune.drain(pendingRune.getCost(), false);
			}

			this.runeActivationCooldown += pendingRune.getDuration();
		}
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void cancel(boolean refund) {
		this.active = false;

		//TODO: Implement refund
	}

	@Override
	public int getPendingRune() {
		return this.catalystRunes.length + this.currentEffectRune;
	}

	@Override
	public void isCatalystActive() {
		//TODO: Catalyst logic
	}

	@Override
	public boolean addRune(IRune rune, IAspectType type) {
		return this.addRune(rune, type, this.allRunes.length);
	}

	@Override
	public boolean addRune(IRune rune, IAspectType type, int slot) {
		if(slot < 0 || slot >= this.allRunes.length) {
			return false;
		}
		IInfusedRune newRune = rune.infuse(type);
		if(newRune.getType() == RuneType.CATALYST) {
			IInfusedRune[] catalysts = new IInfusedRune[this.catalystRunes.length + 1];
			for(int i = 0; i < this.catalystRunes.length + 1; i++) {
				if(i < slot) {
					catalysts[i] = this.catalystRunes[i];
				} else if(i == slot) {
					catalysts[i] = newRune;
				} else {
					catalysts[i] = this.catalystRunes[i + 1];
				}
			}
			this.catalystRunes = catalysts;
		} else {
			slot -= this.catalystRunes.length;
			IInfusedRune[] effectRunes = new IInfusedRune[this.effectRunes.length + 1];
			for(int i = 0; i < this.effectRunes.length + 1; i++) {
				if(i < slot) {
					effectRunes[i] = this.effectRunes[i];
				} else if(i == slot) {
					effectRunes[i] = newRune;
				} else {
					effectRunes[i] = this.effectRunes[i + 1];
				}
			}
			this.effectRunes = effectRunes;
		}
		this.allRunes = new IInfusedRune[this.allRunes.length + 1];
		System.arraycopy(this.catalystRunes, 0, this.allRunes, 0, this.catalystRunes.length);
		System.arraycopy(this.effectRunes, 0, this.allRunes, this.catalystRunes.length, this.effectRunes.length);
		return true;
	}

	@Override
	@Nullable
	public IInfusedRune removeRune(int slot) {
		if(slot < 0 || slot >= this.allRunes.length) {
			return null;
		}
		IInfusedRune rune = this.getRune(slot);
		if(rune.getType() == RuneType.CATALYST) {
			IInfusedRune[] catalysts = new IInfusedRune[this.catalystRunes.length - 1];
			for(int i = 0; i < this.catalystRunes.length - 1; i++) {
				if(i < slot) {
					catalysts[i] = this.catalystRunes[i];
				} else {
					catalysts[i] = this.catalystRunes[i + 1];
				}
			}
			this.catalystRunes = catalysts;
		} else {
			slot -= this.catalystRunes.length;
			IInfusedRune[] effectRunes = new IInfusedRune[this.effectRunes.length - 1];
			for(int i = 0; i < this.effectRunes.length - 1; i++) {
				if(i < slot) {
					effectRunes[i] = this.effectRunes[i];
				} else {
					effectRunes[i] = this.effectRunes[i + 1];
				}
			}
			this.effectRunes = effectRunes;
		}
		this.allRunes = new IInfusedRune[this.allRunes.length + 1];
		System.arraycopy(this.catalystRunes, 0, this.allRunes, 0, this.catalystRunes.length);
		System.arraycopy(this.effectRunes, 0, this.allRunes, this.catalystRunes.length, this.effectRunes.length);
		return rune;
	}

	@Override
	public IInfusedRune[] getRunes() {
		return this.allRunes;
	}

	@Override
	@Nullable
	public IInfusedRune getRune(int slot) {
		if(slot < 0 || slot >= this.allRunes.length) {
			return null;
		}
		return this.allRunes[slot];
	}

	@Override
	public IInfusedRune[] getLinkedRunes(int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuneMark[] getRuneMarks(int slot) {
		// TODO Auto-generated method stub
		return null;
	}
}
