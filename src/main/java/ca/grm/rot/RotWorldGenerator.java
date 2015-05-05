package ca.grm.rot;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import ca.grm.rot.blocks.RotBlocks;

import com.google.common.base.Predicate;

public class RotWorldGenerator implements IWorldGenerator{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionId()) {
        case 0: GenerateOverworld(random, chunkX * 16, chunkZ * 16, world); break;
        case 1: GenerateEnd(random, chunkX * 16, chunkZ * 16, world); break;
        case -1: GenerateNether(random, chunkX * 16, chunkZ * 16, world); break;	
	}
}

    public void addOreSpawn(Block block, Block replace, Random random, World world, int blockXPos, int blockZPos, int minY, int maxY, int minVeinSize, int maxVeinSize, int chancesToSpawn )
    {
        WorldGenMinable minable = new WorldGenMinable(block.getDefaultState(), (minVeinSize + random.nextInt(maxVeinSize - minVeinSize)), BlockHelper.forBlock(replace));
        for(int i = 0; i < chancesToSpawn; i++)
        {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            BlockPos minableBlockPos = new BlockPos(posX, posY, posZ);
            minable.generate(world, random, minableBlockPos);
        }
    }
	
	private void GenerateNether(Random random, int i, int j, World world) {
		// TODO Auto-generated method stub
		
	}

	private void GenerateEnd(Random random, int i, int j, World world) {
		// TODO Auto-generated method stub
		
	}

	private void GenerateOverworld(Random random, int i, int j, World world) {
		// TODO Auto-generated method stub
		 this.addOreSpawn(RotBlocks.copperOre, Blocks.stone, random, world, i, j, 0, 64, 4, 10, 20);
		 this.addOreSpawn(RotBlocks.silverOre, Blocks.stone, random, world, i, j, 0, 48, 1, 5, 10);
		 this.addOreSpawn(RotBlocks.leadOre, Blocks.stone, random, world, i, j, 0, 48, 3, 8, 12);
		 this.addOreSpawn(RotBlocks.platinumOre, Blocks.stone, random, world, i, j, 0, 16, 3, 6, 2);
		 this.addOreSpawn(RotBlocks.tinOre, Blocks.stone, random, world, i, j, 0, 64, 4, 10, 20);
		 this.addOreSpawn(RotBlocks.galenaOre, Blocks.stone, random, world, i, j, 0, 48, 1, 5, 7);
		 
		 this.addOreSpawn(RotBlocks.amethystSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 2);
		 this.addOreSpawn(RotBlocks.diamondSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 2);
		 this.addOreSpawn(RotBlocks.emeraldSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 2);
		 this.addOreSpawn(RotBlocks.rubySand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 2);
		 this.addOreSpawn(RotBlocks.topazSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 2);
	}
}