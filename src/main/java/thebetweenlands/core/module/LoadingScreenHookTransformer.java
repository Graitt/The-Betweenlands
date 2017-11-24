package thebetweenlands.core.module;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class LoadingScreenHookTransformer extends TransformerModule {
	public LoadingScreenHookTransformer() {
		this.addAcceptedClass("N/A", "net.minecraft.client.LoadingScreenRenderer");
	}

	@Override
	public String getName() {
		return "LoadingScreenHook";
	}

	@Override
	public boolean acceptsMethod(MethodNode method) {
		return method.name.equals(this.getMappedName("N/A", "setLoadingProgress")) && method.desc.equals("(I)V");
	}

	@Override
	public int transformMethodInstruction(MethodNode method, AbstractInsnNode node, int index) {
		if(node.getOpcode() == Opcodes.ILOAD && node instanceof VarInsnNode && ((VarInsnNode) node).var == 1 && index < method.instructions.size() - 1) {
			if(method.instructions.get(index + 1).getOpcode() == Opcodes.IFLT) {
				List<AbstractInsnNode> insertions = new ArrayList<AbstractInsnNode>();

				//Call ClientHooks#onRenderLoadingScreen()
				insertions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "thebetweenlands/client/ClientHooks", "onRenderLoadingScreen", "()V", false));

				this.insertBefore(method, node, insertions);

				this.setSuccessful();

				return 1;
			}
		}
		return 0;
	}
}
