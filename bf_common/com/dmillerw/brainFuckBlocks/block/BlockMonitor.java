package com.dmillerw.brainFuckBlocks.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.dmillerw.brainFuckBlocks.BrainFuckBlocks;
import com.dmillerw.brainFuckBlocks.interfaces.IBFWrench;
import com.dmillerw.brainFuckBlocks.interfaces.IIconProvider;
import com.dmillerw.brainFuckBlocks.interfaces.IRotatable;
import com.dmillerw.brainFuckBlocks.lib.ModInfo;
import com.dmillerw.brainFuckBlocks.tileentity.TileEntityByteMonitor;
import com.dmillerw.brainFuckBlocks.util.PlayerUtil;
import com.dmillerw.brainFuckBlocks.util.TextureCoordinates;

public class BlockMonitor extends BlockContainer {

	private TextureCoordinates topTexture;
	private TextureCoordinates bottomTexture;
	private TextureCoordinates sideTexture;
	private TextureCoordinates frontTexture;
	
	public static String[] blockNames = new String[] {"Byte Monitor", "Character Monitor"};
	
	public BlockMonitor(int id) {
		super(id, Material.iron);
		setHardness(1F);
		setResistance(1F);
		setCreativeTab(BrainFuckBlocks.creativeTabBF);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IBFWrench) {
			IRotatable tile = (IRotatable) world.getBlockTileEntity(x, y, z);
			tile.rotate();
			world.markBlockForRenderUpdate(x, y, z);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, living, stack);
		ForgeDirection side = PlayerUtil.get2DBlockOrientation(living);
		TileEntityByteMonitor tile = (TileEntityByteMonitor) world.getBlockTileEntity(x, y, z);
		tile.setRotation(side);
		tile.type = (byte) world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
		IRotatable rotate = (IRotatable) world.getBlockTileEntity(x, y, z);
		
		if (forgeSide == ForgeDirection.UP) {
			return topTexture.getTextureIndex();
		} else if (forgeSide == ForgeDirection.DOWN) {
			return bottomTexture.getTextureIndex();
		} else if (forgeSide != rotate.getRotation().getOpposite()) {
			return sideTexture.getTextureIndex();
		} else {
			return frontTexture.getTextureIndex();
		}
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
		
		if (forgeSide == ForgeDirection.UP) {
			return topTexture.getTextureIndex();
		} else if (forgeSide == ForgeDirection.DOWN) {
			return bottomTexture.getTextureIndex();
		} else if (forgeSide != ForgeDirection.SOUTH) {
			return sideTexture.getTextureIndex();
		} else {
			return frontTexture.getTextureIndex();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		for (int i=0; i<blockNames.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileEntityByteMonitor();
	}
	
	/* IGNORE */
	public TileEntity createNewTileEntity(World world) {return null;}

}
