package com.dmillerw.brainFuckBlocks.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import com.dmillerw.brainFuckBlocks.block.BlockIDs;
import com.dmillerw.brainFuckBlocks.block.BlockPeripheral;
import com.dmillerw.brainFuckBlocks.interfaces.IInputPeripheral;

public class TileEntityRedstoneData extends TileEntity implements IInputPeripheral {

	public byte rsOutput = 0;

	@SuppressWarnings("static-access")
	@Override
	public void handleDataInput(byte data) {
		BlockPeripheral block = (BlockPeripheral) Block.blocksList[BlockIDs.bfPeripheralID];
		block.updateSurroundingBlocks(worldObj, xCoord, yCoord, zCoord);
		rsOutput = data;
	}

}
