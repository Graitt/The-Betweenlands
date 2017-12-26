package thebetweenlands.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thebetweenlands.api.herblore.rune.DefaultRuneMarks.BlockRuneMark;
import thebetweenlands.api.herblore.rune.IRune;
import thebetweenlands.api.herblore.rune.IRuneMark;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer;
import thebetweenlands.api.herblore.rune.IRuneMarkContainer.CombinatorialIterator;
import thebetweenlands.api.herblore.rune.RuneMarkContainer;
import thebetweenlands.common.herblore.rune.RuneChain;
import thebetweenlands.common.herblore.rune.test.TestRune1;
import thebetweenlands.common.herblore.rune.test.TestRune2;
import thebetweenlands.common.herblore.rune.test.TestRune3;
import thebetweenlands.common.registries.AspectRegistry;

//MINE!!
public class TestItem extends Item {
	public TestItem() {
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!worldIn.isRemote) {
			try {

				RuneChain chain = new RuneChain(worldIn);

				IRune rune1 = new TestRune1(AspectRegistry.ARMANIIS, 8);
				IRune rune2 = new TestRune2(AspectRegistry.CELAWYNN);
				IRune rune3 = new TestRune3(AspectRegistry.CELAWYNN);

				chain.addRune(rune1);
				chain.addRune(rune3);
				chain.addRune(rune2);

				chain.linkRune(0, 0, 1, 0);
				chain.linkRune(0, 0, 2, 0);

				chain.setUser(playerIn, playerIn.getPositionEyes(1));

				rune1.fill(100000, false);
				rune2.fill(100000, false);
				rune3.fill(100000, false);

				chain.activate();

				long start = System.nanoTime();

				for(int it = 0; it < 1; it++) {
					int ticks = 60;
					for(int i = 0; i < ticks; i++) {
						//System.out.println("Tick: " + i);
						chain.update();
					}
				}

				System.out.println("Time: " + (System.nanoTime() - start) / 1000000.0F / 1);

//				start = System.nanoTime();
//
//				BlockPos pos = playerIn.getPosition();
//				
//				for(int it = 0; it < 1; it++) {
//
//					int range = 5;
//					int len = (range*2+1)*(range*2+1)*(range*2+1);
//
//					int i = 0;
//					int[][] ints = new int[1][len];
//					IRuneMark[][] marks = new IRuneMark[1][len];
//					for(int xo = -range; xo <= range; xo++) {
//						for(int yo = -range; yo <= range; yo++) {
//							for(int zo = -range; zo <= range; zo++) {
//								marks[0][i] = new BlockRuneMark(pos.add(xo, yo, zo), worldIn);
//								ints[0][i] = i;
//								i++;
//							}
//						}
//					}
//
//					IRuneMarkContainer container = new RuneMarkContainer(marks);
//
//					CombinatorialIterator cit = container.getCombinations();
//
//					while(cit.hasNext()) {
//						IRuneMarkContainer c = cit.next();
//					}
//
//					/*int i = 0;
//				while(i < marks[0].length) {
//					int t = 4 + 8 + marks[0].length + getSomeInt(i, ints);
//					i++;
//				}*/
//
//					/*for(int k = 0; k < 21*21*21; k++) {
//						IBlockState state = worldIn.getBlockState(playerIn.getPosition());
//					}*/
//				}
//
//				System.out.println("Time 2: " + (System.nanoTime() - start) / 1000000.0F / 1);

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	private int getSomeInt(int i, int[][] ints) {
		return ints[0][i] + 5 + 8 /  (1 + ints[0][i / 2]);
	}

	private int someMethod(int input) {
		return input * 50;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			/*
			WorldGenTarPoolDungeon gen = new WorldGenTarPoolDungeon();
			gen.generate(worldIn, itemRand, pos.up());
			 */
			/*
			WorldGenDruidCircle worldGenDruidCircle = new WorldGenDruidCircle();
			worldGenDruidCircle.generateStructure(worldIn, itemRand, pos.up());
			 */
			/*
            WorldGenIdolHeads head = new WorldGenIdolHeads();
            head.generate(world, itemRand, pos.up());
			 */
			/*
            WorldGenSpawnerStructure smallRuins = new WorldGenSpawnerStructure();
            smallRuins.generate(worldIn, itemRand, pos.up());
			 */

			/*
			WorldGenWightFortress fortress = new WorldGenWightFortress();
			fortress.generate(worldIn, itemRand, pos.up());
			 */
			/*
			if(player.isSneaking()) {
				BetweenlandsWorldData worldStorage = BetweenlandsWorldData.forWorld(worldIn);
				List<SharedStorage> storages = worldStorage.getSharedStorageAt(SharedStorage.class, (storage) -> {
					if(storage instanceof LocationStorage) {
						return ((LocationStorage)storage).isInside(pos);
					}
					return true;
				}, pos.getX(), pos.getZ());
				for(SharedStorage storage : storages) {
					worldStorage.removeSharedStorage(storage);
				}
			} else {
				WorldGenWightFortress fortress = new WorldGenWightFortress();
				fortress.generate(worldIn, itemRand, pos.up());
			}
			 */
			/*
			ItemAspectContainer container = ItemAspectContainer.fromItem(player.getHeldItem(hand));
			if(!player.isSneaking()) {
				container.add(AspectRegistry.AZUWYNN, 10);
				System.out.println("Added: 10");
			} else {
				System.out.println("Drained: " + container.drain(AspectRegistry.AZUWYNN, 8));
			}
			 */
			/*
			WorldGenSpawner spawner = new WorldGenSpawner();
			if(spawner.generate(worldIn, itemRand, pos)) {
				//playerIn.setHeldItem(hand, null);
			}
			 */
			/*
			WorldGenCragrockTower tower = new WorldGenCragrockTower();
			if(tower.generate(worldIn, itemRand, pos.up(8).add(8, 0, 0))) {
				//playerIn.setHeldItem(hand, null);
			}
			 */
		}

		return EnumActionResult.SUCCESS;
	}
}
