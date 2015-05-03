package ca.grm.rot;

import java.util.Random;

import ca.grm.rot.blocks.RotBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class RotWorldGenerator implements IWorldGenerator{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
	    switch(world.provider.getDimensionId())
	    {
	    case 0:
	    GenerateOverWorld(world, chunkX *16, chunkZ *16, random);
	    break;
	    }
	}
	
	 private void addOre(Block block, Block blockSpawn,Random random, World world, int posX, int posZ, int minY, int maxY, int minVein, int maxVein, int spawnChance)
	 {
		 for(int i = 0; i < spawnChance; i++){
			 int defaultChunkSize = 16;
			 int Xpos = posX + random.nextInt(defaultChunkSize);
			 int Ypos = minY + random.nextInt(maxY - minY);
			 int Zpos = posZ + random.nextInt(defaultChunkSize);
			 IBlockState state = block.getDefaultState();
			 BlockPos blockPos = new BlockPos(Xpos, Ypos, Zpos);
			 new WorldGenMinable(state, maxVein).generate(world, random, blockPos);
		 }
	 }
	 
	 private void GenerateOverWorld(World world, int i, int j, Random random) 
	 {
		 addOre(RotBlocks.copperOre, Blocks.stone, random, world, i, j, 0, 64, 4, 10, 20);
		 addOre(RotBlocks.silverOre, Blocks.stone, random, world, i, j, 0, 48, 1, 5, 10);
		 addOre(RotBlocks.leadOre, Blocks.stone, random, world, i, j, 0, 48, 3, 8, 12);
		 addOre(RotBlocks.platinumOre, Blocks.stone, random, world, i, j, 0, 16, 3, 6, 2);
		 addOre(RotBlocks.tinOre, Blocks.stone, random, world, i, j, 0, 64, 4, 10, 20);
		 addOre(RotBlocks.galenaOre, Blocks.stone, random, world, i, j, 0, 48, 1, 5, 7);
		 
		 addOre(RotBlocks.amethystSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 100);
		 addOre(RotBlocks.diamondSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 100);
		 addOre(RotBlocks.emeraldSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 100);
		 addOre(RotBlocks.rubySand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 100);
		 addOre(RotBlocks.topazSand, Blocks.sand, random, world, i, j, 0, 256, 2, 4, 100);
	 }
}