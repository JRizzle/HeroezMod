package com.heroez.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
	MinecraftForgeClient
		.preloadTexture("/mods/heroez/textures/items/Items.png");
	// MinecraftForgeClient.preloadTexture(BLOCK_PNG);
    }

}