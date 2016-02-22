package thebetweenlands.world.feature.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.blocks.BlockBLSpawner;

public class WorldGenWightTower extends WorldGenerator {

    private int length = -1;
    private int width = -1;
    private int height = -1;
    private int direction = -1;
    private Block bricks = BLBlockRegistry.limestoneBricks;
    private Block slab = BLBlockRegistry.limestoneBrickSlab;
    private Block stairs = BLBlockRegistry.limestoneBrickStairs;
    private Block polished = BLBlockRegistry.polishedLimestone;
    private Block wall = BLBlockRegistry.limestoneBrickWall;
    private BlockBLSpawner spawner = BLBlockRegistry.blSpawner;
    
    public WorldGenWightTower() {
    	//these sizes are subject to change
        length = 13;
        width = 13;
        height = 19;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return generateStructure(world, random, x, y, z);
    }

    public boolean generateStructure(World world, Random rand, int xx, int yy, int zz) {
      // air just to erase old one :P
		for (int xa = xx; xa <= xx + 32; ++xa) {
            for(int za = zz; za <= zz + 32; ++za) {
                for(int ya = yy; ya < yy + 32; ++ya ) {
                	world.setBlockToAir(xa, ya, za);
                }
            }
        }

		//TODO prepare for decoration and block placing

		//ground floors
		length = 32;
        width = 32;
		for (direction = 0; direction < 4; direction++) {
			rotatedCubeVolume(world, rand, xx, yy, zz, 1, -1, 1, polished, 0, 11, 1, 11, direction);
			rotatedCubeVolume(world, rand, xx, yy, zz, 6, 2, 6, spawner, 0, 1, 1, 1, direction);
			spawner.setMob(world, xx + 6, yy + 2, zz + 6, "thebetweenlands.swampHag");
			spawner.setMob(world, xx + 25, yy + 2, zz + 6, "thebetweenlands.swampHag");
			spawner.setMob(world, xx + 25, yy + 2, zz + 25, "thebetweenlands.swampHag");
			spawner.setMob(world, xx + 6, yy + 2, zz + 25, "thebetweenlands.swampHag");

		//1st floors
			rotatedCubeVolume(world, rand, xx, yy, zz, 3, 5, 3, polished, 0, 7, 1, 7, direction);
			rotatedCubeVolume(world, rand, xx, yy, zz, 6, 7, 6, spawner, 0, 1, 1, 1, direction);
			spawner.setMob(world, xx + 6, yy + 7, zz + 6, "thebetweenlands.wight");
			spawner.setMob(world, xx + 25, yy + 7, zz + 6, "thebetweenlands.wight");
			spawner.setMob(world, xx + 25, yy + 7, zz + 25, "thebetweenlands.wight");
			spawner.setMob(world, xx + 6, yy + 7, zz + 25, "thebetweenlands.wight");

		//2nd floors
			rotatedCubeVolume(world, rand, xx, yy, zz, 4, 11, 4, polished, 0, 5, 1, 5, direction);
			rotatedCubeVolume(world, rand, xx, yy, zz, 6, 13, 6, spawner, 0, 1, 1, 1, direction);
			spawner.setMob(world, xx + 6, yy + 13, zz + 6, "thebetweenlands.termite");
			spawner.setMob(world, xx + 25, yy + 13, zz + 6, "thebetweenlands.termite");
			spawner.setMob(world, xx + 25, yy + 13, zz + 25, "thebetweenlands.termite");
			spawner.setMob(world, xx + 6, yy + 13, zz + 25, "thebetweenlands.termite");

		//3rd floors
			rotatedCubeVolume(world, rand, xx, yy, zz, 4, 16, 4, polished, 0, 5, 1, 5, direction);
			rotatedCubeVolume(world, rand, xx, yy, zz, 6, 18, 6, spawner, 0, 1, 1, 1, direction);
			spawner.setMob(world, xx + 6, yy + 18, zz + 6, "thebetweenlands.wight");
			spawner.setMob(world, xx + 25, yy + 18, zz + 6, "thebetweenlands.wight");
			spawner.setMob(world, xx + 25, yy + 18, zz + 25, "thebetweenlands.wight");
			spawner.setMob(world, xx + 6, yy + 18, zz + 25, "thebetweenlands.wight");
		}

		length = 13;
		width = 13;
		for (int tower = 0; tower  < 5; tower ++) {
			int x = xx, y = yy, z = zz;

    		if (tower == 1)
    			x = xx + 19;

    		if (tower == 2) {
    		    x = xx + 19;
    		    z = zz + 19;
    		}

    		if (tower == 3)
    		    z = zz + 19;

    		for (direction = 0; direction < 4; direction++) {
    			if(tower < 4) {
    			rotatedCubeVolume(world, rand, x, y, z, 0, 0, 1, bricks, 0, 1, 3, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 0, 0, 3, bricks, 0, 1, 3, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 1, 0, 1, bricks, 0, 1, 4, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 1, 0, 2, bricks, 0, 1, 6, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 2, 0, 1, bricks, 0, 1, 6, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 4, 0, 1, bricks, 0, 1, 8, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 8, 0, 1, bricks, 0, 1, 8, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 2, 4, 2, bricks, 0, 1, 3, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 2, 4, 3, bricks, 0, 1, 4, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 3, 4, 2, bricks, 0, 1, 4, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 3, 3, 1, bricks, 0, 5, 1, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 1, 3, 3, bricks, 0, 1, 1, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 3, 8, 3, bricks, 0, 1, 6, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 4, 8, 2, bricks, 0, 1, 11, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 8, 8, 2, bricks, 0, 1, 11, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 6, 16, 2, bricks, 0, 1, 3, 1, direction);
    			rotatedCubeVolume(world, rand, x, y, z, 5, 17, 2, bricks, 0, 1, 1, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 7, 17, 2, bricks, 0, 1, 1, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 6, 16, 3, bricks, 0, 1, 1, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 4, 10, 3, bricks, 0, 1, 8, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 8, 10, 3, bricks, 0, 1, 8, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 5, 10, 3, bricks, 0, 1, 7, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 7, 10, 3, bricks, 0, 1, 7, 1, direction);
				rotatedCubeVolume(world, rand, x, y, z, 1, 0, 0, bricks, 0, 1, 3, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 3, 0, 0, bricks, 0, 1, 3, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 5, 0, 0, bricks, 0, 1, 3, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 7, 0, 0, bricks, 0, 1, 3, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 4, 5, 2, bricks, 0, 2, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 7, 5, 2, bricks, 0, 2, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 5, 6, 2, bricks, 0, 1, 3, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 7, 6, 2, bricks, 0, 1, 3, 1, direction);

	        	rotatedCubeVolume(world, rand, x, y, z, 6, 15, 3, polished, 0, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 14, 3, slab, 8, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 9, 2, polished, 0, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 8, 2, slab, 8, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 3, 0, polished, 0, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 2, 0, slab, 8, 1, 1, 1, direction);

	        	rotatedCubeVolume(world, rand, x, y, z, 1, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 3, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 5, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 7, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 5, 9, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 7, 9, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 5, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 0, 3, 1, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 0, 3, 3, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 2, 8, 3, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 3, 8, 2, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 4, 8, 1, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 8, 8, 1, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
	        	rotatedCubeVolume(world, rand, x, y, z, 6, 11, 4, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);

	        	//walkways
	        	if (tower == 0 && direction == 0 || tower == 0 && direction == 1 || tower == 1 && direction == 0 || tower == 1 && direction == 3|| tower == 2 && direction == 2 || tower == 2 && direction == 3|| tower == 3 && direction == 1 || tower == 3 && direction == 2) {
	        		rotatedCubeVolume(world, rand, x, y, z, 5, 4, 11, bricks, 0, 3, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 5, 12, bricks, 0, 1, 1, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 5, 12, bricks, 0, 1, 1, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 5, 4, 1, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 3, 1, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 6, 10, 3, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);

	        		rotatedCubeVolume(world, rand, x, y, z, 4, 6, 12, wall, 0, 1, 1, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 6, 12, wall, 0, 1, 1, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 7, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 7, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 9, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 9, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 10, 11, wall, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 10, 11, wall, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 5, 10, 9, bricks, 0, 3, 1, 7, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 11, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 11, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 12, 11, wall, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 12, 11, wall, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 13, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 13, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 15, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 15, 11, bricks, 0, 1, 1, 5, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 5, 16, 11, bricks, 0, 3, 1, 5, direction);
				}

	        	//top Floor
	        	if (tower == 0 && direction == 0 || tower == 1 && direction == 3 || tower == 2 && direction == 2 || tower == 3 && direction == 1) {
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 16, 11, bricks, 0, 1, 3, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 7, 17, 15, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 17, 15, bricks, 0, 3, 1, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 9, 17, 14, bricks, 0, 3, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 19, 10, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 19, 12, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 19, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 8, 21, 10, slab, 0, 1, 1, 5, direction);
	        	}
	        	if (tower == 0 && direction == 1 || tower == 1 && direction == 0 || tower == 2 && direction == 3 || tower == 3 && direction == 2) {
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 16, 11, bricks, 0, 1, 3, 4, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 5, 17, 15, stairs, direction == 0 ? 1 : direction== 1 ? 2 : direction == 2 ? 0 : 3, 1, 1, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 2, 17, 15, bricks, 0, 3, 1, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 1, 17, 14, bricks, 0, 3, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 19, 10, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 19, 12, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 19, 14, wall, 0, 1, 2, 1, direction);
	        		rotatedCubeVolume(world, rand, x, y, z, 4, 21, 10, slab, 0, 1, 1, 5, direction);
	        	}
    		}
    			//top tower
	        	if(tower == 4) {
	        		length = 14;
	                width = 14;
	      			x = xx + 9;
	    		    z = zz + 9;
	    		    y = yy + 18;
	        		generateTopTowerRight(world, rand, x, y, z, direction);
	        		generateTopTowerLeft(world, rand, x, y, z, direction);
	        	}
    		}
    	}

		//retro-gen stairs
		direction = rand.nextInt(4);
		length = 32;
        width = 32;

        //main stairs
		rotatedCubeVolume(world, rand, xx, yy, zz, 12, 0, 4, bricks, 0, 8, 4, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 0, 3, bricks, 0, 6, 3, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 0, 2, bricks, 0, 6, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 0, 1, bricks, 0, 6, 1, 1, direction);
		
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 3, 3, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 2, 2, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 1, 1, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 0, 0, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 3, 3, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 2, 2, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 1, 1, bricks, 0, 1, 2, 2, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 0, 0, bricks, 0, 1, 2, 2, direction);

		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 5, 3, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 4, 2, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 3, 1, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 2, 0, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 5, 3, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 4, 2, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 3, 1, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 2, 0, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 13, 7, 4, wall, 0, 1, 2, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 7, 4, wall, 0, 1, 2, 1, direction);

		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 0, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 1, 1, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 2, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 3, 3, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 4, 4, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 14, 5, 4, Blocks.air, 0, 4, 4, 1, direction);

		//2nd stairs
		for(int count = 0;count < 6;count ++)
			rotatedCubeVolume(world, rand, xx, yy, zz, 16 + count, 5 + count, 24, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 17, 5, 24, bricks, 0, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 6, 24, bricks, 0, 3, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 19, 7, 24, bricks, 0, 2, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 20, 8, 24, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 18, 10, 24, Blocks.air, 0, 3, 1, 1, direction);

		//3rd stairs
		for(int count = 0;count < 7;count ++)
			rotatedCubeVolume(world, rand, xx, yy, zz, 16 - count, 11 + count, 7, stairs, direction == 0 ? 1 : direction== 1 ? 2 : direction == 2 ? 0 : 3, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 11, 7, bricks, 0, 6, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 12, 7, bricks, 0, 5, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 13, 7, bricks, 0, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 14, 7, bricks, 0, 3, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 15, 7, bricks, 0, 2, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 10, 16, 7, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, xx, yy, zz, 12, 16, 7, Blocks.air, 0, 3, 1, 1, direction);

        return true;
    }

    public void rotatedCubeVolume(World world, Random rand, int x, int y, int z, int offsetA, int offsetB, int offsetC, Block blockType, int blockMeta, int sizeWidth, int sizeHeight, int sizeDepth, int direction) {
        switch (direction) {
            case 0:
            	for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
            		for (int xx = x + offsetA; xx < x + offsetA + sizeWidth; xx++)
            			for (int zz = z + offsetC; zz < z + offsetC + sizeDepth; zz++)
            				world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 1:
            	for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
            		for (int zz = z + length - offsetA - 1; zz > z + length - offsetA - sizeWidth - 1; zz--)
            			for (int xx = x + offsetC; xx < x + offsetC + sizeDepth; xx++)
            				world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 2:
            	for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
            		for (int xx = x + length - offsetA - 1; xx > x + length - offsetA - sizeWidth - 1; xx--)
            			for (int zz = z + length - offsetC - 1; zz > z + length - offsetC - sizeDepth - 1; zz--)
            				world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 3:
            	for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
            		for (int zz = z + offsetA; zz < z + offsetA + sizeWidth; zz++)
            			for (int xx = x + length - offsetC - 1; xx > x + length - offsetC - sizeDepth - 1; xx--)
            				world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
        }
    }

    public void generateTopTowerRight(World world, Random rand, int x, int y, int z, int direction) {		   
    	rotatedCubeVolume(world, rand, x, y, z, 0, 0, 1, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 0, 0, 3, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, 1, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, 2, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 0, 1, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 0, 1, bricks, 0, 1, 8, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 4, 2, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 4, 3, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 4, 2, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 3, 1, bricks, 0, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 3, 3, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 8, 3, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 8, 2, bricks, 0, 1, 11, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 16, 2, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 17, 2, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 16, 3, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 10, 3, bricks, 0, 1, 8, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 10, 3, bricks, 0, 1, 7, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, 0, bricks, 0, 1, 3, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 3, 0, 0, bricks, 0, 1, 3, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 5, 0, 0, bricks, 0, 1, 3, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 4, 5, 2, bricks, 0, 2, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 5, 6, 2, bricks, 0, 1, 3, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 15, 3, polished, 0, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 14, 3, slab, 8, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 9, 2, polished, 0, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 8, 2, slab, 8, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 3, 0, polished, 0, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 2, 0, slab, 8, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 1, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 3, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 5, 3, 0, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 5, 9, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 6, 5, 2, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 0, 3, 1, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
    	rotatedCubeVolume(world, rand, x, y, z, 0, 3, 3, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);    	
    	rotatedCubeVolume(world, rand, x, y, z, 5, 4, 1, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 10, 3, stairs, direction == 0 ? 2 : direction== 1 ? 0 : direction == 2 ? 3 : 1, 2, 1, 1, direction);
    }
    
    public void generateTopTowerLeft(World world, Random rand, int x, int y, int z, int direction) {
        if(direction == 2)
        	z -= 13;
        if(direction == 0)
        	z += 13;
        if(direction == 3)
        	x -= 13;
        if(direction == 1)
        	x += 13;

        rotatedCubeVolume(world, rand, x, y, z, 0, 0, -1, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 0, 0, -3, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, -1, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, -2, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 0, -1, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 0, -1, bricks, 0, 1, 8, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 4, -2, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 2, 4, -3, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 4, -2, bricks, 0, 1, 4, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 3, -1, bricks, 0, 4, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 3, -3, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 8, -3, bricks, 0, 1, 6, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 8, -2, bricks, 0, 1, 11, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 16, -2, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 17, -2, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 16, -3, bricks, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 10, -3, bricks, 0, 1, 8, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 10, -3, bricks, 0, 1, 7, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 0, 0, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 0, 0, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 0, 0, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 4, 5, -2, bricks, 0, 2, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 6, -2, bricks, 0, 1, 3, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 15, -3, polished, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 14, -3, slab, 8, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 9, -2, polished, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 8, -2, slab, 8, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 3, 0, polished, 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 2, 0, slab, 8, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 1, 3, 0, stairs, direction == 0 ? 3 : direction== 1 ? 1 : direction == 2 ? 2 : 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 3, 3, 0, stairs, direction == 0 ? 3 : direction== 1 ? 1 : direction == 2 ? 2 : 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 3, 0, stairs, direction == 0 ? 3 : direction== 1 ? 1 : direction == 2 ? 2 : 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 5, 9, -2, stairs, direction == 0 ? 3 : direction== 1 ? 1 : direction == 2 ? 2 : 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 6, 5, -2, stairs, direction == 0 ? 3 : direction== 1 ? 1 : direction == 2 ? 2 : 0, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 0, 3, -1, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
		rotatedCubeVolume(world, rand, x, y, z, 0, 3, -3, stairs, direction == 0 ? 0 : direction== 1 ? 3 : direction == 2 ? 1 : 2, 1, 1, 1, direction);
	}

}
