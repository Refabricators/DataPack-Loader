package me.hadroncollision.datapackloader.mixin;

import me.hadroncollision.datapackloader.DataPackLoader;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.resource.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;

@Mixin(value = CreateWorldScreen.class, priority = 1001)
public class CreateWorldScreenMixin {

    @Redirect(method = "getScannedPack", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/VanillaDataPackProvider;createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;"))
    private ResourcePackManager getScannedPacks(Path dataPacksPath) {
        return new ResourcePackManager(new VanillaDataPackProvider(), new FileResourcePackProvider(dataPacksPath, ResourceType.SERVER_DATA, ResourcePackSource.WORLD),
                new FileResourcePackProvider(
                        DataPackLoader.DATAPACKS_PATH,
                        ResourceType.SERVER_DATA,
                        DataPackLoader.RESOURCE_PACK_SOURCE
                ));
    }
}