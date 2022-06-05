package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.inventory.Fuel;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.Binary;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class Item implements Cloneable, BlockIds, ItemIds {

    private static CompoundTag parseCompoundTag(byte[] tag) {
        try {
            return NBTIO.read(tag, ByteOrder.LITTLE_ENDIAN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] writeCompoundTag(CompoundTag tag) {
        try {
            return NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class[] list = null;

    protected Block block = null;
    protected final int id;
    protected int meta;
    protected boolean hasMeta = true;
    private byte[] tags = new byte[0];
    private CompoundTag cachedNBT = null;
    public int count;
    protected int durability = 0;
    protected String name;

    public Item(int id) {
        this(id, 0, 1, "Unknown");
    }

    public Item(int id, Integer meta) {
        this(id, meta, 1, "Unknown");
    }

    public Item(int id, Integer meta, int count) {
        this(id, meta, count, "Unknown");
    }

    public Item(int id, Integer meta, int count, String name) {
        this.id = id & 0xffff;
        if (meta != null && meta >= 0) {
            this.meta = meta & 0xffff;
        } else {
            this.hasMeta = false;
        }
        this.count = count;
        this.name = name;
        /*f (this.block != null && this.id <= 0xff && Block.list[id] != null) { //probably useless
            this.block = Block.get(this.id, this.meta);
            this.name = this.block.getName();
        }*/
    }

    public boolean hasMeta() {
        return hasMeta;
    }

    public boolean canBeActivated() {
        return false;
    }

    public static void init() {
        if (list == null) {
            list = new Class[65535];
            list[IRON_SHOVEL] = ItemShovelIron.class; //256
            list[IRON_PICKAXE] = ItemPickaxeIron.class; //257
            list[IRON_AXE] = ItemAxeIron.class; //258
            list[FLINT_AND_STEEL] = ItemFlintSteel.class; //259
            list[APPLE] = ItemApple.class; //260
            list[BOW] = ItemBow.class; //261
            list[ARROW] = ItemArrow.class; //262
            list[COAL] = ItemCoal.class; //263
            list[DIAMOND] = ItemDiamond.class; //264
            list[IRON_INGOT] = ItemIngotIron.class; //265
            list[GOLD_INGOT] = ItemIngotGold.class; //266
            list[IRON_SWORD] = ItemSwordIron.class; //267
            list[WOODEN_SWORD] = ItemSwordWood.class; //268
            list[WOODEN_SHOVEL] = ItemShovelWood.class; //269
            list[WOODEN_PICKAXE] = ItemPickaxeWood.class; //270
            list[WOODEN_AXE] = ItemAxeWood.class; //271
            list[STONE_SWORD] = ItemSwordStone.class; //272
            list[STONE_SHOVEL] = ItemShovelStone.class; //273
            list[STONE_PICKAXE] = ItemPickaxeStone.class; //274
            list[STONE_AXE] = ItemAxeStone.class; //275
            list[DIAMOND_SWORD] = ItemSwordDiamond.class; //276
            list[DIAMOND_SHOVEL] = ItemShovelDiamond.class; //277
            list[DIAMOND_PICKAXE] = ItemPickaxeDiamond.class; //278
            list[DIAMOND_AXE] = ItemAxeDiamond.class; //279
            list[STICK] = ItemStick.class; //280
            list[BOWL] = ItemBowl.class; //281
            list[MUSHROOM_STEW] = ItemMushroomStew.class; //282
            list[GOLD_SWORD] = ItemSwordGold.class; //283
            list[GOLD_SHOVEL] = ItemShovelGold.class; //284
            list[GOLD_PICKAXE] = ItemPickaxeGold.class; //285
            list[GOLD_AXE] = ItemAxeGold.class; //286
            list[STRING] = ItemString.class; //287
            list[FEATHER] = ItemFeather.class; //288
            list[GUNPOWDER] = ItemGunpowder.class; //289
            list[WOODEN_HOE] = ItemHoeWood.class; //290
            list[STONE_HOE] = ItemHoeStone.class; //291
            list[IRON_HOE] = ItemHoeIron.class; //292
            list[DIAMOND_HOE] = ItemHoeDiamond.class; //293
            list[GOLD_HOE] = ItemHoeGold.class; //294
            list[WHEAT_SEEDS] = ItemSeedsWheat.class; //295
            list[WHEAT] = ItemWheat.class; //296
            list[BREAD] = ItemBread.class; //297
            list[LEATHER_CAP] = ItemHelmetLeather.class; //298
            list[LEATHER_TUNIC] = ItemChestplateLeather.class; //299
            list[LEATHER_PANTS] = ItemLeggingsLeather.class; //300
            list[LEATHER_BOOTS] = ItemBootsLeather.class; //301
            list[CHAIN_HELMET] = ItemHelmetChain.class; //302
            list[CHAIN_CHESTPLATE] = ItemChestplateChain.class; //303
            list[CHAIN_LEGGINGS] = ItemLeggingsChain.class; //304
            list[CHAIN_BOOTS] = ItemBootsChain.class; //305
            list[IRON_HELMET] = ItemHelmetIron.class; //306
            list[IRON_CHESTPLATE] = ItemChestplateIron.class; //307
            list[IRON_LEGGINGS] = ItemLeggingsIron.class; //308
            list[IRON_BOOTS] = ItemBootsIron.class; //309
            list[DIAMOND_HELMET] = ItemHelmetDiamond.class; //310
            list[DIAMOND_CHESTPLATE] = ItemChestplateDiamond.class; //311
            list[DIAMOND_LEGGINGS] = ItemLeggingsDiamond.class; //312
            list[DIAMOND_BOOTS] = ItemBootsDiamond.class; //313
            list[GOLD_HELMET] = ItemHelmetGold.class; //314
            list[GOLD_CHESTPLATE] = ItemChestplateGold.class; //315
            list[GOLD_LEGGINGS] = ItemLeggingsGold.class; //316
            list[GOLD_BOOTS] = ItemBootsGold.class; //317
            list[FLINT] = ItemFlint.class; //318
            list[RAW_PORKCHOP] = ItemPorkchopRaw.class; //319
            list[COOKED_PORKCHOP] = ItemPorkchopCooked.class; //320
            list[PAINTING] = ItemPainting.class; //321
            list[GOLDEN_APPLE] = ItemAppleGold.class; //322
            list[SIGN] = ItemSign.class; //323
            list[WOODEN_DOOR] = ItemDoorWood.class; //324
            list[BUCKET] = ItemBucket.class; //325

            list[MINECART] = ItemMinecart.class; //328
            list[SADDLE] = ItemSaddle.class; //329
            list[IRON_DOOR] = ItemDoorIron.class; //330
            list[REDSTONE] = ItemRedstone.class; //331
            list[SNOWBALL] = ItemSnowball.class; //332
            list[BOAT] = ItemBoat.class; //333
            list[LEATHER] = ItemLeather.class; //334

            list[BRICK] = ItemBrick.class; //336
            list[CLAY] = ItemClay.class; //337
            list[SUGARCANE] = ItemSugarcane.class; //338
            list[PAPER] = ItemPaper.class; //339
            list[BOOK] = ItemBook.class; //340
            list[SLIMEBALL] = ItemSlimeball.class; //341
            list[MINECART_WITH_CHEST] = ItemMinecartChest.class; //342

            list[EGG] = ItemEgg.class; //344
            list[COMPASS] = ItemCompass.class; //345
            list[FISHING_ROD] = ItemFishingRod.class; //346
            list[CLOCK] = ItemClock.class; //347
            list[GLOWSTONE_DUST] = ItemGlowstoneDust.class; //348
            list[RAW_FISH] = ItemFish.class; //349
            list[COOKED_FISH] = ItemFishCooked.class; //350
            list[DYE] = ItemDye.class; //351
            list[BONE] = ItemBone.class; //352
            list[SUGAR] = ItemSugar.class; //353
            list[CAKE] = ItemCake.class; //354
            list[BED] = ItemBed.class; //355
            list[REPEATER] = ItemRedstoneRepeater.class; //356
            list[COOKIE] = ItemCookie.class; //357
            list[MAP] = ItemMap.class; //358
            list[SHEARS] = ItemShears.class; //359
            list[MELON] = ItemMelon.class; //360
            list[PUMPKIN_SEEDS] = ItemSeedsPumpkin.class; //361
            list[MELON_SEEDS] = ItemSeedsMelon.class; //362
            list[RAW_BEEF] = ItemBeefRaw.class; //363
            list[STEAK] = ItemSteak.class; //364
            list[RAW_CHICKEN] = ItemChickenRaw.class; //365
            list[COOKED_CHICKEN] = ItemChickenCooked.class; //366
            list[ROTTEN_FLESH] = ItemRottenFlesh.class; //367
            list[ENDER_PEARL] = ItemEnderPearl.class; //368
            list[BLAZE_ROD] = ItemBlazeRod.class; //369
            list[GHAST_TEAR] = ItemGhastTear.class; //370
            list[GOLD_NUGGET] = ItemNuggetGold.class; //371
            list[NETHER_WART] = ItemNetherWart.class; //372
            list[POTION] = ItemPotion.class; //373
            list[GLASS_BOTTLE] = ItemGlassBottle.class; //374
            list[SPIDER_EYE] = ItemSpiderEye.class; //375
            list[FERMENTED_SPIDER_EYE] = ItemSpiderEyeFermented.class; //376
            list[BLAZE_POWDER] = ItemBlazePowder.class; //377
            list[MAGMA_CREAM] = ItemMagmaCream.class; //378
            list[BREWING_STAND] = ItemBrewingStand.class; //379
            list[CAULDRON] = ItemCauldron.class; //380
            list[ENDER_EYE] = ItemEnderEye.class; //381
            list[GLISTERING_MELON] = ItemMelonGlistering.class; //382
            list[SPAWN_EGG] = ItemSpawnEgg.class; //383
            list[EXPERIENCE_BOTTLE] = ItemExpBottle.class; //384
            list[FIRE_CHARGE] = ItemFireCharge.class; //385

            list[EMERALD] = ItemEmerald.class; //388
            list[ITEM_FRAME] = ItemItemFrame.class; //389
            list[FLOWER_POT] = ItemFlowerPot.class; //390
            list[CARROT] = ItemCarrot.class; //391
            list[BAKED_POTATO] = ItemPotatoBaked.class; //393
            list[POISONOUS_POTATO] = ItemPotatoPoisonous.class; //394
            //TODO: list[EMPTY_MAP] = ItemEmptyMap.class; //395
            //TODO: list[GOLDEN_CARROT] = ItemCarrotGolden.class; //396
            list[SKULL] = ItemSkull.class; //397
            list[CARROT_ON_A_STICK] = ItemCarrotOnAStick.class; //398
            list[NETHER_STAR] = ItemNetherStar.class; //399
            list[PUMPKIN_PIE] = ItemPumpkinPie.class; //400

            list[ENCHANTED_BOOK] = ItemBookEnchanted.class; //403
            list[COMPARATOR] = ItemRedstoneComparator.class; //404
            list[NETHER_BRICK] = ItemNetherBrick.class; //405
            list[QUARTZ] = ItemQuartz.class; //406
            list[MINECART_WITH_TNT] = ItemMinecartTNT.class; //407
            list[MINECART_WITH_HOPPER] = ItemMinecartHopper.class; //408
            list[PRISMARINE_SHARD] = ItemPrismarineShard.class; //409
            list[HOPPER] = ItemHopper.class;
            list[RAW_RABBIT] = ItemRabbitRaw.class; //411
            list[COOKED_RABBIT] = ItemRabbitCooked.class; //412
            list[RABBIT_STEW] = ItemRabbitStew.class; //413
            list[RABBIT_FOOT] = ItemRabbitFoot.class; //414
            //TODO: list[RABBIT_HIDE] = ItemRabbitHide.class; //415
            list[LEATHER_HORSE_ARMOR] = ItemHorseArmorLeather.class; //416
            list[IRON_HORSE_ARMOR] = ItemHorseArmorIron.class; //417
            list[GOLD_HORSE_ARMOR] = ItemHorseArmorGold.class; //418
            list[DIAMOND_HORSE_ARMOR] = ItemHorseArmorDiamond.class; //419
            //TODO: list[LEAD] = ItemLead.class; //420
            //TODO: list[NAME_TAG] = ItemNameTag.class; //421
            list[PRISMARINE_CRYSTALS] = ItemPrismarineCrystals.class; //422
            list[RAW_MUTTON] = ItemMuttonRaw.class; //423
            list[COOKED_MUTTON] = ItemMuttonCooked.class; //424

            list[END_CRYSTAL] = ItemEndCrystal.class; //426
            list[SPRUCE_DOOR] = ItemDoorSpruce.class; //427
            list[BIRCH_DOOR] = ItemDoorBirch.class; //428
            list[JUNGLE_DOOR] = ItemDoorJungle.class; //429
            list[ACACIA_DOOR] = ItemDoorAcacia.class; //430
            list[DARK_OAK_DOOR] = ItemDoorDarkOak.class; //431
            //TODO: list[CHORUS_FRUIT] = ItemChorusFruit.class; //432
            //TODO: list[POPPED_CHORUS_FRUIT] = ItemChorusFruitPopped.class; //433

            //TODO: list[DRAGON_BREATH] = ItemDragonBreath.class; //437
            list[SPLASH_POTION] = ItemPotionSplash.class; //438

            //TODO: list[LINGERING_POTION] = ItemPotionLingering.class; //441

            list[ELYTRA] = ItemElytra.class; //444

            //TODO: list[SHULKER_SHELL] = ItemShulkerShell.class; //445
            list[TOTEM] = ItemTotem.class; //457
            list[BEETROOT] = ItemBeetroot.class; //457
            list[BEETROOT_SEEDS] = ItemSeedsBeetroot.class; //458
            list[BEETROOT_SOUP] = ItemBeetrootSoup.class; //459
            list[RAW_SALMON] = ItemSalmon.class; //460
            list[CLOWNFISH] = ItemClownfish.class; //461
            list[PUFFERFISH] = ItemPufferfish.class; //462
            list[COOKED_SALMON] = ItemSalmonCooked.class; //463

            list[GOLDEN_APPLE_ENCHANTED] = ItemAppleGoldEnchanted.class; //466

            for (int i = 0; i < 256; ++i) {
                if (Block.list[i] != null) {
                    list[i] = Block.list[i];
                }
            }
        }

        initCreativeItems();
    }

    private static final ArrayList<Item> creative = new ArrayList<>();

    private static void initCreativeItems() {
        clearCreativeItems();

        //Building
        addCreativeItem(Item.get(Item.COBBLESTONE, 0));
        addCreativeItem(Item.get(Item.STONE_BRICKS, 0));
        addCreativeItem(Item.get(Item.STONE_BRICKS, 1));
        addCreativeItem(Item.get(Item.STONE_BRICKS, 2));
        addCreativeItem(Item.get(Item.STONE_BRICKS, 3));
        addCreativeItem(Item.get(Item.MOSS_STONE, 0));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 0));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 1));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 2));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 3));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 4));
        addCreativeItem(Item.get(Item.WOODEN_PLANKS, 5));
        addCreativeItem(Item.get(Item.BRICKS, 0));

        addCreativeItem(Item.get(Item.STONE, 0));
        addCreativeItem(Item.get(Item.STONE, 1));
        addCreativeItem(Item.get(Item.STONE, 2));
        addCreativeItem(Item.get(Item.STONE, 3));
        addCreativeItem(Item.get(Item.STONE, 4));
        addCreativeItem(Item.get(Item.STONE, 5));
        addCreativeItem(Item.get(Item.STONE, 6));
        addCreativeItem(Item.get(Item.DIRT, 0));
        addCreativeItem(Item.get(Item.PODZOL, 0));
        addCreativeItem(Item.get(Item.GRASS, 0));
        addCreativeItem(Item.get(Item.MYCELIUM, 0));
        addCreativeItem(Item.get(Item.CLAY_BLOCK, 0));
        addCreativeItem(Item.get(Item.TERRACOTTA, 0));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 0));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 1));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 2));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 3));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 4));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 5));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 6));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 7));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 8));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 9));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 10));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 11));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 12));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 13));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 14));
        addCreativeItem(Item.get(Item.STAINED_TERRACOTTA, 15));
        addCreativeItem(Item.get(Item.SANDSTONE, 0));
        addCreativeItem(Item.get(Item.SANDSTONE, 1));
        addCreativeItem(Item.get(Item.SANDSTONE, 2));
        addCreativeItem(Item.get(Item.RED_SANDSTONE, 0));
        addCreativeItem(Item.get(Item.RED_SANDSTONE, 1));
        addCreativeItem(Item.get(Item.RED_SANDSTONE, 2));
        addCreativeItem(Item.get(Item.SAND, 0));
        addCreativeItem(Item.get(Item.SAND, 1));
        addCreativeItem(Item.get(Item.GRAVEL, 0));
        addCreativeItem(Item.get(Item.TRUNK, 0));
        addCreativeItem(Item.get(Item.TRUNK, 1));
        addCreativeItem(Item.get(Item.TRUNK, 2));
        addCreativeItem(Item.get(Item.TRUNK, 3));
        addCreativeItem(Item.get(Item.TRUNK2, 0));
        addCreativeItem(Item.get(Item.TRUNK2, 1));
        addCreativeItem(Item.get(Item.NETHER_BRICKS, 0));
        addCreativeItem(Item.get(Item.NETHERRACK, 0));
        addCreativeItem(Item.get(Item.SOUL_SAND, 0));
        addCreativeItem(Item.get(Item.BEDROCK, 0));
        addCreativeItem(Item.get(Item.COBBLESTONE_STAIRS, 0));
        addCreativeItem(Item.get(Item.OAK_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.SPRUCE_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.BIRCH_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.JUNGLE_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.ACACIA_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.DARK_OAK_WOODEN_STAIRS, 0));
        addCreativeItem(Item.get(Item.BRICK_STAIRS, 0));
        addCreativeItem(Item.get(Item.SANDSTONE_STAIRS, 0));
        addCreativeItem(Item.get(Item.RED_SANDSTONE_STAIRS, 0));
        addCreativeItem(Item.get(Item.STONE_BRICK_STAIRS, 0));
        addCreativeItem(Item.get(Item.NETHER_BRICKS_STAIRS, 0));
        addCreativeItem(Item.get(Item.QUARTZ_STAIRS, 0));
        addCreativeItem(Item.get(Item.PURPUR_STAIRS, 0));
        addCreativeItem(Item.get(Item.SLAB, 0));
        addCreativeItem(Item.get(Item.SLAB, 3));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 0));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 1));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 2));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 3));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 4));
        addCreativeItem(Item.get(Item.WOODEN_SLAB, 5));
        addCreativeItem(Item.get(Item.SLAB, 4));
        addCreativeItem(Item.get(Item.SLAB, 1));
        addCreativeItem(Item.get(Item.RED_SANDSTONE_SLAB));
        addCreativeItem(Item.get(Item.SLAB, 5));
        addCreativeItem(Item.get(Item.SLAB, 7));
        addCreativeItem(Item.get(Item.SLAB, 6));
        addCreativeItem(Item.get(Item.RED_SANDSTONE_SLAB, 1));
        addCreativeItem(Item.get(Item.QUARTZ_BLOCK, 0));
        addCreativeItem(Item.get(Item.QUARTZ_BLOCK, 2));
        addCreativeItem(Item.get(Item.QUARTZ_BLOCK, 1));
        addCreativeItem(Item.get(Item.PRISMARINE, 0));
        addCreativeItem(Item.get(Item.PRISMARINE, 1));
        addCreativeItem(Item.get(Item.PRISMARINE, 2));
        addCreativeItem(Item.get(Item.PURPUR_BLOCK, 0));
        addCreativeItem(Item.get(Item.PURPUR_BLOCK, 2));
        addCreativeItem(Item.get(Item.COAL_ORE, 0));
        addCreativeItem(Item.get(Item.IRON_ORE, 0));
        addCreativeItem(Item.get(Item.GOLD_ORE, 0));
        addCreativeItem(Item.get(Item.DIAMOND_ORE, 0));
        addCreativeItem(Item.get(Item.LAPIS_ORE, 0));
        addCreativeItem(Item.get(Item.REDSTONE_ORE, 0));
        addCreativeItem(Item.get(Item.EMERALD_ORE, 0));
        addCreativeItem(Item.get(Item.QUARTZ_ORE, 0));
        addCreativeItem(Item.get(Item.OBSIDIAN, 0));
        addCreativeItem(Item.get(Item.ICE, 0));
        addCreativeItem(Item.get(Item.PACKED_ICE, 0));
        addCreativeItem(Item.get(Item.SNOW_BLOCK, 0));
        addCreativeItem(Item.get(Item.END_BRICKS, 0));
        addCreativeItem(Item.get(Item.END_STONE, 0));

        //Decoration
        addCreativeItem(Item.get(Item.BEACON, 0));
        addCreativeItem(Item.get(Item.COBBLESTONE_WALL, 0));
        addCreativeItem(Item.get(Item.COBBLESTONE_WALL, 1));
        addCreativeItem(Item.get(Item.WATER_LILY, 0));
        addCreativeItem(Item.get(Item.SEA_LANTERN, 0));
        addCreativeItem(Item.get(Item.CHORUS_PLANT, 0));
        addCreativeItem(Item.get(Item.CHORUS_FLOWER, 0));
        addCreativeItem(Item.get(Item.GOLD_BLOCK, 0));
        addCreativeItem(Item.get(Item.IRON_BLOCK, 0));
        addCreativeItem(Item.get(Item.DIAMOND_BLOCK, 0));
        addCreativeItem(Item.get(Item.LAPIS_BLOCK, 0));
        addCreativeItem(Item.get(Item.COAL_BLOCK, 0));
        addCreativeItem(Item.get(Item.EMERALD_BLOCK, 0));
        addCreativeItem(Item.get(Item.REDSTONE_BLOCK, 0));
        addCreativeItem(Item.get(Item.SNOW_LAYER, 0));
        addCreativeItem(Item.get(Item.GLASS, 0));
        addCreativeItem(Item.get(Item.GLOWSTONE_BLOCK, 0));
        addCreativeItem(Item.get(Item.VINES, 0));
        addCreativeItem(Item.get(Item.LADDER, 0));
        addCreativeItem(Item.get(Item.SPONGE, 0));
        addCreativeItem(Item.get(Item.SPONGE, 1));
        addCreativeItem(Item.get(Item.GLASS_PANE, 0));
        addCreativeItem(Item.get(Item.WOODEN_DOOR, 0));
        addCreativeItem(Item.get(Item.SPRUCE_DOOR, 0));
        addCreativeItem(Item.get(Item.BIRCH_DOOR, 0));
        addCreativeItem(Item.get(Item.JUNGLE_DOOR, 0));
        addCreativeItem(Item.get(Item.ACACIA_DOOR, 0));
        addCreativeItem(Item.get(Item.DARK_OAK_DOOR, 0));
        addCreativeItem(Item.get(Item.IRON_DOOR, 0));
        addCreativeItem(Item.get(Item.TRAPDOOR, 0));
        addCreativeItem(Item.get(Item.IRON_TRAPDOOR, 0));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_OAK));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_SPRUCE));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_BIRCH));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_JUNGLE));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_ACACIA));
        addCreativeItem(Item.get(Item.FENCE, BlockFence.FENCE_DARK_OAK));
        addCreativeItem(Item.get(Item.NETHER_BRICK_FENCE, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE_SPRUCE, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE_BIRCH, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE_JUNGLE, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE_ACACIA, 0));
        addCreativeItem(Item.get(Item.FENCE_GATE_DARK_OAK, 0));
        addCreativeItem(Item.get(Item.IRON_BARS, 0));
        int[] dyeColors = {0, 8, 7, 15, 12, 14, 1, 4, 5, 13, 9, 3, 11, 10, 2, 6};
        for (int color : dyeColors) {
            addCreativeItem(Item.get(Item.BED, color));
        }
        addCreativeItem(Item.get(Item.BOOKSHELF, 0));
        addCreativeItem(Item.get(Item.SIGN, 0));
        addCreativeItem(Item.get(Item.PAINTING, 0));
        addCreativeItem(Item.get(Item.ITEM_FRAME, 0));
        addCreativeItem(Item.get(Item.WORKBENCH, 0));
        addCreativeItem(Item.get(Item.STONECUTTER, 0));
        addCreativeItem(Item.get(Item.CHEST, 0));
        addCreativeItem(Item.get(Item.TRAPPED_CHEST, 0));
        addCreativeItem(Item.get(Item.FURNACE, 0));
        addCreativeItem(Item.get(Item.BREWING_STAND, 0));
        addCreativeItem(Item.get(Item.CAULDRON, 0));
        addCreativeItem(Item.get(Item.NOTEBLOCK, 0));
        addCreativeItem(Item.get(Item.END_ROD, 0));
        addCreativeItem(Item.get(Item.END_PORTAL_FRAME, 0));
        addCreativeItem(Item.get(Item.ANVIL, 0));
        addCreativeItem(Item.get(Item.ANVIL, 4));
        addCreativeItem(Item.get(Item.ANVIL, 8));
        addCreativeItem(Item.get(Item.DANDELION, 0));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_POPPY));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_BLUE_ORCHID));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_ALLIUM));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_AZURE_BLUET));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_RED_TULIP));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_ORANGE_TULIP));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_WHITE_TULIP));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_PINK_TULIP));
        addCreativeItem(Item.get(Item.RED_FLOWER, BlockFlower.TYPE_OXEYE_DAISY));
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 0)); // SUNFLOWER
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 1)); // Lilac
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 2)); // Double Tall Grass
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 3)); // Large fern
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 4)); // Rose bush
        addCreativeItem(Item.get(Item.DOUBLE_PLANT, 5)); // Peony
        addCreativeItem(Item.get(Item.BROWN_MUSHROOM, 0));
        addCreativeItem(Item.get(Item.RED_MUSHROOM, 0));
        addCreativeItem(Item.get(Item.BROWN_MUSHROOM_BLOCK, 14));
        addCreativeItem(Item.get(Item.RED_MUSHROOM_BLOCK, 14));
        addCreativeItem(Item.get(Item.BROWN_MUSHROOM_BLOCK, 0));
        addCreativeItem(Item.get(Item.RED_MUSHROOM_BLOCK, 15));
        addCreativeItem(Item.get(Item.CACTUS, 0));
        addCreativeItem(Item.get(Item.MELON_BLOCK, 0));
        addCreativeItem(Item.get(Item.PUMPKIN, 0));
        addCreativeItem(Item.get(Item.LIT_PUMPKIN, 0));
        addCreativeItem(Item.get(Item.COBWEB, 0));
        addCreativeItem(Item.get(Item.HAY_BALE, 0));
        addCreativeItem(Item.get(Item.TALL_GRASS, 1));
        addCreativeItem(Item.get(Item.TALL_GRASS, 2));
        addCreativeItem(Item.get(Item.DEAD_BUSH, 0));
        addCreativeItem(Item.get(Item.SAPLING, 0));
        addCreativeItem(Item.get(Item.SAPLING, 1));
        addCreativeItem(Item.get(Item.SAPLING, 2));
        addCreativeItem(Item.get(Item.SAPLING, 3));
        addCreativeItem(Item.get(Item.SAPLING, 4));
        addCreativeItem(Item.get(Item.SAPLING, 5));
        addCreativeItem(Item.get(Item.LEAVES, 0));
        addCreativeItem(Item.get(Item.LEAVES, 1));
        addCreativeItem(Item.get(Item.LEAVES, 2));
        addCreativeItem(Item.get(Item.LEAVES, 3));
        addCreativeItem(Item.get(Item.LEAVES2, 0));
        addCreativeItem(Item.get(Item.LEAVES2, 1));

        addCreativeItem(Item.get(Item.WHITE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.SILVER_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.GRAY_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.BLACK_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.BROWN_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.RED_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.ORANGE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.LIME_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.GREEN_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.CYAN_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.LIGHT_BLUE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.BLUE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.PURPLE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.MAGENTA_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.WHITE_GLAZED_TERRACOTTA));
        addCreativeItem(Item.get(Item.PINK_GLAZED_TERRACOTTA));

        addCreativeItem(Item.get(Item.CAKE, 0));

        addCreativeItem(Item.get(Item.SKULL, ItemSkull.SKELETON_SKULL));
        addCreativeItem(Item.get(Item.SKULL, ItemSkull.WITHER_SKELETON_SKULL));
        addCreativeItem(Item.get(Item.SKULL, ItemSkull.ZOMBIE_HEAD));
        addCreativeItem(Item.get(Item.SKULL, ItemSkull.HEAD));
        addCreativeItem(Item.get(Item.SKULL, ItemSkull.CREEPER_HEAD));
        addCreativeItem(Item.get(Item.SKULL, ItemSkull.DRAGON_HEAD));

        addCreativeItem(Item.get(Item.FLOWER_POT, 0));

        addCreativeItem(Item.get(Item.MONSTER_EGG, 0));
        addCreativeItem(Item.get(Item.MONSTER_EGG, 1));
        addCreativeItem(Item.get(Item.MONSTER_EGG, 2));
        addCreativeItem(Item.get(Item.MONSTER_EGG, 3));
        addCreativeItem(Item.get(Item.MONSTER_EGG, 4));
        addCreativeItem(Item.get(Item.MONSTER_EGG, 5));

        addCreativeItem(Item.get(Item.DRAGON_EGG, 0));
        addCreativeItem(Item.get(Item.END_CRYSTAL, 0));
        addCreativeItem(Item.get(Item.MONSTER_SPAWNER, 0));
        addCreativeItem(Item.get(Item.ENCHANTMENT_TABLE, 0));
        addCreativeItem(Item.get(Item.SLIME_BLOCK, 0));
        addCreativeItem(Item.get(Item.ENDER_CHEST, 0));

        for (int color : dyeColors) {
            addCreativeItem(Item.get(Item.WOOL, color));
        }

        addCreativeItem(Item.get(Item.CARPET, 0));
        addCreativeItem(Item.get(Item.CARPET, 8));
        addCreativeItem(Item.get(Item.CARPET, 7));
        addCreativeItem(Item.get(Item.CARPET, 15));
        addCreativeItem(Item.get(Item.CARPET, 12));
        addCreativeItem(Item.get(Item.CARPET, 14));
        addCreativeItem(Item.get(Item.CARPET, 1));
        addCreativeItem(Item.get(Item.CARPET, 4));
        addCreativeItem(Item.get(Item.CARPET, 5));
        addCreativeItem(Item.get(Item.CARPET, 13));
        addCreativeItem(Item.get(Item.CARPET, 9));
        addCreativeItem(Item.get(Item.CARPET, 3));
        addCreativeItem(Item.get(Item.CARPET, 11));
        addCreativeItem(Item.get(Item.CARPET, 10));
        addCreativeItem(Item.get(Item.CARPET, 2));
        addCreativeItem(Item.get(Item.CARPET, 6));


        //Tools
        addCreativeItem(Item.get(Item.RAIL, 0));
        addCreativeItem(Item.get(Item.POWERED_RAIL, 0));
        addCreativeItem(Item.get(Item.DETECTOR_RAIL, 0));
        addCreativeItem(Item.get(Item.ACTIVATOR_RAIL, 0));
        addCreativeItem(Item.get(Item.TORCH, 0));
        addCreativeItem(Item.get(Item.BUCKET, 0));
        addCreativeItem(Item.get(Item.BUCKET, 1)); // milk
        addCreativeItem(Item.get(Item.BUCKET, 8)); // water
        addCreativeItem(Item.get(Item.BUCKET, 10)); // lava
        addCreativeItem(Item.get(Item.TNT, 0));
        addCreativeItem(Item.get(Item.LEAD, 0));
        addCreativeItem(Item.get(Item.NAME_TAG, 0));
        addCreativeItem(Item.get(Item.REDSTONE, 0));
        addCreativeItem(Item.get(Item.BOW, 0));
        addCreativeItem(Item.get(Item.FISHING_ROD, 0));
        addCreativeItem(Item.get(Item.FLINT_AND_STEEL, 0));
        addCreativeItem(Item.get(Item.SHEARS, 0));
        addCreativeItem(Item.get(Item.CLOCK, 0));
        addCreativeItem(Item.get(Item.COMPASS, 0));
        addCreativeItem(Item.get(Item.MINECART, 0));
        addCreativeItem(Item.get(Item.MINECART_WITH_CHEST, 0));
        addCreativeItem(Item.get(Item.MINECART_WITH_HOPPER, 0));
        addCreativeItem(Item.get(Item.MINECART_WITH_TNT, 0));
        addCreativeItem(Item.get(Item.BOAT, 0)); // Oak
        addCreativeItem(Item.get(Item.BOAT, 1)); // Spruce
        addCreativeItem(Item.get(Item.BOAT, 2)); // Birch
        addCreativeItem(Item.get(Item.BOAT, 3)); // Jungle
        addCreativeItem(Item.get(Item.BOAT, 4)); // Acacia
        addCreativeItem(Item.get(Item.BOAT, 5)); // Dark Oak
        addCreativeItem(Item.get(Item.SADDLE, 0));
        addCreativeItem(Item.get(Item.LEATHER_HORSE_ARMOR, 0));
        addCreativeItem(Item.get(Item.IRON_HORSE_ARMOR, 0));
        addCreativeItem(Item.get(Item.GOLD_HORSE_ARMOR, 0));
        addCreativeItem(Item.get(Item.DIAMOND_HORSE_ARMOR, 0));
        
        addCreativeItem(Item.get(Item.SPAWN_EGG, 10)); //Chicken
        addCreativeItem(Item.get(Item.SPAWN_EGG, 11)); //Cow
        addCreativeItem(Item.get(Item.SPAWN_EGG, 12)); //Pig
        addCreativeItem(Item.get(Item.SPAWN_EGG, 13)); //Sheep
        addCreativeItem(Item.get(Item.SPAWN_EGG, 15)); //Villager
        addCreativeItem(Item.get(Item.SPAWN_EGG, 16)); //Mooshroom
        addCreativeItem(Item.get(Item.SPAWN_EGG, 17)); //Squid
        addCreativeItem(Item.get(Item.SPAWN_EGG, 19)); //Bat 
		//addCreativeItem(Item.get(Item.SPAWN_EGG, 20)); //Iron Golem
        //addCreativeItem(Item.get(Item.SPAWN_EGG, 21)); //Snow Golem
        addCreativeItem(Item.get(Item.SPAWN_EGG, 22)); //Ocelot
        addCreativeItem(Item.get(Item.SPAWN_EGG, 23)); //Horse
        addCreativeItem(Item.get(Item.SPAWN_EGG, 24)); //Donkey
        addCreativeItem(Item.get(Item.SPAWN_EGG, 25)); //Mule
        addCreativeItem(Item.get(Item.SPAWN_EGG, 26)); //SkeletonHorse
        addCreativeItem(Item.get(Item.SPAWN_EGG, 27)); //ZombieHorse
        addCreativeItem(Item.get(Item.SPAWN_EGG, 28)); //PolarBear
        addCreativeItem(Item.get(Item.SPAWN_EGG, 29)); //Llama
        addCreativeItem(Item.get(Item.SPAWN_EGG, 32)); //Zombie
        addCreativeItem(Item.get(Item.SPAWN_EGG, 33)); //Creeper
        addCreativeItem(Item.get(Item.SPAWN_EGG, 34)); //Skeleton
        addCreativeItem(Item.get(Item.SPAWN_EGG, 35)); //Spider
        addCreativeItem(Item.get(Item.SPAWN_EGG, 36)); //Zombie Pigman
        addCreativeItem(Item.get(Item.SPAWN_EGG, 37)); //Slime
        addCreativeItem(Item.get(Item.SPAWN_EGG, 38)); //Enderman
        addCreativeItem(Item.get(Item.SPAWN_EGG, 39)); //Silverfish
        addCreativeItem(Item.get(Item.SPAWN_EGG, 40)); //Cave spider
        addCreativeItem(Item.get(Item.SPAWN_EGG, 41)); //Ghast
        addCreativeItem(Item.get(Item.SPAWN_EGG, 42)); //MagmaCube
        addCreativeItem(Item.get(Item.SPAWN_EGG, 43)); //Blaze
        addCreativeItem(Item.get(Item.SPAWN_EGG, 45)); //Witch
        addCreativeItem(Item.get(Item.SPAWN_EGG, 46)); //Stray
        addCreativeItem(Item.get(Item.SPAWN_EGG, 47)); //Husk
        addCreativeItem(Item.get(Item.SPAWN_EGG, 49)); //Guardian
        addCreativeItem(Item.get(Item.SPAWN_EGG, 50)); //ElderGuardian
        addCreativeItem(Item.get(Item.SPAWN_EGG, 54)); //Shulker
        
        addCreativeItem(Item.get(Item.FIRE_CHARGE, 0));
        addCreativeItem(Item.get(Item.WOODEN_SWORD));
        addCreativeItem(Item.get(Item.WOODEN_HOE));
        addCreativeItem(Item.get(Item.WOODEN_SHOVEL));
        addCreativeItem(Item.get(Item.WOODEN_PICKAXE));
        addCreativeItem(Item.get(Item.WOODEN_AXE));
        addCreativeItem(Item.get(Item.STONE_SWORD));
        addCreativeItem(Item.get(Item.STONE_HOE));
        addCreativeItem(Item.get(Item.STONE_SHOVEL));
        addCreativeItem(Item.get(Item.STONE_PICKAXE));
        addCreativeItem(Item.get(Item.STONE_AXE));
        addCreativeItem(Item.get(Item.IRON_SWORD));
        addCreativeItem(Item.get(Item.IRON_HOE));
        addCreativeItem(Item.get(Item.IRON_SHOVEL));
        addCreativeItem(Item.get(Item.IRON_PICKAXE));
        addCreativeItem(Item.get(Item.IRON_AXE));
        addCreativeItem(Item.get(Item.DIAMOND_SWORD));
        addCreativeItem(Item.get(Item.DIAMOND_HOE));
        addCreativeItem(Item.get(Item.DIAMOND_SHOVEL));
        addCreativeItem(Item.get(Item.DIAMOND_PICKAXE));
        addCreativeItem(Item.get(Item.DIAMOND_AXE));
        addCreativeItem(Item.get(Item.GOLD_SWORD));
        addCreativeItem(Item.get(Item.GOLD_HOE));
        addCreativeItem(Item.get(Item.GOLD_SHOVEL));
        addCreativeItem(Item.get(Item.GOLD_PICKAXE));
        addCreativeItem(Item.get(Item.GOLD_AXE));
        addCreativeItem(Item.get(Item.LEATHER_CAP));
        addCreativeItem(Item.get(Item.LEATHER_TUNIC));
        addCreativeItem(Item.get(Item.LEATHER_PANTS));
        addCreativeItem(Item.get(Item.LEATHER_BOOTS));
        addCreativeItem(Item.get(Item.CHAIN_HELMET));
        addCreativeItem(Item.get(Item.CHAIN_CHESTPLATE));
        addCreativeItem(Item.get(Item.CHAIN_LEGGINGS));
        addCreativeItem(Item.get(Item.CHAIN_BOOTS));
        addCreativeItem(Item.get(Item.IRON_HELMET));
        addCreativeItem(Item.get(Item.IRON_CHESTPLATE));
        addCreativeItem(Item.get(Item.IRON_LEGGINGS));
        addCreativeItem(Item.get(Item.IRON_BOOTS));
        addCreativeItem(Item.get(Item.DIAMOND_HELMET));
        addCreativeItem(Item.get(Item.DIAMOND_CHESTPLATE));
        addCreativeItem(Item.get(Item.DIAMOND_LEGGINGS));
        addCreativeItem(Item.get(Item.DIAMOND_BOOTS));
        addCreativeItem(Item.get(Item.GOLD_HELMET));
        addCreativeItem(Item.get(Item.GOLD_CHESTPLATE));
        addCreativeItem(Item.get(Item.GOLD_LEGGINGS));
        addCreativeItem(Item.get(Item.GOLD_BOOTS));
        addCreativeItem(Item.get(Item.ELYTRA));
        addCreativeItem(Item.get(Item.LEVER));
        addCreativeItem(Item.get(Item.REDSTONE_LAMP));
        addCreativeItem(Item.get(Item.REDSTONE_TORCH));
        addCreativeItem(Item.get(Item.WOODEN_PRESSURE_PLATE));
        addCreativeItem(Item.get(Item.STONE_PRESSURE_PLATE));
        addCreativeItem(Item.get(Item.LIGHT_WEIGHTED_PRESSURE_PLATE));
        addCreativeItem(Item.get(Item.HEAVY_WEIGHTED_PRESSURE_PLATE));
        addCreativeItem(Item.get(Item.WOODEN_BUTTON, 5));
        addCreativeItem(Item.get(Item.STONE_BUTTON, 5));
        addCreativeItem(Item.get(Item.DAYLIGHT_DETECTOR));
        addCreativeItem(Item.get(Item.TRIPWIRE_HOOK));
        addCreativeItem(Item.get(Item.REPEATER));
        addCreativeItem(Item.get(Item.COMPARATOR));
        addCreativeItem(Item.get(Item.DISPENSER, 3));
        addCreativeItem(Item.get(Item.DROPPER));
        addCreativeItem(Item.get(Item.PISTON));
        addCreativeItem(Item.get(Item.STICKY_PISTON));
        addCreativeItem(Item.get(Item.OBSERVER));
        addCreativeItem(Item.get(Item.HOPPER));
        addCreativeItem(Item.get(Item.SNOWBALL));
        addCreativeItem(Item.get(Item.ENDER_PEARL));
        addCreativeItem(Item.get(Item.ENDER_EYE));

        //Seeds
        addCreativeItem(Item.get(Item.COAL, 0));
        addCreativeItem(Item.get(Item.COAL, 1));
        addCreativeItem(Item.get(Item.DIAMOND, 0));
        addCreativeItem(Item.get(Item.IRON_INGOT, 0));
        addCreativeItem(Item.get(Item.GOLD_INGOT, 0));
        addCreativeItem(Item.get(Item.EMERALD, 0));
        addCreativeItem(Item.get(Item.STICK, 0));
        addCreativeItem(Item.get(Item.BOWL, 0));
        addCreativeItem(Item.get(Item.STRING, 0));
        addCreativeItem(Item.get(Item.FEATHER, 0));
        addCreativeItem(Item.get(Item.FLINT, 0));
        addCreativeItem(Item.get(Item.LEATHER, 0));
        addCreativeItem(Item.get(Item.RABBIT_HIDE, 0));
        addCreativeItem(Item.get(Item.CLAY, 0));
        addCreativeItem(Item.get(Item.SUGAR, 0));
        addCreativeItem(Item.get(Item.BRICK, 0));
        addCreativeItem(Item.get(Item.NETHER_BRICK, 0));
        addCreativeItem(Item.get(Item.NETHER_QUARTZ, 0));
        addCreativeItem(Item.get(Item.PAPER, 0));
        addCreativeItem(Item.get(Item.BOOK, 0));
        addCreativeItem(Item.get(Item.ARROW, 0));
        addCreativeItem(Item.get(Item.BONE, 0));
        addCreativeItem(Item.get(Item.EMPTY_MAP, 0));
        addCreativeItem(Item.get(Item.SUGARCANE, 0));
        addCreativeItem(Item.get(Item.WHEAT, 0));
        addCreativeItem(Item.get(Item.SEEDS, 0));
        addCreativeItem(Item.get(Item.PUMPKIN_SEEDS, 0));
        addCreativeItem(Item.get(Item.MELON_SEEDS, 0));
        addCreativeItem(Item.get(Item.BEETROOT_SEEDS, 0));
        addCreativeItem(Item.get(Item.EGG, 0));
        addCreativeItem(Item.get(Item.APPLE, 0));
        addCreativeItem(Item.get(Item.GOLDEN_APPLE, 0));
        addCreativeItem(Item.get(Item.GOLDEN_APPLE_ENCHANTED, 0));
        addCreativeItem(Item.get(Item.RAW_FISH, 0));
        addCreativeItem(Item.get(Item.RAW_SALMON, 0));
        addCreativeItem(Item.get(Item.CLOWNFISH, 0));
        addCreativeItem(Item.get(Item.PUFFERFISH, 0));
        addCreativeItem(Item.get(Item.COOKED_FISH, 0));
        addCreativeItem(Item.get(Item.COOKED_SALMON, 0));
        addCreativeItem(Item.get(Item.ROTTEN_FLESH, 0));
        addCreativeItem(Item.get(Item.MUSHROOM_STEW, 0));
        addCreativeItem(Item.get(Item.BREAD, 0));
        addCreativeItem(Item.get(Item.RAW_PORKCHOP, 0));
        addCreativeItem(Item.get(Item.COOKED_PORKCHOP, 0));
        addCreativeItem(Item.get(Item.RAW_CHICKEN, 0));
        addCreativeItem(Item.get(Item.COOKED_CHICKEN, 0));
        addCreativeItem(Item.get(Item.RAW_MUTTON, 0));
        addCreativeItem(Item.get(Item.COOKED_MUTTON, 0));
        addCreativeItem(Item.get(Item.RAW_BEEF, 0));
        addCreativeItem(Item.get(Item.STEAK, 0));
        addCreativeItem(Item.get(Item.MELON, 0));
        addCreativeItem(Item.get(Item.CARROT, 0));
        addCreativeItem(Item.get(Item.POTATO, 0));
        addCreativeItem(Item.get(Item.BAKED_POTATO, 0));
        addCreativeItem(Item.get(Item.POISONOUS_POTATO, 0));
        addCreativeItem(Item.get(Item.BEETROOT, 0));
        addCreativeItem(Item.get(Item.COOKIE, 0));
        addCreativeItem(Item.get(Item.PUMPKIN_PIE, 0));
        addCreativeItem(Item.get(Item.RAW_RABBIT, 0));
        addCreativeItem(Item.get(Item.COOKED_RABBIT, 0));
        addCreativeItem(Item.get(Item.RABBIT_STEW, 0));
        addCreativeItem(Item.get(Item.CHORUS_FRUIT, 0));
        addCreativeItem(Item.get(Item.POPPED_CHORUS_FRUIT, 0));
        addCreativeItem(Item.get(Item.NETHER_STAR, 0));
        addCreativeItem(Item.get(Item.MAGMA_CREAM, 0));
        addCreativeItem(Item.get(Item.BLAZE_ROD, 0));
        addCreativeItem(Item.get(Item.GOLD_NUGGET, 0));
        addCreativeItem(Item.get(Item.GOLDEN_CARROT, 0));
        addCreativeItem(Item.get(Item.GLISTERING_MELON, 0));
        addCreativeItem(Item.get(Item.RABBIT_FOOT, 0));
        addCreativeItem(Item.get(Item.GHAST_TEAR, 0));
        addCreativeItem(Item.get(Item.SLIMEBALL, 0));
        addCreativeItem(Item.get(Item.BLAZE_POWDER, 0));
        addCreativeItem(Item.get(Item.NETHER_WART, 0));
        addCreativeItem(Item.get(Item.GUNPOWDER, 0));
        addCreativeItem(Item.get(Item.GLOWSTONE_DUST, 0));
        addCreativeItem(Item.get(Item.SPIDER_EYE, 0));
        addCreativeItem(Item.get(Item.FERMENTED_SPIDER_EYE, 0));
        addCreativeItem(Item.get(Item.DRAGON_BREATH));
        addCreativeItem(Item.get(Item.CARROT_ON_A_STICK));
        addCreativeItem(Item.get(Item.EXPERIENCE_BOTTLE));
        addCreativeItem(Item.get(Item.SHULKER_SHELL));
        addCreativeItem(Item.get(Item.PRISMARINE_SHARD, 0));
        addCreativeItem(Item.get(Item.PRISMARINE_CRYSTALS, 0));
        for (int color : dyeColors) {
            addCreativeItem(Item.get(Item.DYE, color));
        }

        //Potion
        addCreativeItem(Item.get(Item.GLASS_BOTTLE, 0));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.NO_EFFECTS));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.MUNDANE));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.MUNDANE_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.THICK));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.AWKWARD));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.NIGHT_VISION));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.NIGHT_VISION_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.INVISIBLE));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.INVISIBLE_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.LEAPING));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.LEAPING_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.LEAPING_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.FIRE_RESISTANCE));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.FIRE_RESISTANCE_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.SPEED));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.SPEED_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.SPEED_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.SLOWNESS));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.SLOWNESS_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.WATER_BREATHING));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.WATER_BREATHING_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.INSTANT_HEALTH));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.INSTANT_HEALTH_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.HARMING));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.HARMING_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.POISON));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.POISON_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.POISON_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.REGENERATION));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.REGENERATION_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.REGENERATION_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.STRENGTH));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.STRENGTH_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.STRENGTH_II));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.WEAKNESS));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.WEAKNESS_LONG));
        addCreativeItem(Item.get(Item.POTION, ItemPotion.DECAY));

        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.NO_EFFECTS));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.MUNDANE));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.MUNDANE_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.THICK));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.AWKWARD));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.NIGHT_VISION));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.NIGHT_VISION_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.INVISIBLE));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.INVISIBLE_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.LEAPING));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.LEAPING_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.LEAPING_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.FIRE_RESISTANCE));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.FIRE_RESISTANCE_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.SPEED));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.SPEED_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.SPEED_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.SLOWNESS));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.SLOWNESS_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.WATER_BREATHING));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.WATER_BREATHING_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.INSTANT_HEALTH));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.INSTANT_HEALTH_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.HARMING));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.HARMING_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.POISON));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.POISON_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.POISON_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.REGENERATION));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.REGENERATION_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.REGENERATION_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.STRENGTH));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.STRENGTH_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.STRENGTH_II));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.WEAKNESS));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.WEAKNESS_LONG));
        addCreativeItem(Item.get(Item.SPLASH_POTION, ItemPotion.DECAY));

        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.NO_EFFECTS));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.MUNDANE));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.MUNDANE_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.THICK));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.AWKWARD));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.NIGHT_VISION));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.NIGHT_VISION_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.INVISIBLE));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.INVISIBLE_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.LEAPING));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.LEAPING_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.LEAPING_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.FIRE_RESISTANCE));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.FIRE_RESISTANCE_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.SPEED));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.SPEED_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.SPEED_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.SLOWNESS));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.SLOWNESS_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.WATER_BREATHING));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.WATER_BREATHING_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.INSTANT_HEALTH));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.INSTANT_HEALTH_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.HARMING));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.HARMING_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.POISON));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.POISON_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.POISON_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.REGENERATION));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.REGENERATION_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.REGENERATION_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.STRENGTH));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.STRENGTH_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.STRENGTH_II));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.WEAKNESS));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.WEAKNESS_LONG));
        addCreativeItem(Item.get(Item.LINGERING_POTION, ItemPotion.DECAY));
    }

    public static void clearCreativeItems() {
        Item.creative.clear();
    }

    public static ArrayList<Item> getCreativeItems() {
        return new ArrayList<>(Item.creative);
    }

    public static void addCreativeItem(Item item) {
        Item.creative.add(item.clone());
    }

    public static void removeCreativeItem(Item item) {
        int index = getCreativeItemIndex(item);
        if (index != -1) {
            Item.creative.remove(index);
        }
    }

    public static boolean isCreativeItem(Item item) {
        for (Item aCreative : Item.creative) {
            if (item.deepEquals(aCreative, !item.isTool())) {
                return true;
            }
        }
        return false;
    }

    public static Item getCreativeItem(int index) {
        return (index >= 0 && index < Item.creative.size()) ? Item.creative.get(index) : null;
    }

    public static int getCreativeItemIndex(Item item) {
        for (int i = 0; i < Item.creative.size(); i++) {
            if (item.deepEquals(Item.creative.get(i), !item.isTool())) {
                return i;
            }
        }
        return -1;
    }

    public static Item get(int id) {
        return get(id, 0);
    }

    public static Item get(int id, Integer meta) {
        return get(id, meta, 1);
    }

    public static Item get(int id, Integer meta, int count) {
        return get(id, meta, count, new byte[0]);
    }

    public static Item get(int id, Integer meta, int count, byte[] tags) {
        try {
            Class c = list[id];
            Item item;

            if (c == null) {
                item = new Item(id, meta, count);
            } else if (id < 256) {
                item = new ItemBlock((Block) c.getConstructor(int.class).newInstance(meta), meta, count);
            } else {
                item = ((Item) c.getConstructor(Integer.class, int.class).newInstance(meta, count));
            }

            if (tags.length != 0) {
                item.setCompoundTag(tags);
            }

            return item;
        } catch (Exception e) {
            return new Item(id, meta, count).setCompoundTag(tags);
        }
    }

    public static Item fromString(String str) {
        String[] b = str.trim().replace(' ', '_').replace("minecraft:", "").split(":");

        int id = 0;
        int meta = 0;

        Pattern integerPattern = Pattern.compile("^[1-9]\\d*$");
        if (integerPattern.matcher(b[0]).matches()) {
            id = Integer.valueOf(b[0]);
        } else {
            try {
                id = Item.class.getField(b[0].toUpperCase()).getInt(null);
            } catch (Exception ignore) {
            }
        }

        id = id & 0xFFFF;
        if (b.length != 1) meta = Integer.valueOf(b[1]) & 0xFFFF;

        return get(id, meta);
    }

    public static Item[] fromStringMultiple(String str) {
        String[] b = str.split(",");
        Item[] items = new Item[b.length - 1];
        for (int i = 0; i < b.length; i++) {
            items[i] = fromString(b[i]);
        }
        return items;
    }

    public Item setCompoundTag(CompoundTag tag) {
        this.setNamedTag(tag);
        return this;
    }

    public Item setCompoundTag(byte[] tags) {
        this.tags = tags;
        this.cachedNBT = null;
        return this;
    }

    public byte[] getCompoundTag() {
        return tags;
    }

    public boolean hasCompoundTag() {
        return this.tags != null && this.tags.length > 0;
    }

    public boolean hasCustomBlockData() {
        if (!this.hasCompoundTag()) {
            return false;
        }

        CompoundTag tag = this.getNamedTag();
        return tag.contains("BlockEntityTag") && tag.get("BlockEntityTag") instanceof CompoundTag;

    }

    public Item clearCustomBlockData() {
        if (!this.hasCompoundTag()) {
            return this;
        }
        CompoundTag tag = this.getNamedTag();

        if (tag.contains("BlockEntityTag") && tag.get("BlockEntityTag") instanceof CompoundTag) {
            tag.remove("BlockEntityTag");
            this.setNamedTag(tag);
        }

        return this;
    }

    public Item setCustomBlockData(CompoundTag compoundTag) {
        CompoundTag tags = compoundTag.copy();
        tags.setName("BlockEntityTag");

        CompoundTag tag;
        if (!this.hasCompoundTag()) {
            tag = new CompoundTag();
        } else {
            tag = this.getNamedTag();
        }

        tag.putCompound("BlockEntityTag", tags);
        this.setNamedTag(tag);

        return this;
    }

    public CompoundTag getCustomBlockData() {
        if (!this.hasCompoundTag()) {
            return null;
        }

        CompoundTag tag = this.getNamedTag();

        if (tag.contains("BlockEntityTag")) {
            Tag bet = tag.get("BlockEntityTag");
            if (bet instanceof CompoundTag) {
                return (CompoundTag) bet;
            }
        }

        return null;
    }

    public boolean hasEnchantments() {
        if (!this.hasCompoundTag()) {
            return false;
        }

        CompoundTag tag = this.getNamedTag();

        if (tag.contains("ench")) {
            Tag enchTag = tag.get("ench");
            if (enchTag instanceof ListTag) {
                return true;
            }
        }

        return false;
    }

    public Enchantment getEnchantment(int id) {
        return getEnchantment((short) (id & 0xffff));
    }

    public Enchantment getEnchantment(short id) {
        if (!this.hasEnchantments()) {
            return null;
        }

        for (CompoundTag entry : this.getNamedTag().getList("ench", CompoundTag.class).getAll()) {
            if (entry.getShort("id") == id) {
                Enchantment e = Enchantment.getEnchantment(entry.getShort("id"));
                if (e != null) {
                    e.setLevel(entry.getShort("lvl"));
                    return e;
                }
            }
        }

        return null;
    }

    public void addEnchantment(Enchantment... enchantments) {
        CompoundTag tag;
        if (!this.hasCompoundTag()) {
            tag = new CompoundTag();
        } else {
            tag = this.getNamedTag();
        }

        ListTag<CompoundTag> ench;
        if (!tag.contains("ench")) {
            ench = new ListTag<>("ench");
            tag.putList(ench);
        } else {
            ench = tag.getList("ench", CompoundTag.class);
        }

        for (Enchantment enchantment : enchantments) {
            boolean found = false;

            for (int k = 0; k < ench.size(); k++) {
                CompoundTag entry = ench.get(k);
                if (entry.getShort("id") == enchantment.getId()) {
                    ench.add(k, new CompoundTag()
                            .putShort("id", enchantment.getId())
                            .putShort("lvl", enchantment.getLevel())
                    );
                    found = true;
                    break;
                }
            }

            if (!found) {
                ench.add(new CompoundTag()
                        .putShort("id", enchantment.getId())
                        .putShort("lvl", enchantment.getLevel())
                );
            }
        }

        this.setNamedTag(tag);
    }

    public Enchantment[] getEnchantments() {
        if (!this.hasEnchantments()) {
            return new Enchantment[0];
        }

        List<Enchantment> enchantments = new ArrayList<>();

        ListTag<CompoundTag> ench = this.getNamedTag().getList("ench", CompoundTag.class);
        for (CompoundTag entry : ench.getAll()) {
            Enchantment e = Enchantment.getEnchantment(entry.getShort("id"));
            if (e != null) {
                e.setLevel(entry.getShort("lvl"));
                enchantments.add(e);
            }
        }

        return enchantments.stream().toArray(Enchantment[]::new);
    }

    public boolean hasCustomName() {
        if (!this.hasCompoundTag()) {
            return false;
        }

        CompoundTag tag = this.getNamedTag();
        if (tag.contains("display")) {
            Tag tag1 = tag.get("display");
            if (tag1 instanceof CompoundTag && ((CompoundTag) tag1).contains("Name") && ((CompoundTag) tag1).get("Name") instanceof StringTag) {
                return true;
            }
        }

        return false;
    }

    public String getCustomName() {
        if (!this.hasCompoundTag()) {
            return "";
        }

        CompoundTag tag = this.getNamedTag();
        if (tag.contains("display")) {
            Tag tag1 = tag.get("display");
            if (tag1 instanceof CompoundTag && ((CompoundTag) tag1).contains("Name") && ((CompoundTag) tag1).get("Name") instanceof StringTag) {
                return ((CompoundTag) tag1).getString("Name");
            }
        }

        return "";
    }

    public Item setCustomName(String name) {
        if (name == null || name.equals("")) {
            this.clearCustomName();
        }

        CompoundTag tag;
        if (!this.hasCompoundTag()) {
            tag = new CompoundTag();
        } else {
            tag = this.getNamedTag();
        }
        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
            tag.getCompound("display").putString("Name", name);
        } else {
            tag.putCompound("display", new CompoundTag("display")
                    .putString("Name", name)
            );
        }
        this.setNamedTag(tag);
        return this;
    }

    public Item clearCustomName() {
        if (!this.hasCompoundTag()) {
            return this;
        }

        CompoundTag tag = this.getNamedTag();

        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
            tag.getCompound("display").remove("Name");
            if (tag.getCompound("display").isEmpty()) {
                tag.remove("display");
            }

            this.setNamedTag(tag);
        }

        return this;
    }

    public String[] getLore() {
        Tag tag = this.getNamedTagEntry("display");
        ArrayList<String> lines = new ArrayList<>();

        if (tag instanceof CompoundTag) {
            CompoundTag nbt = (CompoundTag) tag;
            ListTag<StringTag> lore = nbt.getList("Lore", StringTag.class);

            if (lore.size() > 0) {
                for (StringTag stringTag : lore.getAll()) {
                    lines.add(stringTag.data);
                }
            }
        }

        return lines.toArray(new String[0]);
    }

    public Item setLore(String... lines) {
        CompoundTag tag;
        if (!this.hasCompoundTag()) {
            tag = new CompoundTag();
        } else {
            tag = this.getNamedTag();
        }
        ListTag<StringTag> lore = new ListTag<>("Lore");

        for (String line : lines) {
            lore.add(new StringTag("", line));
        }

        if (!tag.contains("display")) {
            tag.putCompound("display", new CompoundTag("display").putList(lore));
        } else {
            tag.getCompound("display").putList(lore);
        }

        this.setNamedTag(tag);
        return this;
    }

    public Tag getNamedTagEntry(String name) {
        CompoundTag tag = this.getNamedTag();
        if (tag != null) {
            return tag.contains(name) ? tag.get(name) : null;
        }

        return null;
    }

    public CompoundTag getNamedTag() {
        if (!this.hasCompoundTag()) {
            return null;
        } else if (this.cachedNBT != null) {
            return this.cachedNBT;
        }
        return this.cachedNBT = parseCompoundTag(this.tags);
    }

    public Item setNamedTag(CompoundTag tag) {
        if (tag.isEmpty()) {
            return this.clearNamedTag();
        }

        this.cachedNBT = tag;
        this.tags = writeCompoundTag(tag);

        return this;
    }

    public Item clearNamedTag() {
        return this.setCompoundTag(new byte[0]);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isNull() {
        return this.count <= 0 || this.id == AIR;
    }

    final public String getName() {
        return this.hasCustomName() ? this.getCustomName() : this.name;
    }

    final public boolean canBePlaced() {
        return ((this.block != null) && this.block.canBePlaced());
    }

    public Block getBlock() {
        if (this.block != null) {
            return this.block.clone();
        } else {
            return new BlockAir();
        }
    }

    public int getId() {
        return id;
    }

    public int getDamage() {
        return meta;
    }

    public void setDamage(Integer meta) {
        if (meta != null) {
            this.meta = meta & 0xffff;
        } else {
            this.hasMeta = false;
        }
    }

    public boolean hasAnyDamageValue() {
        return this.meta == -1;
    }

    public int getMaxStackSize() {
        return 64;
    }

    final public Short getFuelTime() {
        if (!Fuel.duration.containsKey(id)) {
            return null;
        }
        if (this.id != BUCKET || this.meta == 10) {
            return Fuel.duration.get(this.id);
        }
        return null;
    }

    public boolean useOn(Entity entity) {
        return false;
    }

    public boolean useOn(Block block) {
        return false;
    }

    public boolean isTool() {
        return false;
    }

    public int getMaxDurability() {
        return -1;
    }

    public int getTier() {
        return 0;
    }

    public boolean isPickaxe() {
        return false;
    }

    public boolean isAxe() {
        return false;
    }

    public boolean isSword() {
        return false;
    }

    public boolean isShovel() {
        return false;
    }

    public boolean isHoe() {
        return false;
    }

    public boolean isShears() {
        return false;
    }

    public boolean isArmor() {
        return false;
    }

    public boolean isHelmet() {
        return false;
    }

    public boolean isChestplate() {
        return false;
    }

    public boolean isLeggings() {
        return false;
    }

    public boolean isBoots() {
        return false;
    }

    public int getEnchantAbility() {
        return 0;
    }

    public int getAttackDamage() {
        return 1;
    }

    public int getArmorPoints() {
        return 0;
    }

    public int getToughness() {
        return 0;
    }

    public boolean isUnbreakable() {
        return false;
    }

    @Override
    final public String toString() {
        return "Item " + this.name + " (" + this.id + ":" + (!this.hasMeta ? "?" : this.meta) + ")x" + this.count + (this.hasCompoundTag() ? " tags:0x" + Binary.bytesToHexString(this.getCompoundTag()) : "");
    }

    public int getDestroySpeed(Block block, Player player) {
        return 1;
    }

    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        return false;
    }

    public final boolean deepEquals(Item item, boolean checkDamage, boolean checkCompound, boolean checkCount) {
        if (this.getId() == item.getId() && (!checkDamage || this.getDamage() == item.getDamage()) && (!checkCount || this.getCount() == item.getCount())) {
            if (checkCompound) {
                if (Arrays.equals(this.getCompoundTag(), item.getCompoundTag())) {
                    return true;
                } else if (this.hasCompoundTag() && item.hasCompoundTag()) {
                    return this.getNamedTag().equals(item.getNamedTag());
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public final boolean deepEquals(Item item, boolean checkDamage, boolean checkCompound) {
        return this.deepEquals(item, checkDamage, checkCompound, false);
    }

    public final boolean deepEquals(Item item, boolean checkDamage) {
        return this.deepEquals(item, checkDamage, true, false);
    }

    public final boolean deepEquals(Item item) {
        return this.deepEquals(item, true, true, false);
    }

    public final boolean equals(Item item, boolean checkDamage, boolean checkCompound, boolean checkCount) {
        return this.deepEquals(item, checkDamage, checkCompound, checkCount);
    }

    public final boolean equals(Item item, boolean checkDamage, boolean checkCompound) {
        return this.deepEquals(item, checkDamage, checkCompound, false);
    }

    public final boolean equals(Item item, boolean checkDamage) {
        return this.deepEquals(item, checkDamage, true, false);
    }

    public final boolean equals(Item item) {
        return this.deepEquals(item, true, true, false);
    }

    @Override
    public Item clone() {
        try {
            Item item = (Item) super.clone();
            item.tags = this.tags.clone();
            return item;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
