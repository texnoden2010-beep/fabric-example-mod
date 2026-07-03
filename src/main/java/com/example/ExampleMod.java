package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "modid";

    public static final Item DESH_ITEM = new Item(new Item.Settings().maxCount(1).component(DataComponentTypes.ITEM_NAME, Text.literal("Desh"))) {
        @Override
        public boolean hasGlint(ItemStack stack) { return true; }

        @Override
        public ActionResult use(World world, PlayerEntity user, Hand hand) {
            if (!world.isClient) {
                Vec3d lookDir = user.getRotationVec(1.0F);
                Vec3d horizontalDir = new Vec3d(lookDir.x, 0.0, lookDir.z);
                horizontalDir = horizontalDir.lengthSquared() < 1.0E-4 ? Vec3d.fromPolar(0.0F, user.getYaw()) : horizontalDir.normalize();
                
                user.setVelocity(horizontalDir.x * 3.5, user.getVelocity().y, horizontalDir.z * 3.5);
                user.velocityModified = true;
                user.getItemCooldownManager().set(this, 30);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WIND_CHARGE_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        }
    };

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "desh"), DESH_ITEM);
    }
}
