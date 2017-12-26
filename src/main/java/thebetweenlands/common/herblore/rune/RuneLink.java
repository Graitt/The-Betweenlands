package thebetweenlands.common.herblore.rune;

import thebetweenlands.api.herblore.rune.IRuneChain;
import thebetweenlands.api.herblore.rune.IRuneLink;

public class RuneLink implements IRuneLink {
	private final int outputRuneSlot, outputMarkIndex, inputRuneSlot, inputMarkIndex;
	private final IRuneChain chain;

	public RuneLink(IRuneChain chain, int outputRuneSlot, int outputMarkIndex, int inputRuneSlot, int inputMarkIndex) {
		this.chain = chain;
		this.outputRuneSlot = outputRuneSlot;
		this.outputMarkIndex = outputMarkIndex;
		this.inputRuneSlot = inputRuneSlot;
		this.inputMarkIndex = inputMarkIndex;
	}

	@Override
	public IRuneChain getChain() {
		return this.chain;
	}

	@Override
	public int getOutputRuneSlot() {
		return this.outputRuneSlot;
	}

	@Override
	public int getOutputMarkIndex() {
		return this.outputMarkIndex;
	}

	@Override
	public int getInputRuneSlot() {
		return this.inputRuneSlot;
	}

	@Override
	public int getInputMarkIndex() {
		return this.inputMarkIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chain == null) ? 0 : chain.hashCode());
		result = prime * result + inputMarkIndex;
		result = prime * result + inputRuneSlot;
		result = prime * result + outputMarkIndex;
		result = prime * result + outputRuneSlot;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuneLink other = (RuneLink) obj;
		if (chain == null) {
			if (other.chain != null)
				return false;
		} else if (!chain.equals(other.chain))
			return false;
		if (inputMarkIndex != other.inputMarkIndex)
			return false;
		if (inputRuneSlot != other.inputRuneSlot)
			return false;
		if (outputMarkIndex != other.outputMarkIndex)
			return false;
		if (outputRuneSlot != other.outputRuneSlot)
			return false;
		return true;
	}
}
