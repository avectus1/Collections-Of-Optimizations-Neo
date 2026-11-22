package com.misanthropy.collections_of_optimizations;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

public final class CoOConfig {

    public static final ModConfigSpec SPEC;

    private static final List<Runnable> BAKERS = new ArrayList<>();

    private static ModConfigSpec.BooleanValue fastBiomeBlendValue;

    public static boolean masterEnabled = true;
    public static boolean releaseStoppedServer = true;
    public static boolean goetyawakenScanOncePerTick = true;
    public static boolean almostunifiedBucketDuplicates = true;
    public static boolean kubejsIndexGeneratedPack = true;
    public static boolean immersiveaircraftSkipEmptyVehicleTrace = true;

    public static boolean curiosSkipSlotlessEntities = true;
    public static boolean curiosSkipClientTickOnNonPlayers = true;
    public static boolean curiosSkipNonPlayerRenderLayer = true;
    public static boolean curiosCacheEntitySlotLookup = true;
    public static boolean curiosFastEquippedItemMiss = true;
    public static boolean curiosFastFindFirstMiss = true;
    public static boolean curiosReuseCurioMapView = true;
    public static boolean curiosReconcileCachedSlotModifiers = true;
    public static boolean curiosThreadSafeCaches = true;
    public static boolean curiosSkipForeignCapabilityLookup = true;

    public static boolean artifactsSkipClientTickOnNonPlayers = true;
    public static boolean artifactsFastPathKittySlippers = true;
    public static boolean artifactsFastPathUmbrella = true;

    public static boolean caelusSkipGroundedNonPlayers = true;

    public static boolean blockswapPaletteFilteredRetroGen = true;
    public static boolean blockswapScanPaletteIds = true;
    public static boolean justdirethingsAvoidChunkTickets = true;
    public static boolean justdirethingsLeanAreaPreviewScan = true;
    public static boolean goetydelightSkipIdleVisualEffects = true;
    public static boolean goetydelightLeanLogScan = true;
    public static boolean goetydelightCacheLogItems = true;

    public static boolean regionsunexploredCacheFurnaceBurnTimes = true;

    public static boolean gnetumSingleModIdLookup = true;

    public static boolean endinglibraryLeanCameraCapLookup = true;

    public static boolean bettercombatCacheWeaponAttributes = true;
    public static boolean bettercombatOffhandOnlyTwoHandedCheck = true;

    public static boolean citadelFastModelRender = true;

    public static boolean cofhCacheTranslucentRenderers = true;

    public static boolean createDedupeBigOutlineProbes = true;
    public static int createBigOutlinePickInterval = 5;

    public static boolean xaerolibCacheConfigProfile = true;
    public static boolean xaerolibCacheEnforcementCheck = true;

    public static int xaeroworldmapVramPollInterval = 500;
    public static int xaeroworldmapRenderProcessInterval = 4;
    public static boolean tooltipoverhaulCacheFrameTagKeys = true;
    public static boolean cablefacadesSkipEmptyLookup = true;
    public static boolean cablefacadesSkipGuardWhenEmpty = true;
    public static boolean fancymenuCacheScreenBlacklist = true;

    public static boolean geckolibReuseRenderVectors = true;
    public static boolean geckolibFastCubeWrite = true;
    public static boolean geckolibCacheBoneLookup = true;

    public static boolean saintsdragonsSkipRedundantBoneTracking = true;
    public static boolean saintsdragonsCacheShakeScan = true;
    public static boolean saintsdragonsCacheSkyfallScan = true;
    public static boolean saintsdragonsDrawHudOncePerFrame = true;
    public static boolean saintsdragonsSkipNonSeatBoneCapture = true;

    public static boolean immediatelyfastSingleBufferLookup = true;
    public static boolean immediatelyfastSkipIdleLayers = true;

    public static boolean modernfixFastRepresentedTabs = true;

    public static int fancymenuSeamlessCaptureInterval = 30;
    public static boolean fancymenuSkipRedundantScaleWrites = true;
    public static boolean fancymenuPinRenderStateToRenderThread = true;
    public static boolean fancymenuCacheScreenIdentifiers = true;
    public static boolean fancymenuMemoLocalSources = true;

    public static boolean emfDropZeroAngerEntries = true;
    public static boolean emfCacheNbtPerTick = true;
    public static boolean emiReleaseHistoryOnLeave = true;
    public static boolean emfCacheBlockEntityTypeName = true;
    public static boolean fancymenuTrackPoseStackOnlyInScreens = true;
    public static boolean emfParseTokensWithoutExceptions = true;
    public static boolean etfStacklessPropertyExceptions = true;

    public static boolean shouldersurfingSkipIdleCrosshairPick = true;
    public static double shouldersurfingCameraSweepStep = 0.125;
    public static boolean shouldersurfingMemoAdaptiveItemPatterns = true;

    public static boolean mowziesmobsReuseKnownLeader = true;
    public static boolean mahoutsukaiMemoAllEntities = true;
    public static boolean geckolibReuseBoneQueues = true;
    public static boolean geckolibPrimitiveEasing = true;
    public static boolean geckolibKeyframeLocals = true;
    public static boolean geckolibParseMolangNumbersWithoutExceptions = true;
    public static boolean ldlibSkipMissingMetadataThrow = true;
    public static int geckolibHoldDistantPosePixels = 0;
    public static int fancymenuInternetProbeSeconds = 600;
    public static boolean vanillaMemoBiomeFogColour = true;
    public static boolean vanillaSkipOrderIndependentSort = true;
    public static boolean vanillaMemoSkyColourPerTick = true;
    public static boolean vanillaSkipEmptyBeardifier = true;
    public static boolean vanillaPurgeStalePlayerMaps = true;
    public static boolean vanillaReuseNoiseChunkDensityGraph = true;
    public static boolean vanillaShareSurfaceEstimates = true;
    public static boolean vanillaPaceFramesBeforeSwap = false;


    public static boolean oculusSkipSignTextInShadowPass = true;
    public static boolean oculusSkipGlintInShadowPass = true;
    public static boolean oculusSkipNameTagsInShadowPass = true;
    public static boolean oculusSkipBannerPatternsInShadowPass = true;
    public static boolean oculusSkipBatchingWithoutShaders = true;

    public static boolean vanillaCacheArmorModelBakes = true;
    public static boolean vanillaCacheOutlineEdges = true;

    public static boolean lootrSkipIdleTileTicker = true;
    public static int lootrTileTickerBudget = 512;
    public static int lootrTileTickerInterval = 5;

    public static boolean naturesauraFastAuraChunkSweep = true;
    public static boolean naturesauraResolveWatchedChunks = true;

    public static int xaeroMinimapRenderFpsCap = 0;
    public static boolean w2w2DeferWaypointSave = true;

    public static boolean terrablenderCacheNamespaceRule = true;
    public static boolean terrablenderThreadLocalAreaCache = true;
    public static boolean terrablenderDedupeStackedLayers = true;

    public static boolean biomeswevegoneSkipForeignChunkTerrain = true;

    public static boolean biolithRestampSwappedBiomeSource = true;
    public static boolean biolithEarlyRegistryCapture = true;
    public static boolean biolithReuseBiomeEntries = true;

    public static boolean terramitySkipItemAnimationCopies = true;
    public static boolean terramitySkipForeignEntityAnimations = true;
    public static boolean terramityMemoizeProcedureRaycasts = true;
    public static boolean terramitySkipClientCurioScans = true;
    public static boolean terramitySkipArmorAnimationScan = true;
    public static boolean terramityFixPhasingShaderStomp = true;

    public static boolean armageddonSkipForeignEntityAnimations = true;
    public static boolean armageddonCacheProgressionIds = true;

    public static boolean borninchaosSkipItemAnimationCopies = true;
    public static boolean borninchaosSkipForeignEntityAnimations = true;
    public static boolean borninchaosReuseModelLocations = true;
    public static boolean borninchaosSkipRedundantDimensionRefresh = true;
    public static boolean borninchaosNarrowMinionScans = true;

    public static boolean brutalbossesSkipNonLivingBossCapability = true;
    public static boolean brutalbossesLeanBossUiTick = true;

    public static boolean brutalityFixDoubleTickCounters = true;
    public static int brutalityProximityScanInterval = 4;
    public static boolean brutalityFastArmorSetCheck = true;
    public static boolean brutalitySealTypeNoThrow = true;
    public static boolean brutalitySkipUnwatchedEffectSync = true;
    public static boolean brutalitySkipIdleBlightScan = true;

    public static boolean mutantmonstersLeanSpawnLimitScan = true;
    public static boolean mutantmonstersSkipEmptyShoulderLookup = true;

    public static boolean manybowsSkipInactiveOriginsQuiver = true;
    public static boolean cncSkipClientEncounterScans = true;
    public static int cncEncounterScanInterval = 5;
    public static boolean cncSkipForeignEntityAnimations = true;
    public static boolean cncSkipRedundantDimensionRefresh = true;
    public static boolean cncGateCaribouDashScan = true;
    public static boolean cncCacheWechugeFogScan = true;
    public static int cncSasquatchTextureScanInterval = 4;
    public static boolean cncSkipClientWoodKnockScan = true;
    public static boolean cncFixSobbingShaderStomp = true;

    public static boolean crittersandcompanionsGateRedPandaAvoidGoal = true;
    public static int crittersandcompanionsKoiLuckScanInterval = 10;
    public static boolean crittersandcompanionsMemoNecklaceLookup = true;
    public static boolean crittersandcompanionsSkipEmptyLeashRender = true;
    public static int crittersandcompanionsCritterItemScanInterval = 4;
    public static boolean crittersandcompanionsFastBehaviourLookup = true;

    public static boolean cucumberLeanTileDispatch = true;
    public static boolean cucumberLeanTagTooltip = true;
    public static boolean cucumberCacheTagLookup = true;

    public static boolean konkreteDeferSoundVolumeThread = true;

    public static boolean dynamictreesLeanLeafPlacement = true;
    public static boolean dynamictreesLeanLeafHydration = true;
    public static boolean dynamictreesCacheThickTrunkShape = true;
    public static boolean dynamictreesBoundedRapidRot = true;

    public static boolean mysticalagricultureLeanAugmentLookup = true;
    public static boolean mysticalagricultureLeanAbilityCache = true;
    public static boolean mysticalagricultureSkipIdleSoulExtractor = true;
    public static boolean mysticalagricultureMemoCropNameKey = true;
    public static boolean mysticalagricultureCacheFurnaceRecipe = true;

    public static boolean skarriermobsSkipForeignEntityAnimations = true;
    public static boolean skarriermobsLeanDaylightBurnScan = true;
    public static boolean skarriermobsLeanResisteelToolScan = true;
    public static boolean skarriermobsLeanResisteelSwordScan = true;
    public static boolean skarriermobsLeanResisteelSetTracking = true;
    public static boolean skarriermobsLeanTargetProximityScans = true;
    public static boolean skarriermobsNarrowRegionScans = true;
    public static boolean industrialforegoingSkipStasisTagChurn = true;
    public static boolean enigmaticaddonsSkipUnsetPersistentData = true;
    public static boolean enigmaticaddonsSkipIdleFrostScan = true;
    public static boolean enigmaticdelicacySkipUnsetPersistentData = true;
    public static int enigmaticlegacyGuardianHeartScanInterval = 4;
    public static boolean enigmaticlegacyNarrowCursedRingAngerScan = true;
    public static boolean enigmaticlegacyGroupTabMembers = true;
    public static boolean enigmaticlegacyPruneAngeredGuardians = true;
    public static boolean enigmaticlegacyCachedCurioLookup = true;
    public static boolean travelopticsLeanClimbCurioScan = true;
    public static boolean travelopticsLeanCastEffectChecks = true;
    public static boolean celestialenchantmentsSkipUnenchantedTick = true;
    public static boolean celestialenchantmentsLeanSlotEnchScan = true;
    public static boolean celestialcoreLeanItemTransformScan = true;
    public static boolean celestialcoreMatchStatBlockRecipe = true;
    public static boolean adamsarsplusLeanDisruptionPurge = true;
    public static boolean adamsarsplusSkipClientFlameAura = true;
    public static int adamsarsplusFlameAuraScanInterval = 10;
    public static boolean uniqueaccessoriesLeanWaistWarmerScan = true;
    public static boolean uniqueaccessoriesSkipUnsetPersistentData = true;

    public static boolean bloodmagicCacheArcRecipeList = true;
    public static boolean bloodmagicCacheArcFurnaceRecipe = true;
    public static boolean bloodmagicFastRoutingConnectivity = true;

    public static boolean animusCacheEquivalencyPreview = true;

    public static boolean patchouliCacheBookItemLookup = true;

    public static boolean structurifyFastStructureSetLookup = true;
    public static boolean structurifySkipDisabledStructureChecks = true;
    public static boolean structurifyLeanHeightCache = true;
    public static boolean structurifyLeanOverlapSections = true;
    public static boolean structurifyCacheStructureSetEntries = true;
    public static boolean structurifySkipStartCheckWrap = true;

    public static boolean lithostitchedFastTemplateShuffle = true;
    public static boolean lithostitchedCacheBiomeModifierList = true;
    public static boolean lithostitchedCacheBlockSwapMap = true;

    public static boolean bossesriseNarrowCinematicScan = true;
    public static boolean bossesriseLeanVfxScan = true;
    public static boolean soulsweaponsLeanDespawnTimer = true;
    public static boolean soulsweaponsCacheModPresence = true;
    public static boolean soulsweaponsSkipIdlePostureMax = true;
    public static boolean konweaponSkipItemAnimationCopies = true;
    public static boolean immersiveaircraftBatchOverlay = true;
    public static boolean ftbchunksSkipHiddenMinimapWork = true;
    public static boolean ftbchunksFastRegionWrite = true;
    public static boolean ftbchunksMemoMinimapRegions = true;
    public static boolean ftbchunksCoalesceMinimapUploads = true;

    public static boolean jeiWeakUnsupportedContainers = true;
    public static boolean radiumPartEntityCollisions = true;
    public static boolean radiumMemoEntityTouchableFlag = true;
    public static boolean radiumNoThrowMethodOverrideScan = true;
    public static boolean radiumFixHashPaletteCopy = true;
    public static boolean radiumKeepBorderListeners = true;
    public static boolean modernfixOrderedModelLocations = true;
    public static boolean farmersdelightShareToolActionScan = true;
    public static boolean terrablenderLazyNamespaceRules = true;
    public static boolean vanillaSkipIrrelevantFireScan = true;
    public static boolean vanillaDryRaycast = true;
    public static boolean vanillaLeanGameEventLookup = true;
    public static boolean vanillaPrecheckBlockRemovalScan = true;
    public static boolean vanillaPrecheckRepellentScan = true;
    public static boolean vanillaDeferSunBurnWetCheck = true;
    public static boolean vanillaSkipEmptyDisabledFlags = true;
    public static boolean forgeStatusPingChannelGroups = true;
    public static boolean forgeRenderLayerMemo = true;
    public static boolean forgePlainTranslationFormat = true;
    public static boolean forgeCustomPayloadHeapCopy = true;
    public static boolean vanillaBoundBeardifierReach = true;
    public static boolean vanillaNumberTagChecks = true;
    public static boolean vanillaDeferBlockedTickContainers = true;
    public static boolean vanillaShareColumnHeights = true;
    public static boolean createSinglePassContraptionCollision = true;
    public static boolean ftbchunksDisableMapWriting = false;
    public static boolean ftbchunksDisableWaystoneIcons = false;
    public static boolean punchyCacheResourceStackMisses = true;
    public static boolean punchySkipEmfDebugTrace = true;
    public static boolean punchyMemoBlacklistMatch = true;
    public static boolean punchyCacheItemIds = true;
    public static boolean punchySkipUnusedGeoScan = true;
    public static boolean punchyMemoActiveMixOptions = true;
    public static boolean punchyMemoAnimationMerge = true;
    public static boolean punchyMemoSpearChecks = true;
    public static boolean embeddiumCacheShaderSources = true;
    public static boolean alltheleaksSkipCrashReportGc = true;
    public static boolean l2hostilitySkipTraitlessCapLookup = true;
    public static boolean l2hostilityLeanCurioItemCheck = true;
    public static boolean l2hostilityNamePlateSightPerTick = true;
    public static boolean fallentraitsCacheSpawnBonus = true;
    public static boolean fallentraitsClampInfernalFollowRange = true;
    public static boolean fallentraitsIndexDevourers = true;
    public static boolean fallentraitsSkipUnchangedRelicModifiers = true;
    public static boolean fallentraitsSkipUnusedStreakReset = true;
    public static boolean fallentraitsSkipUnsetPersistentData = true;
    public static boolean fallentraitsSkipUnmarkedEffectOwner = true;
    public static boolean fallentraitsSkipIdleRootSlotList = true;
    public static boolean fallentraitsCacheRenderScale = true;
    public static boolean fallentraitsSkipUnchangedDayNightModifiers = true;
    public static boolean iceandfireFastEntityDataLookup = true;
    public static boolean iceandfireSkipPathDebugRender = true;
    public static boolean iceandfireSkipEmptyArmorLayer = true;
    public static boolean iceandfireCacheDragonTexture = true;
    public static boolean iceandfireSkipEmptyDragonLayers = true;
    public static boolean iceandfireLeanMultipartTick = true;
    public static int iceandfireDragonTargetSearchHeight = 32;
    public static boolean iafdragonfixStructureDens = true;
    public static int iafdragonfixRoostSpawnDistance = 800;
    public static int iafdragonfixCaveSpawnDistance = 800;
    public static boolean mowziesmobsFastCapabilityLookup = true;
    public static boolean mowziesmobsDedupeCapabilityAttach = true;
    public static boolean mowziesmobsCacheCameraShakeScan = true;
    public static int mowziesmobsBossMusicPacketInterval = 5;
    public static boolean mowziesmobsSkipMissingPrevTargetThrow = true;
    public static boolean mowziesmobsLeanBoneLookup = true;
    public static boolean mowziesmobsHoistChainRenderMatrix = true;
    public static int mowziesmobsDynamicChainSubstepCap = 4;
    public static boolean mowziesmobsCacheUmvuthanaLeader = true;
    public static boolean mowziesmobsLeanModelBoxVectors = true;
    public static boolean mowziesmobsSkipBlankElokosaTransform = true;
    public static boolean mowziesmobsLeanLayerBoneScan = true;
    public static boolean mowziesmobsCacheEffectRenderTypes = true;
    public static boolean pickupnotifierSkipOpaqueSpriteBuffer = true;
    public static boolean placeboSkipEmptyEnchantmentEvent = true;

    public static boolean photonLeanParticleQuads = true;
    public static boolean photonLeanParticleLight = true;
    public static boolean photonLeanTrailVertices = true;
    public static boolean photonDropEmptyEffectCacheEntries = true;
    public static boolean integratedapiSkipEmptyBeardifier = true;
    public static boolean echelonCacheTierAttributeUuids = true;
    public static boolean elysiumapiMemoClimateSample = true;
    public static boolean elysiumapiCacheSoundTransformers = true;
    public static boolean balmMemoDynamicModelKeys = true;
    public static boolean balmCacheConfigIdentifier = true;

    public static boolean dungeoncrawlSkipBlockEntityProbe = true;

    public static boolean moonlightSkipEmptyMapMarkerScan = true;

    public static boolean pehkuiLeanScaleTick = true;
    public static boolean pehkuiMemoModifierType = true;
    public static boolean pehkuiCacheEveryScaleType = true;

    public static boolean tonsofenchantsSkipAbsentAttributeRemoval = true;
    public static boolean tonsofenchantsFrostbiteSkipClient = true;
    public static boolean tonsofenchantsLeanAttributeLookup = true;
    public static boolean tonsofenchantsSinglePhasePlayerTick = true;

    public static boolean subtleeffectsDisplayTickPlan = true;
    public static boolean subtleeffectsMemoBiomeFiddles = true;
    public static boolean subtleeffectsIdleFluidHooks = true;
    public static boolean subtleeffectsUnusedBubbleTint = true;
    public static boolean subtleeffectsOneRangeCheck = true;
    public static boolean subtleeffectsDryChestTicker = true;
    public static boolean subtleeffectsLeanTickerRemoval = true;

    public static boolean immersiveweatheringFastSpread = true;
    public static boolean immersiveweatheringGrowthTable = true;
    public static boolean immersiveweatheringOneChanceRoll = true;
    public static boolean immersiveweatheringAreaScan = true;
    public static boolean immersiveweatheringLeanFluidGenerators = true;
    public static boolean immersiveweatheringLeafPileTint = true;

    public static boolean arsengSkipDeadRelayListeners = true;
    public static boolean arsengGateGenericInvWrapper = true;

    public static boolean perceptionShareDefaultTrailData = true;

    public static boolean quarkSkipPigLitterTagChurn = true;

    public static boolean zetaLeanStructureReplacement = true;
    public static boolean zetaShareEventWrappers = true;

    public static boolean goetyCacheCapabilityOptional = true;
    public static boolean goetySkipCapabilityFallback = true;
    public static boolean goetyCacheBrewEffects = true;
    public static boolean goetyFastEmptyAllyCheck = true;
    public static boolean goetyFastCurioItemMiss = true;
    public static boolean goetyMemoCurioFilter = true;
    public static boolean goetySkipBossMusicTargetLookup = true;
    public static boolean goetyCacheFogWightScan = true;
    public static boolean goetyCacheShakeScan = true;

    public static boolean cataclysmCacheShakeScan = true;
    public static boolean cataclysmSkipIdleAnimate = true;
    public static boolean cataclysmReuseLayerPose = true;
    public static boolean cataclysmLeanPoseReset = true;
    public static boolean cataclysmCacheCicleTexture = true;
    public static boolean cataclysmCacheTentacleLight = true;
    public static boolean cataclysmPruneLightningRenders = true;
    public static boolean cataclysmScanOnlyOnDamageTicks = true;
    public static boolean cataclysmSkipClientBeamEntityScan = true;
    public static boolean cataclysmSkipEmpScanWithoutEmp = true;
    public static boolean cataclysmTentacleDistanceBeforeSight = true;
    public static boolean cataclysmUnsyncedMissileFuse = true;
    public static boolean cataclysmSandstormSoundCleanup = true;
    public static boolean cataclysmCacheCapabilities = true;
    public static boolean cataclysmShareItemRenderer = true;
    public static boolean cataclysmBossMusicOnChange = true;
    public static boolean cataclysmFastStructureCheck = true;
    public static boolean cataclysmBasaltPrecheck = true;
    public static boolean cataclysmSkipIdleArmPoseEvent = true;
    public static boolean cataclysmQuietTentacleLoad = true;
    public static boolean cataclysmLazyMonstrousHelmScan = true;
    public static boolean cataclysmLeanDeeplingSwimRefresh = true;
    public static boolean cataclysmDeeplingRideCheckFirst = true;
    public static boolean cataclysmSwimCheckFirst = true;
    public static boolean cataclysmLeanMonstrosityBlockBreaking = true;
    public static boolean cataclysmLeanRiftBlockBreaking = true;
    public static boolean cataclysmSkipServerLegSolver = true;
    public static boolean cataclysmCacheHomeDimension = true;
    public static boolean dodosmobsCacheShakeScan = true;
    public static boolean eeeabsmobsCacheShakeScan = true;
    public static boolean fromtheshadowsCacheShakeScan = true;
    public static boolean gtbcsCacheShakeScan = true;

    public static boolean gtbcsgeomancyGateGrandmasterSpawnScan = true;
    public static boolean gtbcsgeomancySkipNonCasterCastingTick = true;
    public static boolean legendarymonstersCacheShakeScan = true;
    public static int legendarymonstersBossMusicInterval = 10;
    public static boolean legendarymonstersSkipDeadZoomWork = true;
    public static boolean legendarymonstersGuardCameraNullPlayer = true;
    public static boolean legendarymonstersFixAnnihilatorProcChance = true;

    public static boolean mythsandlegendsCacheFogBossScan = true;
    public static boolean mythsandlegendsCacheShakeScan = true;

    public static boolean ambientsoundsMemoBiomeMatch = true;

    public static boolean timmStreamMusic = true;
    public static boolean timmSkipStructureScan = true;
    public static boolean timmCacheMusic = true;
    public static boolean timmDebugLogToDebug = true;
    public static int arsnouveauSkyTextureInterval = 1;

    public static boolean arselementalFixGlobalHealBoost = true;
    public static boolean arselementalSkipBangleScan = true;
    public static boolean arselementalLeanCurioFallback = true;
    public static boolean arselementalSkipNonSonicMirrorRoll = true;
    public static boolean arselementalFixCurioBagKeybind = true;
    public static boolean pehkuiMemoInteractionBoxScales = true;
    public static boolean pehkuiCacheClientScales = true;

    public static boolean relicsClampEssenceSpeed = true;
    public static boolean morerelicsHoistEquippedCurios = true;
    public static boolean morerelicsSafeDelayedTasks = true;
    public static boolean terracurioCachedCurioLookup = true;
    public static boolean terracurioLeanAttributeMap = true;
    public static boolean terracurioSkipIdleAggroScan = true;
    public static boolean terracurioSkipUnchangedIceFlag = true;
    public static boolean cosmeticarmorPerPlayerRestoreQueue = true;
    public static double relicsEssenceMaxSpeed = 4.0D;

    public static boolean morehitboxesSkipAbsentMultiPartFilter = true;

    public static boolean goetyrevelationCacheHaloLookup = true;
    public static boolean revelationfixSkipMobFluidStandScan = true;
    public static boolean revelationfixSkipOutlineScan = true;
    public static boolean revelationfixSkipNonSpiderHurtByTargetEvents = true;

    public static boolean macabreSkipForeignEntityAnimations = true;
    public static boolean macabreSkipItemAnimationCopies = true;
    public static boolean macabreCoalesceVariableSync = true;

    public static boolean alexsmobsSkipCreeperAvoidGoals = true;
    public static int alexsmobsSpiderFlyScanInterval = 10;
    public static boolean alexsmobsReleaseLevelMaps = true;
    public static boolean alexsmobsCrowScanPrecheck = true;

    public static boolean alexscavesMemoRareBiomeQuads = true;
    public static boolean alexscavesMemoClimateSample = true;
    public static boolean alexscavesCacheShakeScan = true;
    public static boolean alexscavesSingleMagnetQuery = true;

    public static boolean architecturyDirectEventDispatch = true;

    public static boolean adastraMemoPlanetDefaults = true;

    public static boolean supplementariesLeanEndermanSkullWatch = true;
    public static boolean supplementariesSkipNonSignCapSync = true;
    public static boolean supplementariesMemoMapTintLookup = true;

    public static boolean amendmentsSkipIdleSwaySync = true;

    public static boolean copycatsMemoStateOcclusion = true;
    public static boolean copycatsFastMigrationChecks = true;
    public static boolean copycatsCachedModelConfig = true;
    public static boolean copycatsLeanVirtualWorldCheck = true;

    public static int itemEntityRenderCap = 1;
    public static boolean vanillaMemoGlyphFontSet = true;
    public static boolean vanillaMemoOutlineStyle = true;
    public static boolean vanillaFasterStructureLocation = true;
    public static boolean vanillaTrimTemplateTopAir = true;
    public static boolean vanillaFixBoatFallDamage = false;
    public static boolean vanillaPredictableItemDrops = false;
    public static boolean vanillaLeanTrackerSectionPos = true;
    public static boolean vanillaLeanSuffocationScan = true;
    public static boolean vanillaLeanMenuBroadcast = true;
    public static boolean vanillaLeanTrackerDelta = true;
    public static boolean vanillaCacheBiomeQuartLookups = true;
    public static boolean vanillaMemoCameraFluid = true;
    public static boolean vanillaIndexModFiles = true;
    public static boolean vanillaIndexModFilesInBackground = true;
    public static boolean vanillaIndexModFileListing = true;
    public static boolean vanillaCachePackNamespaces = true;
    public static boolean vanillaSkipAbsentPackNamespaces = true;
    public static boolean vanillaPlaceFeaturesWithoutStreams = true;
    public static boolean vanillaSkipEmptyOwnerUuidParse = true;
    public static boolean vanillaCacheStaticHardwareReport = true;
    public static boolean vanillaIndexZipPacks = true;
    public static boolean vanillaMemoSkyColour = true;
    public static boolean vanillaPurgeGhostPlayers = true;
    public static boolean vanillaFastBiomeBlend = true;
    public static double vanillaMovementCheckSlack = 100.0D;
    public static boolean vanillaDisableFlyingKick = true;
    public static boolean vanillaCacheModelPartLookups = true;
    public static boolean vanillaLeanKeyframeAnimation = true;
    public static boolean vanillaUniformSectionLookup = true;
    public static boolean vanillaAxisAlignedRotation = true;
    public static boolean vanillaLeanBlockPosRange = true;
    public static boolean createsolarMemoGogglesLookup = true;
    public static int xaeroworldmapUploadHeadroomMicros = 1000;
    public static boolean xaeroworldmapOpenReflectedFields = true;
    public static boolean xaeroworldmapOrphanUploadPbo = true;
    public static boolean xaeroworldmapIdleMapFrameWait = true;
    public static int xaeroworldmapMapFrameSpinTail = 200;
    public static boolean gnetumMemoCacheSettings = true;
    public static boolean mcreatorShareDefaultPlayerVariables = true;

    public static boolean distanthorizonsClearBiomeCachesOnUnload = true;
    public static boolean distanthorizonsMemoBiomeBlendColors = true;
    public static boolean distanthorizonsCacheChunkBiomeLookup = true;

    public static boolean summonityCacheOwnedMinionScans = true;
    public static int summonityIdleTargetScanInterval = 4;
    public static boolean summonityCacheDragonParts = true;
    public static boolean summonityFastCurioMiss = true;
    public static boolean summonitySkipUnchangedSlotSync = true;
    public static boolean summonityMemoDroneModifierIds = true;

    public static boolean oddaccessoriesFastCurioMiss = true;
    public static boolean oddaccessoriesGateMealToyScan = true;
    public static int oddaccessoriesCloakAggroScanInterval = 4;
    public static int oddaccessoriesCloakActionBarInterval = 10;
    public static int oddaccessoriesNameUpdateInterval = 10;
    public static boolean oddaccessoriesMemoBagRewardPool = true;
    public static boolean titaniumSkipRedundantProgressWrite = true;
    public static boolean titaniumMemoRecipeLists = true;
    public static boolean titaniumSkipEmptyTransferScan = true;
    public static boolean titaniumCachedFacingState = true;

    public static boolean domesticationinnovationFastEnchantLookup = true;
    public static boolean domesticationinnovationSafeCollarPrune = true;

    public static boolean moreartifactsFastEquipState = true;
    public static boolean moreartifactsTickEndPhaseOnly = true;
    public static boolean moreartifactsPruneDamageMaps = true;

    public static boolean simplymoreSkipUnheldFootfalls = true;

    public static boolean celestisynthFastCapabilityLookup = true;

    public static boolean hexereiDisableDynamicLights = true;
    public static boolean hexereiCacheLightToggle = true;
    public static boolean hexereiSkipEmptyLightScan = true;
    public static boolean hexereiLazyModelSwaps = false;

    public static boolean covenantofthesevenTickEndPhaseOnly = true;
    public static boolean covenantofthesevenSilenceDebugSpam = true;

    public static int simplyswordsConfigRereadInterval = 1000;

    public static boolean apothicattributesSkipReplacedTooltipModifiers = true;

    public static int easyvillagersDirtyMarkInterval = 20;

    public static boolean companionsLeanEntityTracker = true;
    public static boolean companionsDeferTeslaRebuild = true;
    public static int companionsTeslaPruneInterval = 200;
    public static boolean companionsFastOwnerWalk = true;

    public static boolean immersivearmorsLeanSteampunkOverlay = true;
    public static boolean immersivearmorsSkipClientSetBonusWrite = true;

    public static int starcatcherFishRadarScanIntervalMs = 5000;
    public static boolean starcatcherTournamentProfileFallback = true;

    public static boolean neobackportsCacheComponentDefaults = true;

    public static boolean faunifyLeanPlayerProximityScan = true;
    public static int faunifyMillipedeParentScanInterval = 20;

    public static int undergardenRotspawnRepelScanRadius = 48;
    public static boolean undergardenStaggerRotspawnRepelScan = true;
    public static boolean undergardenLeanScintlingGooTrail = true;
    public static int undergardenScintlingIdleGooInterval = 10;
    public static boolean undergardenMuncherRespectMobGriefing = true;
    public static boolean undergardenSkipRedundantMasticatorSpeedSync = true;
    public static boolean undergardenTolerateUnregisteredForgottenTargets = true;

    public static boolean darkdoppelgangerLeanBossBarNameCheck = true;
    public static boolean darkdoppelgangerFixDoubleMinionSpawn = true;
    public static boolean darkdoppelgangerSkipDuplicateSpellScan = true;

    public static boolean torchmasterCacheLevelLightRegistry = true;
    public static boolean torchmasterMegaTorchRangeCheckFirst = true;

    public static boolean textanimatorSkipUnknownEffectTags = true;
    public static boolean textanimatorStacklessEffectErrors = true;
    public static boolean textanimatorSkipIdleTypewriterProbe = true;
    public static boolean textanimatorParseParamsWithoutExceptions = true;
    public static boolean itemfiltersCacheTagFilterKeys = true;
    public static boolean asyncparticlesConstantSamplerNames = true;

    public static boolean cognitionSkipClientInfection = true;
    public static boolean cognitionCacheInfectingRecipes = true;
    public static boolean cognitionLeanPlayerScan = true;
    public static boolean cognitionSkipForeignTooltipScan = true;

    public static boolean opposingforceCacheStealthVisibility = true;
    public static boolean opposingforceSkipClientEffectTicks = true;

    public static boolean peyroscytheServerOnlyAuraScans = true;
    public static int peyroscytheAuraEffectApplyInterval = 10;
    public static boolean peyroscytheBlackFlameWingsServerOnlyContact = true;

    public static boolean somakespellsSkipUnlinkedConnectionTick = true;
    public static boolean somakespellsCacheBalanceUuids = true;

    public static boolean biomancySkipEmptySpawnQuery = true;
    public static boolean biomancyCacheSpatialStorage = true;
    public static boolean biomancyCacheSpatialLevelKey = true;

    public static boolean notenoughanimationsCachePetScan = true;
    public static boolean notenoughanimationsSkipServerSwordTracking = true;

    public static boolean lethalitySkipDuplicateTickPhases = true;
    public static boolean lethalityServerOnlyBiomeBladeState = true;
    public static boolean lethalityLeanBonusDropCheck = true;
    public static boolean lethalitySkipUnequippedCharmScan = true;
    public static boolean lethalityFixServerSideSosName = true;

    public static boolean castercuriosbonusCachedCurioLookup = true;
    public static boolean castercuriosbonusServerOnlyManaToHealth = true;
    public static boolean castercuriosbonusPerPlayerCurioThrottle = true;

    public static boolean legendaryspellbooksLeanTornadoScan = true;
    public static boolean legendaryspellbooksLocalPlayerDriftScan = true;
    public static int legendaryspellbooksAnnihilationBonusInterval = 10;

    public static boolean meetyourfightCachedCurioLookup = true;
    public static boolean meetyourfightServerOnlyGlowSweep = true;

    public static boolean bmdLeanWardSpawnScan = true;
    public static boolean bmdLeanMonolithExplosionScan = true;
    public static boolean bmdLeanLevitationFlightScan = true;
    public static boolean bmdSinglePlayerTickPhase = true;

    public static boolean mahoutsukaiSkipClientAuthorityTick = true;
    public static boolean mahoutsukaiLeanRipperTick = true;
    public static boolean mahoutsukaiLeanProjectorEnchantScan = true;
    public static boolean mahoutsukaiFixStaffSensitivity = true;
    public static boolean mahoutsukaiPurgeInsightTargets = true;

    public static boolean primitivemobsSkipVillagerGoblinAvoid = false;
    public static int primitivemobsVillagerGoblinAvoidInterval = 5;
    public static int primitivemobsSheepmanPiglinAvoidInterval = 5;

    public static int ribbitsWaterCropsScanInterval = 20;
    public static int ribbitsFishScanInterval = 20;
    public static int ribbitsBandScanInterval = 10;
    public static boolean ribbitsFixPerformerCrash = true;
    public static boolean hazennstuffCacheGeoResources = true;

    public static boolean speciesFixDoubleRenderListener = true;
    public static int speciesWickedHauntScanInterval = 20;
    public static int speciesLimpetScareScanInterval = 10;

    public static boolean cookingforblockheadsIdempotentCompatReload = true;
    public static boolean cookingforblockheadsCacheOvenSmeltingResult = true;
    public static boolean cookingforblockheadsOvenFacingFromState = true;
    public static boolean cookingforblockheadsIndexRecipeBookScan = true;

    public static boolean integratedvillagesIsolateWorkstationPicks = true;

    public static boolean deeperdarkerLeanBreakEvent = true;
    public static boolean deeperdarkerFixSculkJawGuards = true;
    public static boolean deeperdarkerFixSculkJawRepeatBite = true;
    public static boolean deeperdarkerLeanStaffChargeTag = true;
    public static boolean deeperdarkerReuseHeartRandom = true;

    public static boolean goetyhostilityLeanBossMusicTick = true;
    public static boolean goetyhostilityLeanGolemProximityScan = true;
    public static boolean goetyhostilityLeanWildfireDropProbe = true;

    public static boolean goetycataclysmRegisterGolemsOnce = true;
    public static boolean goetycataclysmSkipGolemUnloadSweep = true;
    public static boolean goetycataclysmFixWantLootingCheck = true;
    public static boolean goetycataclysmLeanNecromancerSummonCheck = true;

    public static boolean fdbossesSkipArenaProtectionScan = true;
    public static boolean fdbossesSkipKineticFieldCollision = true;
    public static boolean fdbossesSkipHellscapeSkyScan = true;
    public static boolean fdbossesLocalOnlyPhaseSphereTick = true;

    public static int cnbCactemFollowElderScanInterval = 10;
    public static int cnbCactemBecomeElderScanInterval = 20;
    public static int cnbCactemTradeScanInterval = 10;
    public static int cnbCactemHealScanInterval = 10;
    public static int cnbSporelingConvertScanInterval = 10;
    public static boolean cnbSkipClientAnimDataWrites = true;

    public static boolean mobgrindingutilsFastEnderInhibitorScan = true;
    public static boolean mobgrindingutilsLeanFanBroadcast = true;
    public static boolean mobgrindingutilsCacheChickenSwellModel = true;
    public static boolean mobgrindingutilsLeanChickenTag = true;

    public static boolean transmogIdentityAppearanceCache = true;

    public static boolean ironsspellbooksLeanCurioEquipCheck = true;
    public static boolean ironsspellbooksClearStaleSyncedData = true;
    public static boolean ironsspellbooksReuseArmorModelLocations = true;

    public static boolean hostilenetworksCacheModelHolder = true;
    public static boolean hostilenetworksSkipBrokenModelOnKill = true;
    public static boolean hostilenetworksLeanEntityCache = true;

    public static boolean extrahnnFixSharedBaseDrop = true;
    public static boolean extrahnnCacheStoredModels = true;
    public static int extrahnnRenderedModelCap = 4;

    public static boolean sanguinenetworksThrottleSync = true;
    public static boolean sanguinenetworksCacheTickStats = true;
    public static boolean sanguinenetworksSkipClientRerender = true;

    public static boolean l2archeryCacheBowUpgrades = true;
    public static boolean l2archeryCacheArrowTexture = true;
    public static boolean l2archerySkipIdleBowFov = true;
    public static boolean l2archeryCacheBowInfoText = true;

    public static boolean l2backpackLeanBagCount = true;
    public static boolean l2backpackLeanBagTagRead = true;
    public static boolean l2backpackCacheBackLayerScan = true;

    public static boolean l2libraryLeanEffectIconScan = true;
    public static boolean l2libraryFixStopTrackingSync = true;
    public static boolean l2libraryCacheTrackedEffects = true;
    public static boolean l2libraryLeanCapabilityResolve = true;
    public static boolean l2librarySkipEmptyConditionalTick = true;

    public static boolean l2complementsLeanAttackChecks = true;
    public static boolean l2complementsLeanEnchantmentLookup = true;
    public static boolean l2complementsSkipUnrelatedTooltips = true;
    public static boolean l2complementsFixBreakerSetRace = true;

    public static boolean blessfulledFixGeckolibHurtOverlay = true;
    public static boolean blessfulledSkipInvisibleHurtOverlay = true;
    public static boolean blessfulledSkipIdleSwingSound = true;
    public static boolean blessfulledBatchPopupFlush = true;

    public static boolean tomeofbloodFixMimicAugmentIndex = true;
    public static boolean tomeofbloodCacheLivingUpgradeUuids = true;
    public static boolean tomeofbloodSilenceServerStartLog = true;

    public static boolean betterspellcastingExpireQueuedSpell = true;
    public static int betterspellcastingQueuedSpellWindow = 40;
    public static boolean betterspellcastingSkipRedundantSwingPacket = true;

    public static boolean arsadditionsSkipStowedCharmTick = true;
    public static boolean arsadditionsCacheRuinedPortalScan = true;
    public static int arsadditionsMagelightParticleDistance = 32;

    public static boolean duplicationlessLazyEntityTracker = true;

    public static boolean fluidiumCacheClaimLookups = true;
    public static boolean fluidiumRefreshDepartedDimension = true;

    public static int corpseIdleScanInterval = 20;
    public static int corpseDeathSweepInterval = 300;

    public static boolean storagedrawersCacheCountLabels = true;
    public static boolean storagedrawersNarrowCountSyncRadius = true;
    public static boolean skyarenaLeanSpawnScan = true;
    public static int skyarenaSpawnScanCacheTicks = 20;
    public static boolean skyarenaPruneAltarPlayerMaps = true;
    public static boolean simplyswordsoverhaulRestoreDashStateOnLogout = true;
    public static boolean simplyswordsoverhaulResetSchedulerOnServerStop = true;
    public static boolean simplyswordsoverhaulLeanMoltenEdgeBuffs = true;

    public static boolean puffishattributesSkipEmptyModifiers = true;

    public static boolean toomanyglyphsLeanChainSearch = true;
    public static int toomanyglyphsRayParticleBudget = 1024;

    public static boolean tactBatchCompendiumUnlockSave = true;
    public static boolean tactClampSubterranodonMeter = true;

    public static boolean dummmmmmySkipUnusedDamageNumbers = true;

    public static boolean sophisticatedbackpacksSkipEntityDataChecks = true;
    public static int dummmmmmyScarecrowScanInterval = 10;
    public static boolean dummmmmmyTolerateUnregisteredDamageTypes = true;
    public static boolean dummmmmmyBatchDamageNumberFlush = true;

    public static boolean refinedmodSkipClientWaveTerrain = true;
    public static boolean refinedmodLeanWaveTerrainScan = true;
    public static boolean refinedmodDisableWaveTerraforming = false;
    public static boolean refinedmodClampHuntersMarkBonus = true;
    public static boolean refinedmodDiscardOwnerlessStormBlade = true;
    public static int refinedmodHuntersMarkMapCap = 512;

    public static boolean farmersdelightLeanBasketScan = true;
    public static boolean farmersdelightSkipNoopItemSync = true;
    public static boolean farmersdelightSkipEmptyStoveWork = true;
    public static boolean farmersdelightSkipClientTemptGoals = true;
    public static boolean farmersdelightLeanTemptGoalPriority = true;
    public static boolean farmersdelightCacheOverlayLookup = true;

    public static boolean netherexpCacheAntidoteEffect = true;
    public static boolean netherexpLeanSanctumCompassTick = true;
    public static boolean netherexpLeanSanctumCompassTooltip = true;
    public static boolean netherexpLeanCandlePlayerScan = true;
    public static boolean netherexpThrottleCandleEffectRefresh = true;

    public static boolean powahSkipIdleEnergyPush = true;
    public static boolean powahLeanContainerCharge = true;
    public static boolean powahSkipIdlePlayerCharge = true;
    public static boolean powahSkipNoopItemEnergyWrites = true;

    public static boolean arsnspellsLeanManaBarOverlayGate = true;
    public static boolean arsnspellsLeanSourceJarTick = true;
    public static boolean arsnspellsLeanResonanceTick = true;
    public static boolean arsnspellsCarryCooldownsThroughClone = true;

    public static boolean modularroutersFastExtruderSideCheck = true;
    public static boolean modularroutersCacheTargetHandlerLookup = true;
    public static boolean modularroutersClampVacuumRegulator = true;
    public static boolean modularroutersSkipEmptyBeamTick = true;

    public static boolean lionfishapiLeanFluidCollision = true;
    public static boolean lionfishapiSkipIdleFluidRenderEvent = true;
    public static boolean lionfishapiCacheModelDescendants = true;
    public static boolean lionfishapiFastModelRender = true;
    public static boolean lionfishapiReuseBoxRotations = true;
    public static boolean lionfishapiCacheModelParts = true;
    public static boolean lionfishapiLeanModelAnimator = true;
    public static boolean lionfishapiLeanKeyframeAnimation = true;

    public static boolean tomeofwondersSafeSquillSchoolWorldgen = true;
    public static boolean tomeofwondersServerOnlyWhirligigPower = true;
    public static int tomeofwondersBaitfishSchoolCap = 24;

    public static boolean bowenchantsDrawSpeedBowsOnly = true;
    public static boolean bowenchantsStrictEnhancedPower = true;

    public static boolean createadditionFixAmbientSoundToggle = true;
    public static boolean createadditionSoundscapeTickOnce = true;
    public static boolean createadditionLeanHeldWireCheck = true;

    public static boolean snufflesServerSideFluffRegrow = true;
    public static boolean snufflesLeanCarpetTouchCheck = true;
    public static boolean snufflesCacheModelParts = true;

    public static boolean mekaweaponsGunClientOnlySound = true;
    public static boolean mekaweaponsGunServerOnlyLaser = true;
    public static boolean mekaweaponsTanaSweepNeedsFullSwing = true;
    public static boolean mekaweaponsBowOffhandArrowStats = true;

    public static boolean dungeonsdelightPerPlayerTenacityInterval = true;
    public static boolean dungeonsdelightLeanMonsterEffectSwap = true;
    public static boolean dungeonsdelightLeanYamAura = true;
    public static boolean dungeonsdelightNoDuplicateZombieAdd = true;
    public static boolean dungeonsdelightSharedFeralBiteRandom = true;
    public static boolean dungeonsdelightSkipInvisibleRushOverlay = true;

    public static boolean chimesSkipClientPhantomScan = true;
    public static int chimesPhantomScanInterval = 4;

    public static boolean sliceanddiceSkipWetAirWithoutSprinklers = true;
    public static boolean sliceanddiceSkipClientWetAirExtinguish = true;
    public static boolean sliceanddiceWetAirExtinguishOnlyBurning = true;

    public static boolean projectiledamageSkipAppliedWeaponModifiers = true;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        define(builder);
        SPEC = builder.build();
    }

    private CoOConfig() {
    }

    @FunctionalInterface
    private interface BoolSink {
        void accept(boolean value);
    }

    private static ModConfigSpec.BooleanValue gate(ModConfigSpec.BooleanValue value, BoolSink sink) {
        BAKERS.add(() -> sink.accept(masterEnabled && value.get()));
        return value;
    }

    private static void gate(ModConfigSpec.IntValue value, int disabled, IntConsumer sink) {
        BAKERS.add(() -> sink.accept(masterEnabled ? value.get() : disabled));
    }

    private static void gate(ModConfigSpec.DoubleValue value, double disabled, DoubleConsumer sink) {
        BAKERS.add(() -> sink.accept(masterEnabled ? value.get() : disabled));
    }

    private static void plain(ModConfigSpec.IntValue value, IntConsumer sink) {
        BAKERS.add(() -> sink.accept(value.get()));
    }

    private static void plain(ModConfigSpec.DoubleValue value, DoubleConsumer sink) {
        BAKERS.add(() -> sink.accept(value.get()));
    }

    private static void define(ModConfigSpec.Builder builder) {
        builder.comment("Master switch for every patch in this mod. Useful for A/B testing.").push("general");
        ModConfigSpec.BooleanValue master = builder.define("enabled", true);
        BAKERS.add(() -> masterEnabled = master.get());
        builder.pop();

        builder.comment("EMI patches.").push("emi");
        gate(builder
                .comment("Clear EMI's recipe screen back and forward history when you leave a world. That history holds the inventory and container screens you browsed from, which hold your player and through it the whole world you just left, so the old world could stay in memory on the title screen and after joining another world. Client.")
                .define("releaseHistoryOnLeave", true), v -> emiReleaseHistoryOnLeave = v);
        builder.pop();

        builder.comment("Curios API patches.").push("curios");
        gate(builder
                .comment("Skip the per-tick curios handler for entity types that have no curio slots at all.")
                .define("skipSlotlessEntities", true), v -> curiosSkipSlotlessEntities = v);
        gate(builder
                .comment("Skip the client-side curios tick for non-player entities.")
                .define("skipClientTickOnNonPlayers", true), v -> curiosSkipClientTickOnNonPlayers = v);
        gate(builder
                .comment("Never attach the Curios render layer to non-player entity renderers.")
                .define("skipNonPlayerRenderLayer", true), v -> curiosSkipNonPlayerRenderLayer = v);
        gate(builder
                .comment("Cache the per-entity curio slot lookup on the entity itself.")
                .define("cacheEntitySlotLookup", true), v -> curiosCacheEntitySlotLookup = v);
        gate(builder
                .comment("Answer 'this entity is not wearing that item' from a per-tick set instead of a full curios inventory walk.")
                .define("fastEquippedItemMiss", true), v -> curiosFastEquippedItemMiss = v);
        gate(builder
                .comment("Answer ICuriosItemHandler#findFirstCurio(Item) from the same per-tick set instead of walking every slot handler.")
                .define("fastFindFirstMiss", true), v -> curiosFastFindFirstMiss = v);
        gate(builder
                .comment("Hand out one reusable unmodifiable view of an entity's curio slot map instead of wrapping the map again on every single call.")
                .define("reuseCurioMapView", true), v -> curiosReuseCurioMapView = v);
        gate(builder
                .comment("Stop Curios from throwing away extra slots that a different slot type is providing, and stop the per tick scan that goes with it. Slot bonuses an item grants while worn are transient, so Curios writes them into the save as cached modifiers and restores them on load so the slot count is right before the items are ticked back on. On the first tick it is supposed to drop that cache again, keeping only the entries that a worn item still vouches for, but it only ever asks the items sitting in the same slot type as the bonus. A Relics belt grants charm slots from the belt slot and a cursed ring grants a blasphemy slot from the ring slot, so their cached entries are never vouched for and never dropped: every tick Curios rebuilds the attribute map of every charm and every blasphemy looking for them, and the moment a charm that itself grants charm slots is equipped the whole charm cache is purged, the belt's bonus with it, the charm slots shrink and the charms in them fall out (Curios issue 575, the reason the pack carried the Curios pre-calculate slots mod, which re-applied every modifier of every curio every tick instead). With this on a restored cache entry is dropped the moment anyone re-applies that modifier, the first purge waits one tick so the equip pass and equip hooks have run, and what is still cached after that is checked against every worn curio and armour piece before it goes. Steady state is a handful of isEmpty checks per player per tick. Server.")
                .define("reconcileCachedSlotModifiers", true), v -> curiosReconcileCachedSlotModifiers = v);
        gate(builder
                .comment("Make the two caches Curios keys by slot name safe to touch from two threads. The slot UUID cache and the slot attribute cache are plain HashMaps filled through computeIfAbsent, and in single player the render thread fills them from tooltips and the render layer while the server thread fills them from the equip pass, which is the case where a HashMap can hand back a wrong entry or spin inside a resize. With this on both are swapped for concurrent maps when the class loads. This one is decided when the Curios classes first load, which is before configs finish loading, so turning it off only takes effect from the next game start.")
                .define("threadSafeCaches", true), v -> curiosThreadSafeCaches = v);
        gate(builder
                .comment("Answer other mods' capability questions on living entities without looking up the entity's curio slots first. Every time any mod asks any living entity for any capability, Curios is asked too, and before it even checks whether the question is about its own inventory it looks up the slot layout for that entity type. For every capability that is not the Curios inventory the answer is always nothing, whether the entity has slots or not. With this on that same nothing comes back straight away, and questions about the Curios inventory go through the normal lookup. Turn it off for stock behaviour.")
                .define("skipForeignCapabilityLookup", true), v -> curiosSkipForeignCapabilityLookup = v);
        builder.pop();

        builder.comment("Artifacts patches.").push("artifacts");
        gate(builder
                .comment("Skip the client-side Artifacts living tick for non-player entities.")
                .define("skipClientTickOnNonPlayers", true), v -> artifactsSkipClientTickOnNonPlayers = v);
        gate(builder
                .comment("Skip the kitty slippers curios scan when the entity has no last-hurt-by mob.")
                .define("fastPathKittySlippers", true), v -> artifactsFastPathKittySlippers = v);
        gate(builder
                .comment("Skip the charm of sinking curios scan when the umbrella glide check cannot pass anyway.")
                .define("fastPathUmbrella", true), v -> artifactsFastPathUmbrella = v);
        builder.pop();

        builder.comment("Caelus patches.").push("caelus");
        gate(builder
                .comment("Skip the flight attribute lookup for non-player entities that are not already fall-flying.")
                .define("skipGroundedNonPlayers", true), v -> caelusSkipGroundedNonPlayers = v);
        builder.pop();




        builder.comment("Better Combat patches.").push("bettercombat");
        gate(builder
                .comment("Cache WeaponRegistry#getAttributes per item. Stock does a registry reverse lookup plus a map get on every Player#getItemBySlot call, twice.")
                .define("cacheWeaponAttributes", true), v -> bettercombatCacheWeaponAttributes = v);
        gate(builder
                .comment("Run Better Combat's two-handed offhand check only when the offhand slot is asked for. Stock runs two weapon lookups on every Player#getItemBySlot call for every slot, and the branch flip when a weapon is held keeps the JIT deoptimising it.")
                .define("offhandOnlyTwoHandedCheck", true), v -> bettercombatOffhandOnlyTwoHandedCheck = v);
        builder.pop();

        builder.comment("Citadel patches.").push("citadel");
        gate(builder
                .comment("Draw Citadel model boxes (Alex's Mobs, Alex's Caves and every other Citadel model) through Embeddium's bulk vertex writer, the same path Embeddium gives vanilla models. Stock allocates a vector per quad and per vertex and pushes every vertex through the per-vertex BufferBuilder path, which is several times slower and much slower again with a shader pack.")
                .define("fastModelRender", true), v -> citadelFastModelRender = v);
        builder.pop();


        builder.comment("Create patches.").push("create");
        gate(builder
                .comment("Read each block position at most once per big outline pick. Stock tests a 3x3x3 neighbourhood at every raycast step, so neighbouring steps re-read the same positions.")
                .define("dedupeBigOutlineProbes", true), v -> createDedupeBigOutlineProbes = v);
        gate(builder
                .comment("Shortest gap, in milliseconds, between Create's big outline raycasts. Create casts a fresh ray from your eye to your reach limit on every frame drawn, so above a hundred frames a second it casts far more often than the outline can visibly change. Skipped frames keep the previous target, which also feeds block breaking and placing, so a value here is how stale that target may get. Vanilla only recalculates it every 50. 0 casts on every frame.")
                .defineInRange("bigOutlinePickInterval", 5, 0, 50), 0, v -> createBigOutlinePickInterval = v);
        gate(builder
                .comment("Build the collision boxes of Create contraptions and trains in one pass. Stock adds every block's shape to the running total one block at a time, and each addition rebuilds everything before it, so a few hundred blocks cost seconds of background work and a large pile of garbage every time a contraption is assembled, loaded or changed. With this on the same block shapes are gathered in the same order and merged in a single pass, giving the same list of boxes in the same order, and anything unusual, such as two edges a hair apart, falls back to Create's own code. Only applies to the Create version it was checked against. Turn it off for stock behaviour.")
                .define("singlePassContraptionCollision", true), v -> createSinglePassContraptionCollision = v);
        builder.pop();

        builder.comment("XaeroLib patches.").push("xaerolib");
        gate(builder
                .comment("Resolve the active config profile once per client tick instead of on every single config read.")
                .define("cacheConfigProfile", true), v -> xaerolibCacheConfigProfile = v);
        gate(builder
                .comment("Answer the server-enforcement check once per client tick. Stock runs a second full config read inside every config read.")
                .define("cacheEnforcementCheck", true), v -> xaerolibCacheEnforcementCheck = v);
        builder.pop();

        builder.comment("Xaero's World Map patches.").push("xaeroworldmap");
        gate(builder
                .comment("Milliseconds between the map limiter's free VRAM query, which stock fires a blocking glGetIntegerv for on every single frame. 0 restores the stock every frame behaviour. Client.")
                .defineInRange("vramPollInterval", 500, 0, 60000), 0, v -> xaeroworldmapVramPollInterval = v);
        gate(builder
                .comment("Run the map tile upload pass at most this often in milliseconds instead of once per rendered frame, so its per frame time budget stops scaling with framerate. The world map screen is never throttled. 0 disables.")
                .defineInRange("renderProcessInterval", 4, 0, 200), 0, v -> xaeroworldmapRenderProcessInterval = v);
        gate(builder
                .comment("Wait out the world map screen's frame budget by parking instead of spinning. While the map screen is open Xaero busy loops on System.nanoTime until 1.6ms of the frame has passed, which burns a whole core for most of every map frame. Pacing is unchanged, the thread just idles instead.")
                .define("idleMapFrameWait", true), v -> xaeroworldmapIdleMapFrameWait = v);
        gate(builder
                .comment("Microseconds of the world map frame budget still spent spinning, so the wait ends on time even though the operating system wakes a parked thread late. Raise it if the map screen feels choppier than before, lower it to give the core back sooner.")
                .defineInRange("mapFrameSpinTail", 200, 0, 1600), 1600, v -> xaeroworldmapMapFrameSpinTail = v);
        gate(builder
                .comment("Microseconds of upload headroom the map assumes a saturated frame still has. Stock hardcodes 3000 and spends a quarter of it uploading region textures every frame, so at 200fps the map takes 750us of a 5ms frame. The floor only applies once the frame has no time left, so lowering it changes nothing at 60fps. The world map screen always uses the stock value. 3000 restores stock.")
                .defineInRange("uploadHeadroomMicros", 1000, 100, 3000), 3000, v -> xaeroworldmapUploadHeadroomMicros = v);
        gate(builder
                .comment("Discard the old contents of a region texture's pixel buffer before mapping it for writing. Stock maps a buffer the driver may still be reading last frame's texture upload out of, which blocks the render thread until the GPU catches up.")
                .define("orphanUploadPbo", true), v -> xaeroworldmapOrphanUploadPbo = v);
        gate(builder
                .comment("Open Xaero's World Map reflected fields once instead of paying the access check on every read. Stock reads the per-chunk clean flag through an unopened Field for every chunk it writes, and toggles setAccessible on and off around every scheduled task queue read.")
                .define("openReflectedFields", true), v -> xaeroworldmapOpenReflectedFields = v);
        builder.pop();




        builder.comment("ModernFix patches.").push("modernfix");
        gate(builder
                .comment("Work out which creative tabs JEI covers by indexing its item list once, instead of asking every tab whether it holds every item. Stock compares each item against each tab, and each comparison hashes the item's NBT, so the first creative search freezes for as long as it takes. Client.")
                .define("fastRepresentedTabs", true), v -> modernfixFastRepresentedTabs = v);
        gate(builder
                .comment("Let mods walk ModernFix's list of model locations in a straight line. With ModernFix's dynamic resources on, every model bake handler is shown a set of every model location in the game, millions of entries, and mods such as Mekanism, Alex's Mobs and Alex's Caves walk all of it on every resource load. That set keeps its order as a chain of links scattered over a huge table, so each step is a cache miss. With this on the same set also records its entries in the order they went in and walks that list instead, giving the same entries in the same order. Lookups and sizes still come from the set itself, and if anything ever removes from the set it goes back to the stock walk. Client. Turn it off for stock behaviour.")
                .define("orderedModelLocations", true), v -> modernfixOrderedModelLocations = v);
        builder.pop();

        builder.comment("FancyMenu patches.").push("fancymenu");
        gate(builder
                .comment("Seconds between seamless world loading screenshots. FancyMenu reads back the whole framebuffer each time, which stalls the render thread. Stock behaviour is 1.")
                .defineInRange("seamlessCaptureInterval", 30, 1, 600), 1, v -> fancymenuSeamlessCaptureInterval = v);
        gate(builder
                .comment("Skip FancyMenu's ThreadLocal render scale write when the value is unchanged. Stock writes it on every PoseStack push, pop and scale.")
                .define("skipRedundantScaleWrites", true), v -> fancymenuSkipRedundantScaleWrites = v);
        gate(builder
                .comment("Hold FancyMenu's render scale, translation and rotation in plain fields for the render thread instead of in ThreadLocals. Stock reads or writes three ThreadLocals on every PoseStack push, pop, scale, translate and mulPose, which the profiler puts at 2.6 percent of the client thread. Other threads keep the stock ThreadLocal. Client.")
                .define("pinRenderStateToRenderThread", true), v -> fancymenuPinRenderStateToRenderThread = v);
        gate(builder
                .comment("Remember which screen identifier belongs to which screen instead of working it out again on every render call. FancyMenu asks four times a frame, and answering walks a hash map, scans its whole universal identifier table for a matching value, and calls Class.forName. The cache is dropped whenever FancyMenu reloads or a new universal identifier is registered. Client.")
                .define("cacheScreenIdentifiers", true), v -> fancymenuCacheScreenIdentifiers = v);
        gate(builder
                .comment("Remember whether a screen class is on FancyMenu's blacklist instead of running every blacklist rule against its name on each render call. Dropped whenever FancyMenu reloads or a new rule is added. Client.")
                .define("cacheScreenBlacklist", true), v -> fancymenuCacheScreenBlacklist = v);
        gate(builder
                .comment("Seconds between FancyMenu's internet availability probes, an HTTPS request to docs.fancymenu.net that the mod makes every 20 seconds. 0 keeps the stock interval. Client.")
                .defineInRange("internetProbeSeconds", 600, 0, 86400), 0, v -> fancymenuInternetProbeSeconds = v);
        gate(builder
                .comment("Remember where a local image, text or video path of a menu layout points once it has been checked, instead of asking the disk for its real path again every time a menu opens. Opening the pause menu resolved every local file of its layout twice through the file system, which froze the game for up to 70 ms. File actions such as copy and delete still check the disk right before they run. Client.")
                .define("memoLocalSources", true), v -> fancymenuMemoLocalSources = v);
        gate(builder
                .comment("Only track the pose stack for FancyMenu while a screen is open.")
                .define("trackPoseStackOnlyInScreens", true), v -> fancymenuTrackPoseStackOnlyInScreens = v);
        builder.pop();

        builder.comment("Entity Model Features patches.").push("emf");
        gate(builder
                .comment("Stop the anger time map growing one entry per neutral mob ever rendered.")
                .define("dropZeroAngerEntries", true), v -> emfDropZeroAngerEntries = v);
        gate(builder
                .comment("Remember each nbt() animation result per entity for one client tick instead of serialising the whole entity to NBT and regex matching it on every frame. Equipment or inventory changes show up at most one tick later. Client.")
                .define("cacheNbtPerTick", true), v -> emfCacheNbtPerTick = v);
        gate(builder
                .comment("Cache block entity type names instead of looking them up on every frame.")
                .define("cacheBlockEntityTypeName", true), v -> emfCacheBlockEntityTypeName = v);
        gate(builder
                .comment("Stop the animation expression parser throwing a stack trace for every variable name it reads. Each piece of an animation expression in a model file, a bone angle formula, a condition, a variable, is first handed to the number parser, and anything that is not a number, which is nearly every name like head_rx or is_hurt, makes it throw a NumberFormatException that the parser catches to learn that the piece is a variable. A model pack with a few hundred animated models builds hundreds of thousands of those at launch, each one copying the loading thread's deep call stack. With this on a piece is only handed to the real number parser when its first character, after any spaces and sign, could start a number at all, which are a digit, a dot, or the N and I of NaN and Infinity, and everything else gets one shared exception with no stack that the same catch block handles in the same way. Every number the real parser accepts, including 1f, .5, hex floats, NaN and Infinity, still goes through it unchanged. Client.")
                .define("parseTokensWithoutExceptions", true), v -> emfParseTokensWithoutExceptions = v);
        builder.pop();




        builder.comment("Lootr patches.").push("lootr");
        gate(builder
                .comment("Skip Lootr's per-server-tick container conversion pass while its queues are empty.")
                .define("skipIdleTileTicker", true), v -> lootrSkipIdleTileTicker = v);
        gate(builder
                .comment("Maximum container conversion candidates Lootr may examine per server tick.")
                .defineInRange("tileTickerBudget", 512, 0, 65536), 0, v -> lootrTileTickerBudget = v);
        gate(builder
                .comment("Run Lootr's container conversion pass every this many server ticks instead of every tick. Each pass rechecks every waiting chest against the 25 chunks around it, and chests at the edge of a large view distance wait there for minutes. New loot chests still turn into Lootr chests, at most this many ticks later. 1 is stock behaviour.")
                .defineInRange("tileTickerInterval", 5, 1, 100), 1, v -> lootrTileTickerInterval = v);
        builder.pop();


        builder.comment("Xaero's Minimap patches.").push("xaerominimap");
        gate(builder
                .comment("Maximum times per second the minimap redraws its map contents. Anything below the client frame rate leaves a visibly stale minimap, so this is off by default. 0 restores the stock every frame behaviour. Client.")
                .defineInRange("renderFpsCap", 0, 0, 260), 0, v -> xaeroMinimapRenderFpsCap = v);
        builder.pop();

        builder.comment("Xaero's + Waystones compatibility patches.").push("w2w2");
        gate(builder
                .comment("Collapse the waypoint file writes this mod does when waystone data arrives.")
                .define("deferWaypointSave", true), v -> w2w2DeferWaypointSave = v);
        builder.pop();

        builder.comment("Oh The Biomes We've Gone patches.").push("biomeswevegone");
        gate(builder
                .comment("Skip the Crag Gardens and Basalt Barrera terrain passes in chunks where neither biome can show up. Both passes run on every chunk generated in every dimension and build four noise generators, two weighted state providers and 512 biome lookups before they ever check whether the biome is present. The biome lookup they use blends in a little of the neighbouring chunks near the edges, so a pass is only skipped when neither the chunk itself nor any of the eight chunks around it has the biome, and it always runs when a neighbour cannot be checked, so cliffs and hexagons along a biome border come out exactly as before.")
                .define("skipForeignChunkTerrain", true), v -> biomeswevegoneSkipForeignChunkTerrain = v);
        builder.pop();

        builder.comment("Biolith patches.").push("biolith");
        gate(builder
                .comment("Re-tag the biome source with its dimension after another mod swaps it. MCreator biome mods replace the overworld MultiNoiseBiomeSource on ServerAboutToStart, and the replacement never passes through LevelStem, so Biolith loses track of which dimension it is and silently stops applying every biome replacement and sub-biome it was asked for.")
                .define("restampSwappedBiomeSource", true), v -> biolithRestampSwappedBiomeSource = v);
        gate(builder
                .comment("Hand Biolith the registries at the start of WorldStem instead of the end. BCLib asks every vanilla biome source for its biome list from inside that constructor, which is before Biolith has a registry to look biomes up in, and Biolith throws.")
                .define("earlyRegistryCapture", true), v -> biolithEarlyRegistryCapture = v);
        gate(builder
                .comment("Return Biolith's cached biome list without entering its lock. Biolith synchronizes on the biome source on every single biome lookup, and world generation runs those lookups on several threads at once.")
                .define("reuseBiomeEntries", true), v -> biolithReuseBiomeEntries = v);
        builder.pop();

        builder.comment("TerraBlender patches.").push("terrablender");
        gate(builder
                .comment("Cache TerraBlender's per-block namespace lookup for the duration of a biome.")
                .define("cacheNamespaceRule", true), v -> terrablenderCacheNamespaceRule = v);
        gate(builder
                .comment("Give every chunk generation worker its own copy of TerraBlender's region noise cache instead of one cache behind a lock. The lock is taken on every biome region lookup during world generation and sixteen workers fight over it; the cached function is pure, so per thread copies return the same values.")
                .define("threadLocalAreaCache", true), v -> terrablenderThreadLocalAreaCache = v);
        gate(builder
                .comment("Build a TerraBlender namespace's surface rules only when a chunk first needs them. For every chunk's surface pass TerraBlender builds the full surface rule tree of every mod that registered surface rules, hundreds of objects each, although a chunk usually only ever reaches one or two of them. With this on, a namespace whose rules are made only of vanilla rule types is built the first time a block in that chunk actually asks for it, with the very same call. Vanilla's rule conditions work out their answer from the chunk's current position whenever they are first asked, so building them later gives the same blocks. A namespace containing any other mod's rule types is built up front as before, and if any other mod changes the vanilla rule classes this patch has not checked, everything is built up front and the log says why once. Turn it off for stock behaviour.")
                .define("lazyNamespaceRules", true), v -> terrablenderLazyNamespaceRules = v);
        gate(builder
                .comment("Drop repeated layers of TerraBlender's overworld and nether surface rules. TerraBlender writes its wrapped surface rules into level.dat and wraps them again on the next load, so every time a world is opened one more full copy of every mod's surface rules is stacked under the last. Below the surface each copy is walked in turn and gives the same answer as the first, so a world opened fifteen times runs the rules fifteen times per block and level.dat keeps growing. With this on, a layer whose rules are identical to a layer above it is skipped; layers that differ, such as rules saved by an older version of a mod, are kept in order, so the generated blocks are the same. Turn it off for stock behaviour.")
                .define("dedupeStackedLayers", true), v -> terrablenderDedupeStackedLayers = v);
        builder.pop();



        builder.comment("Born in Chaos patches.").push("borninchaos");
        gate(builder
                .comment("Bail out of Born in Chaos' held item animation handler before it copies your hands.")
                .define("skipItemAnimationCopies", true), v -> borninchaosSkipItemAnimationCopies = v);
        gate(builder
                .comment("Skip Born in Chaos' entity animation handler for entities that are not its own.")
                .define("skipForeignEntityAnimations", true), v -> borninchaosSkipForeignEntityAnimations = v);
        gate(builder
                .comment("Hand Born in Chaos' mob models the same resource location every time they ask for their model, animation or texture file instead of building and validating a new one on every frame for every mob. Client.")
                .define("reuseModelLocations", true), v -> borninchaosReuseModelLocations = v);
        gate(builder
                .comment("Stop 83 Born in Chaos mobs from resizing themselves once per tick for no reason.")
                .define("skipRedundantDimensionRefresh", true), v -> borninchaosSkipRedundantDimensionRefresh = v);
        gate(builder
                .comment("Narrow the four Born in Chaos minion claim scans to the mod's own entities.")
                .define("narrowMinionScans", true), v -> borninchaosNarrowMinionScans = v);
        builder.pop();







        builder.comment("Cucumber Library patches.").push("cucumber");
        gate(builder
                .comment("Look for a player within sixty four blocks before serialising a block entity, not after. Cucumber's dispatch helper writes the whole tile, inventory NBT and all, into a fresh update packet and only then asks whether anyone is close enough to receive it, and every Mystical Agriculture machine calls it at the end of every tick it changed, which for a running machine is every tick. Also swaps the Math.hypot distance test for the squared distance. Result is identical.")
                .define("leanTileDispatch", true), v -> cucumberLeanTileDispatch = v);
        gate(builder
                .comment("Ask whether an item has any tags instead of building the full lists, when you are not holding CTRL. With advanced tooltips on, stock collects every block tag, every item tag and every fluid tag, running a fluid handler capability lookup and a sorted distinct pass over them, once per hovered item per frame, and then throws all of it away to print 'Hold CTRL for tags'. Result is identical.")
                .define("leanTagTooltip", true), v -> cucumberLeanTagTooltip = v);
        gate(builder
                .comment("Remember which item a tag resolved to instead of re-reading config/cucumber-tags.json off disk. Cucumber only caches a tag once the file has been parsed successfully, so every tag it has not seen yet costs a file open plus a full Gson parse, and a malformed cucumber-tags.json makes that miss permanent: every ShapedTagRecipe in the recipe sync packet re-reads the broken file and logs a full stack trace, on the netty thread, once per player join. The memo is dropped whenever Cucumber rebuilds its own tag map, so it lives exactly as long as stock's. Result is identical.")
                .define("cacheTagLookup", true), v -> cucumberCacheTagLookup = v);
        builder.pop();
















        builder.comment("Patchouli patches.").push("patchouli");
        gate(builder
                .comment("Answer 'is this item one of the guide books' from a per item cache.")
                .define("cacheBookItemLookup", true), v -> patchouliCacheBookItemLookup = v);
        builder.pop();







        builder.comment("FTB Chunks patches.").push("ftbchunks");
        gate(builder
                .comment("Stop building the minimap texture when the minimap is not being shown.")
                .define("skipHiddenMinimapWork", true), v -> ftbchunksSkipHiddenMinimapWork = v);
        gate(builder
                .comment("Copy a map region's five images straight into their pixel arrays instead of making 1.3 million per pixel setRGB calls on the calling thread.")
                .define("fastRegionWrite", true), v -> ftbchunksFastRegionWrite = v);
        gate(builder
                .comment("Reuse the region and its image across the 225 chunk tiles of one minimap rebuild instead of taking the shared map lock for every tile.")
                .define("memoMinimapRegions", true), v -> ftbchunksMemoMinimapRegions = v);
        gate(builder
                .comment("Send the minimap to the GPU in at most four pieces instead of 225 tiny ones, and skip tiles whose picture has not changed. Every time you cross a chunk border or a map region finishes redrawing, the minimap is rebuilt by uploading its 15 by 15 chunk tiles one 16 pixel square at a time, about seven GL calls each, even though neighbouring tiles come from the same region picture and most of them did not change at all. With this on, each run of tiles that comes from one region picture goes up as a single rectangle, and a tile is skipped when the texture already holds exactly that piece of that picture and the region has not been redrawn since. While a region is still being drawn in the background every rebuild uploads it like before, so the minimap and the chunk claim screen look exactly the same, they just cost less. Turn it off for stock behaviour.")
                .define("coalesceMinimapUploads", true), v -> ftbchunksCoalesceMinimapUploads = v);
        gate(builder
                .comment("Stop FTB Chunks recording map data entirely. No chunk is scanned when it arrives from the server, no block change is re-rendered, and nothing new is written to disk. Turn this on only if another map mod is doing the job. Already recorded regions still load and display.")
                .define("disableMapWriting", false), v -> ftbchunksDisableMapWriting = v);
        gate(builder
                .comment("Stop the FTB XMod Compat integration from putting Waystones icons on the FTB minimap and large map.")
                .define("disableWaystoneIcons", false), v -> ftbchunksDisableWaystoneIcons = v);
        builder.pop();





        builder.comment("Regions Unexplored patches.").push("regionsunexplored");
        gate(builder
                .comment("Remember what the furnace fuel handler decided for an item instead of walking its 1100 branch block comparison chain again.")
                .define("cacheFurnaceBurnTimes", true), v -> regionsunexploredCacheFurnaceBurnTimes = v);
        builder.pop();


        builder.comment("Cable Facades patches.").push("cablefacades");
        gate(builder
                .comment("Answer a facade lookup with a single flag read while no facade has been placed in the loaded world. Cable Facades queries this map from seven hooks in the hottest vanilla block methods, so it runs for every block of every chunk rebuild and every light update. Client.")
                .define("skipEmptyLookup", true), v -> cablefacadesSkipEmptyLookup = v);
        gate(builder
                .comment("Skip Cable Facades' recursion guard when no facade can exist. Cable Facades hooks six of the hottest block state methods, the shape, collision shape, occlusion shape, light block, skylight and solid render checks, and every call first reads and writes a thread local recursion guard before it even looks for a facade. Those methods run for every block of every chunk mesh and every light update, on every meshing thread. On the client, when no facade has ever been placed or received, the facade lookup can only come back empty, so with this on the guard reads as already held and the hook returns straight away, which is the same result the full path gives. Server worlds keep the full path, and the moment the client sees any facade every hook goes back to the full path too.")
                .define("skipGuardWhenEmpty", true), v -> cablefacadesSkipGuardWhenEmpty = v);
        builder.pop();





        builder.comment("AllTheLeaks patches.").push("alltheleaks");
        gate(builder
                .comment("Skip the full garbage collection AllTheLeaks forces on every second crash report. Mods that catch errors and keep the game running write crash reports during play, and each forced collection froze the game for over half a second. The leak list in the report may then count objects that were about to be collected.")
                .define("skipCrashReportGc", true), v -> alltheleaksSkipCrashReportGc = v);
        builder.pop();

        builder.comment("L2 Hostility patches.").push("l2hostility");
        gate(builder
                .comment("Stop re-asking entities that cannot have traits for their trait capability.")
                .define("skipTraitlessCapLookup", true), v -> l2hostilitySkipTraitlessCapLookup = v);
        gate(builder
                .comment("Make L2 Hostility's curio item check do only the work its answer needs. Every Fallen Traits relic hook, the trait items and many L2 items ask whether an entity wears a given curio, often around ten times per combat hit. Each ask resolved the curio inventory three times, ran a full first match search whose result was thrown away, and built a new tag key for every slot type it walked. With this on the inventory is resolved once, the unused search is skipped and each slot type's tag key is built once and reused. The answer is the same. Turn it off for stock behaviour.")
                .define("leanCurioItemCheck", true), v -> l2hostilityLeanCurioItemCheck = v);
        gate(builder
                .comment("Ray trace the player's line of sight to a mob once per game tick for L2Hostility's level and trait name plate, instead of once per mob per frame.")
                .define("namePlateSightPerTick", true), v -> l2hostilityNamePlateSightPerTick = v);
        builder.pop();










        builder.comment("Elysium API patches.").push("elysiumapi");
        gate(builder
                .comment("Answer a repeated climate sample for the same position from a one entry cache.")
                .define("memoClimateSample", true), v -> elysiumapiMemoClimateSample = v);
        gate(builder
                .comment("Remember which Elysium sound transformer applies to each block. Elysium searched its whole transformer registry with a fresh stream every time any block was asked for its sound, which happens constantly while chunks are meshed and entities walk. The answer is worked out again after a world change or a tag reload.")
                .define("cacheSoundTransformers", true), v -> elysiumapiCacheSoundTransformers = v);
        builder.pop();

        builder.comment("Balm patches.").push("balm");
        gate(builder
                .comment("Stop rebuilding a block state's toString on every quad request.")
                .define("memoDynamicModelKeys", true), v -> balmMemoDynamicModelKeys = v);
        gate(builder
                .comment("Remember which config file belongs to each Balm config class instead of working it out again on every read. Every Balm mod reads its settings through getActive, and each of those reads asks the JVM for the class annotation, builds a brand new ResourceLocation out of the same two strings, validating both character by character, and allocates a lambda before it even looks in the map that already holds the answer. Waystones does that twice per waystone per frame from its block renderer and Cooking for Blockheads does it up to a dozen times per oven per tick, so it adds up across a pack. With this on the identifier is worked out once per class and the config object is looked up straight out of Balm's own map, and only if it is missing there does the stock path run. Turn it off for stock behaviour.")
                .define("cacheConfigIdentifier", true), v -> balmCacheConfigIdentifier = v);
        builder.pop();














        builder.comment("Zeta patches. Zeta is Quark's module and event framework.").push("zeta");
        gate(builder
                .comment("Stop allocating an iterator for every block every structure places.")
                .define("leanStructureReplacement", true), v -> zetaLeanStructureReplacement = v);
        gate(builder
                .comment("Build one Zeta event wrapper per dispatch instead of one per listener.")
                .define("shareEventWrappers", true), v -> zetaShareEventWrappers = v);
        builder.pop();

        builder.comment("Dungeon Crawl patches.").push("dungeoncrawl");
        gate(builder
                .comment("Stop asking for a block entity after placing a block that cannot have one.")
                .define("skipBlockEntityProbe", true), v -> dungeoncrawlSkipBlockEntityProbe = v);
        builder.pop();


        builder.comment("L_Ender's Cataclysm patches.").push("cataclysm");
        gate(builder
                .comment("Run the camera shake entity scan once per tick instead of once per frame. Client.")
                .define("cacheShakeScan", true), v -> cataclysmCacheShakeScan = v);
        gate(builder
                .comment("Skip the keyframe pass of Ignis, the Leviathan, the Ancient Remnant, the Ender Guardian and the Amethyst Crab while they are not attacking. Their animation code is so large that Java never compiles it, so every frame it walks through hundreds of attack keyframes one by one just to find out that none of them is playing. With this on, when the boss is between attacks it still resets its pose exactly as before and then stops, because every one of those keyframe steps does nothing without an attack playing. Idle movement, head turning and breathing are done elsewhere and are not touched. Client. Turn it off for stock behaviour.")
                .define("skipIdleAnimate", true), v -> cataclysmSkipIdleAnimate = v);
        gate(builder
                .comment("Stop the Ignis and Ignited Revenant shield layers, and the Harbinger's powered shield, working out the whole boss pose a second time each frame. Ignis and the Revenant keep a second copy of their model just for the shield and ran the full animation on it again with the same inputs, and the Harbinger re-ran its animation on its own model. With this on the shield copy takes the pose the main model already worked out, and the Harbinger keeps the pose it already has, so the shields sit and move exactly as before. Client. Turn it off for stock behaviour.")
                .define("reuseLayerPose", true), v -> cataclysmReuseLayerPose = v);
        gate(builder
                .comment("Reset the pose of Scylla, the Maledictus, the Netherite Monstrosity, the Aptrgangr, the Draugrs and the other newer Cataclysm models with a plain loop. Each of them rebuilt a chain of Java streams over every part of the model, every frame, only to put each part back to its starting pose. With this on the same parts are reset the same way by walking the model directly. Client. Turn it off for stock behaviour.")
                .define("leanPoseReset", true), v -> cataclysmLeanPoseReset = v);
        gate(builder
                .comment("Stop the Maledictus' hand circles creating their texture name twice every frame. With this on one shared name is used, which finds the same render type. Client. Turn it off for stock behaviour.")
                .define("cacheCicleTexture", true), v -> cataclysmCacheCicleTexture = v);
        gate(builder
                .comment("Look up the light under the Leviathan's tongue and the Tidal Claws tentacle once per block instead of once per segment. Both are drawn as up to 128 short segments, each reading the light of the block it is in, and most of them sit in the same few blocks. With this on a block's light is read once per draw and reused for the other segments in it, so the tongue and tentacle are lit exactly the same. Client. Turn it off for stock behaviour.")
                .define("cacheTentacleLight", true), v -> cataclysmCacheTentacleLight = v);
        gate(builder
                .comment("Fix a memory leak in Cataclysm's lightning effects. The Boltstrike, the death laser beam, the Maledictus hand lightning and Scylla's anchor and eye sparks remember a lightning effect for every entity they ever drew, and that memory holds on to the entity itself, so every bolt and beam from every fight stays in memory, and after you change worlds it keeps the whole old world loaded too. With this on, before drawing, the effects of entities that are gone or in another world are forgotten. Nothing that is still on screen changes. The one difference is a boss or bolt that leaves your view distance and comes back: stock would also keep drawing the leftover lightning of its earlier visit next to the new one. Client. Turn it off for stock behaviour.")
                .define("pruneLightningRenders", true), v -> cataclysmPruneLightningRenders = v);
        gate(builder
                .comment("Only search for victims on the ticks where Cataclysm's area attacks can actually hurt something. The Sandstorm, Ignis's Flame Strike, the Ashen Breath, the Earthquake shockwave, the Phantom Halberd and the Void Rune each search the area around them every tick (the Sandstorm and Flame Strike on your client too), then skip every mob they found unless it is one of their damage ticks, which is every 3rd, 5th or 10th tick. With this on the search is skipped on the ticks where nothing could be hit, so they hit the same mobs on the same ticks for the same damage. Both sides. Turn it off for stock behaviour.")
                .define("scanOnlyOnDamageTicks", true), v -> cataclysmScanOnlyOnDamageTicks = v);
        gate(builder
                .comment("Stop the Harbinger's and Prowler's death laser and the baby Leviathan's mini abyss blast searching for mobs along the beam on your client. Every tick the beam is on, your client did the same mob search as the server and threw the list away, because only the server deals the damage. With this on your client still traces the beam against blocks, so the beam and its impact sparks look exactly the same. Client. Turn it off for stock behaviour.")
                .define("skipClientBeamEntityScan", true), v -> cataclysmSkipClientBeamEntityScan = v);
        gate(builder
                .comment("Stop the death laser reading 216 blocks around its impact point every tick looking for an EMP block when there is none nearby. With this on it first checks the list of block types each 16x16x16 piece of the world holds, and only reads the blocks one by one when an EMP block could be there, so EMPs are switched off exactly as before. Server. Turn it off for stock behaviour.")
                .define("skipEmpScanWithoutEmp", true), v -> cataclysmSkipEmpScanWithoutEmp = v);
        gate(builder
                .comment("Make the Tidal Claws tentacle compare distances before tracing a line of sight when it picks its next target to chain to. It traced a line to every candidate, even ones further away than the best target already found, which can never be picked. With this on those are skipped without the trace, so the tentacle picks the same target. Server. Turn it off for stock behaviour.")
                .define("tentacleDistanceBeforeSight", true), v -> cataclysmTentacleDistanceBeforeSight = v);
        gate(builder
                .comment("Stop every Wither Homing Missile sending an update packet to every nearby player every tick. The missile counts down its fuse in data that is synced to clients, so each tick of the countdown went out over the network, but nothing on the client ever looks at the fuse. With this on the server keeps counting down the same way and saves it the same way, it just does not send it. Server. Turn it off for stock behaviour.")
                .define("unsyncedMissileFuse", true), v -> cataclysmUnsyncedMissileFuse = v);
        gate(builder
                .comment("Fix a small memory leak from the Ancient Remnant's sandstorms. Your client keeps each sandstorm's wind sound in a list and only removes it when the storm runs out of time by itself, so a storm that vanishes early (the boss died, or you walked away) stayed in that list along with the storm and the whole world it was in, even after you left that world. With this on the entry is removed when the storm is removed. The sound already stops at that moment, so nothing you hear changes. Client. Turn it off for stock behaviour.")
                .define("sandstormSoundCleanup", true), v -> cataclysmSandstormSoundCleanup = v);
        gate(builder
                .comment("Stop every living thing in the game paying for Cataclysm's ability checks every tick. Cataclysm runs a tick handler on every mob, animal, villager and player, on both the server and your client, that looks up three of its abilities (the Tidal Claws hook, the shield charge and the Soul Render rush) and asks for each one twice, which walks the entity's whole list of attached mod data six times per entity per tick, even though those abilities are idle more than 99.9% of the time. With this on each entity remembers where its Cataclysm data lives the first time it is asked for, and every later lookup is a single field read. The same data object comes back, so every ability behaves exactly as before, and dead or removed entities still get nothing. Turn it off for stock behaviour.")
                .define("cacheCapabilities", true), v -> cataclysmCacheCapabilities = v);
        gate(builder
                .comment("Stop Cataclysm building a whole new item renderer every time one of its 3D items is drawn. Shields, gauntlets, spears, bows, altars and the other modelled items each made a fresh renderer per draw, which creates seven block entities (firing seven Forge capability events), re-bakes three skull models and builds eight texture names, every frame, for your hotbar, your hand, other players' hands, item frames, dropped items and every slot of an open inventory or JEI. With this on one renderer is made and reused, and it re-bakes its skulls when resource packs reload just like before, so the items look exactly the same. Client.")
                .define("shareItemRenderer", true), v -> cataclysmShareItemRenderer = v);
        gate(builder
                .comment("Stop every Cataclysm boss sending a stop the music packet to everyone near it twenty times a second. Each boss with a theme sends either play or stop to all players within tracking range every single tick, even while it is asleep in its arena, so an idle boss floods nearby clients with stop messages that do nothing after the first one. With this on the stop message is sent once each time the music should end and repeats are skipped, which changes nothing a client hears because a client only reacts to stop for the boss it is currently playing, and only a play message can make a boss that. Play messages still go out every tick during the fight exactly as before. Server.")
                .define("bossMusicOnChange", true), v -> cataclysmBossMusicOnChange = v);
        gate(builder
                .comment("Make Cataclysm's no ores, lakes, geodes, lichen, magma or basalt inside my structures check cheap when there is no Cataclysm structure nearby. Cataclysm checks every ore vein, glow lichen, sculk vein, lake, geode and delta that world generation places, hundreds per chunk, and every check built throwaway lists for each of its structures even though the chunk almost never has a reference to them. With this on a chunk with no reference to the structure answers no structure straight away, which is the answer the full check gives, and chunks that do have one go through the original check. World generation.")
                .define("fastStructureCheck", true), v -> cataclysmFastStructureCheck = v);
        gate(builder
                .comment("Let vanilla reject a basalt column spot before Cataclysm's structure check runs. In basalt deltas the column feature probes thousands of spots per chunk, and Cataclysm checks for its Nether structures on every probe before vanilla's own one block test, even though its check can only ever turn the answer into no. With this on vanilla's test runs first and a spot it rejects is rejected right away. Spots vanilla accepts still go through Cataclysm's check, so columns generate exactly as before. World generation.")
                .define("basaltPrecheck", true), v -> cataclysmBasaltPrecheck = v);
        gate(builder
                .comment("Stop Cataclysm firing an arm pose event twice per frame for every player, zombie, skeleton and other humanoid on screen. The event exists so that holding two Annihilators or two Immolators, or two Obliterators from Travel Optics, can raise both arms while using them, and every listener in the pack only acts when the entity is using an item with something in its offhand. With this on the event is only built and sent in that case, so those poses look exactly the same. Client.")
                .define("skipIdleArmPoseEvent", true), v -> cataclysmSkipIdleArmPoseEvent = v);
        gate(builder
                .comment("Stop an exception being thrown and caught every time any living entity is loaded. Cataclysm's Tidal Claws data reads a saved tentacle id that is almost never there, and reading a missing id throws an error that is then quietly swallowed, on every chunk load, login and dimension change for every mob in it. With this on the read is skipped when the id is not saved, and everything else loads exactly as before. Turn it off for stock behaviour.")
                .define("quietTentacleLoad", true), v -> cataclysmQuietTentacleLoad = v);
        gate(builder
                .comment("Only search for nearby mobs when the Monstrous Helm can actually knock them back. While you wear the helm it searched a box around you every tick and then threw the result away unless you were at half health or less and the ability was off cooldown. With this on the search only happens in that case, so the knockback hits exactly the same mobs. Both sides.")
                .define("lazyMonstrousHelmScan", true), v -> cataclysmLazyMonstrousHelmScan = v);
        gate(builder
                .comment("Stop Deeplings re-checking their swimming size every tick when nothing changed. Every Deepling, Angler, Brute, Priest and Warlock tested a collision box around itself and then rebuilt its hitbox and posted a size event every single tick, on both sides, even while it stayed swimming or stayed walking. With this on that work only happens when the swim state still has to flip or the stored size no longer matches, so Deeplings switch between swimming and walking at exactly the same moments. Both sides. Turn it off for stock behaviour.")
                .define("leanDeeplingSwimRefresh", true), v -> cataclysmLeanDeeplingSwimRefresh = v);
        gate(builder
                .comment("Let Deeplings skip their search for a Coralssus to ride when they cannot ride anyway. Each Deepling's ride goal searched a 30 block box for Coralssus several times a second before checking that it was not already riding and not drying out, which is almost always the case. With this on those two checks come first, so they mount exactly the same Coralssus. Server. Turn it off for stock behaviour.")
                .define("deeplingRideCheckFirst", true), v -> cataclysmDeeplingRideCheckFirst = v);
        gate(builder
                .comment("Let the Coral Golem and Coralssus skip a collision test whose answer cannot matter. Every tick they tested a collision box and only then looked at whether they were already in the swim state they were about to set. With this on the swim state is looked at first, so they switch between swimming and walking at exactly the same moments. Both sides. Turn it off for stock behaviour.")
                .define("swimCheckFirst", true), v -> cataclysmSwimCheckFirst = v);
        gate(builder
                .comment("Make the Netherite Monstrosity's walking block breaking cheaper. While it walks, both the current and the old Monstrosity scan every block around their body every tick it is allowed to break things, creating new positions and looking up a block entity at every spot, even air. With this on the scan reuses one position and only looks up a block entity where the original actually uses it, so the same blocks break and drop the same way. Server. Turn it off for stock behaviour.")
                .define("leanMonstrosityBlockBreaking", true), v -> cataclysmLeanMonstrosityBlockBreaking = v);
        gate(builder
                .comment("Make the Leviathan's Dimensional Rift block breaking much cheaper. A rift scans a 31 by 31 by 31 cube, almost 30000 blocks, every tick for its whole 15 second life, reading two blocks, a block entity and creating two positions at every spot. With this on empty air chunk sections are skipped outright, each block is read once instead of twice, and the block entity is only looked up where it decides anything, so exactly the same blocks are torn out in the same order with the same random rolls. Server. Turn it off for stock behaviour.")
                .define("leanRiftBlockBreaking", true), v -> cataclysmLeanRiftBlockBreaking = v);
        gate(builder
                .comment("Stop the Ancient Remnant and the Clawdian working out where their feet should land on the server. Their leg solver probes the blocks under each leg every tick so the model can plant its feet, and it ran on the server too, where nothing ever reads the result. With this on it only runs on the client, so the feet look exactly the same. Server. Turn it off for stock behaviour.")
                .define("skipServerLegSolver", true), v -> cataclysmSkipServerLegSolver = v);
        gate(builder
                .comment("Remember which dimension a Cataclysm boss calls home. Every tick an idle boss checks whether it should walk home, and every time it re-parses its saved dimension name and looks the dimension key up again. With this on the parsed name is kept until the saved name changes, so bosses return home exactly as before. Server. Turn it off for stock behaviour.")
                .define("cacheHomeDimension", true), v -> cataclysmCacheHomeDimension = v);
        builder.pop();


        builder.comment("EEEAB's Mobs patches.").push("eeeabsmobs");
        gate(builder
                .comment("Run the camera shake entity scan once per tick instead of once per frame. Client.")
                .define("cacheShakeScan", true), v -> eeeabsmobsCacheShakeScan = v);
        builder.pop();




        builder.comment("Legendary Monsters patches.").push("legendarymonsters");
        gate(builder
                .comment("Run the camera shake and dynamic zoom entity scans once per tick instead of once per frame. Client.")
                .define("cacheShakeScan", true), v -> legendarymonstersCacheShakeScan = v);
        gate(builder
                .comment("Ticks between boss music state broadcasts. Every loaded boss with music sends a packet to every player on the server on every tick, so 20 packets per second per boss per player even when nothing changed. A state change is still sent immediately; this only limits the repeats that let players who moved in or out of hearing range resync. 1 restores the stock every tick behaviour.")
                .defineInRange("bossMusicInterval", 10, 1, 200), 1, v -> legendarymonstersBossMusicInterval = v);
        gate(builder
                .comment("Skip the dynamic camera zoom listener. It scans for zoom entities every frame and then throws away the only value it computes, so the eight view clip raycasts behind that value change nothing. Client.")
                .define("skipDeadZoomWork", true), v -> legendarymonstersSkipDeadZoomWork = v);
        gate(builder
                .comment("Skip the camera shake listener when there is no client player instead of reading its tick count first, which is a null dereference placed above the mod's own null check. Client.")
                .define("guardCameraNullPlayer", true), v -> legendarymonstersGuardCameraNullPlayer = v);
        gate(builder
                .comment("Fix the Annihilator Leggings shockwave roll. The mod multiplies a full range random int by 100 and compares it to 25, which overflows and fires about half the time instead of the intended quarter.")
                .define("fixAnnihilatorProcChance", true), v -> legendarymonstersFixAnnihilatorProcChance = v);
        builder.pop();


        builder.comment("AmbientSounds patches.").push("ambientsounds");
        gate(builder
                .comment("Work out whether a biome matches a region's biome patterns once instead of running the regex every client tick. Client.")
                .define("memoBiomeMatch", true), v -> ambientsoundsMemoBiomeMatch = v);
        builder.pop();


        builder.comment("Ars Nouveau patches.").push("arsnouveau");
        gate(builder
                .comment("Ticks between refreshes of the offscreen sky texture, which costs a second full sky, cloud and weather pass plus a fog event post every frame. 1 refreshes once a tick, 0 restores the stock every frame behaviour. Client.")
                .defineInRange("skyTextureInterval", 1, 0, 200), 0, v -> arsnouveauSkyTextureInterval = v);
        builder.pop();

        builder.comment("Ars Elemental patches.").push("arselemental");
        gate(builder
                .comment("Only give the 50 percent healing bonus to a player actually holding an earth focus. Stock code reads the focus toggle with an or instead of an and, so while \"Enable glyph empowering\" is on every heal of every living entity in the world is multiplied by 1.5.")
                .define("fixGlobalHealBoost", true), v -> arselementalFixGlobalHealBoost = v);
        gate(builder
                .comment("Skip the per-attack bangle curio walk when the cached curio set already proves neither side is wearing a school bangle.")
                .define("skipBangleScan", true), v -> arselementalSkipBangleScan = v);
        gate(builder
                .comment("Build the throwaway empty curio result only when the curio inventory is actually missing, instead of on every focus and bangle lookup.")
                .define("leanCurioFallback", true), v -> arselementalLeanCurioFallback = v);
        gate(builder
                .comment("Check for sonic boom damage before reading the mirror enchantment level and rolling for a reflect, instead of after.")
                .define("skipNonSonicMirrorRoll", true), v -> arselementalSkipNonSonicMirrorRoll = v);
        gate(builder
                .comment("Make the curio pouch keybind work. Ars Elemental registers the key but its handler is a non static method on the mod event bus, so the game never calls it. Client.")
                .define("fixCurioBagKeybind", true), v -> arselementalFixCurioBagKeybind = v);
        builder.pop();




        builder.comment("Alex's Mobs patches.").push("alexsmobs");
        gate(builder
                .comment("Stop attaching the snow leopard and tiger avoidance goals to every creeper.")
                .define("skipCreeperAvoidGoals", true), v -> alexsmobsSkipCreeperAvoidGoals = v);
        gate(builder
                .comment("How often a vanilla spider may scan for Alex's Mobs flies, in goal evaluations.")
                .defineInRange("spiderFlyScanInterval", 10, 1, 200), 1, v -> alexsmobsSpiderFlyScanInterval = v);
        gate(builder
                .comment("Release the level keyed maps Alex's Mobs never clears, when a server stops.")
                .define("releaseLevelMaps", true), v -> alexsmobsReleaseLevelMaps = v);
        gate(builder
                .comment("Skip the crow crop-circling and pumpkin-avoid block scans (up to 67k block reads each) when no chunk section in range can hold a matching block.")
                .define("crowScanPrecheck", true), v -> alexsmobsCrowScanPrecheck = v);
        builder.pop();

        builder.comment("Alex's Caves patches.").push("alexscaves");
        gate(builder
                .comment("Stop recomputing Alex's Caves' cave biome placement 96 times per column.")
                .define("memoRareBiomeQuads", true), v -> alexscavesMemoRareBiomeQuads = v);
        gate(builder
                .comment("Answer Alex's Caves' duplicate climate sample from a one entry cache.")
                .define("memoClimateSample", true), v -> alexscavesMemoClimateSample = v);
        gate(builder
                .comment("Look for screen shaking mobs once per tick instead of once per frame.")
                .define("cacheShakeScan", true), v -> alexscavesCacheShakeScan = v);
        gate(builder
                .comment("Find nearby attracting and repelling magnets with one point of interest search instead of two for every magnetic entity on every tick. Server.")
                .define("singleMagnetQuery", true), v -> alexscavesSingleMagnetQuery = v);
        builder.pop();

        builder.comment("Architectury API patches.").push("architectury");
        gate(builder
                .comment("Call Architectury event listeners through reflection directly instead of building a new MethodHandle for every listener on every event. Tick, entity and player events all go through this.")
                .define("directEventDispatch", true), v -> architecturyDirectEventDispatch = v);
        builder.pop();

        builder.comment("Ad Astra patches.").push("adastra");
        gate(builder
                .comment("Memoise Ad Astra's per-dimension gravity and temperature constants.")
                .define("memoPlanetDefaults", true), v -> adastraMemoPlanetDefaults = v);
        builder.pop();



        builder.comment("Create: Copycats+ patches.").push("copycats");
        gate(builder
                .comment("Remember each block state's occlusion answer instead of re-running Copycats+' holder and instanceof checks on every canOcclude call.")
                .define("memoStateOcclusion", true), v -> copycatsMemoStateOcclusion = v);
        gate(builder
                .comment("Skip the config lookup, registry lookup and string building Copycats+ runs for every block entity and structure block that is not a copycat.")
                .define("fastMigrationChecks", true), v -> copycatsFastMigrationChecks = v);
        gate(builder
                .comment("Read the two Copycats+ client model settings at most once per second instead of twice per copycat model query.")
                .define("cachedModelConfig", true), v -> copycatsCachedModelConfig = v);
        gate(builder
                .comment("Replace the lambda and platform lookups Copycats+ runs on every Create block entity update with one cached class check.")
                .define("leanVirtualWorldCheck", true), v -> copycatsLeanVirtualWorldCheck = v);
        builder.pop();

        builder.comment("Vanilla patches. These are the only patches here that are not aimed at a specific mod.").push("vanilla");
        gate(builder
                .comment("When a world is closed, drop the references other mods keep to it (Citadel, Copycats, KubeJS, Opposing Force, Xaero's config library, Simply Swords, Subtle Effects). Without this the last world stays in memory on the title screen and next to the new world after rejoining.")
                .define("releaseClosedWorld", true), v -> releaseStoppedServer = v);
        gate(builder
                .comment("Maximum times a dropped item stack's model is drawn.")
                .defineInRange("itemEntityRenderCap", 1, 0, 5), 0, v -> itemEntityRenderCap = v);
        gate(builder
                .comment("Resolve a rendered string's font set once instead of once per glyph.")
                .define("memoGlyphFontSet", true), v -> vanillaMemoGlyphFontSet = v);
        gate(builder
                .comment("Reuse the recoloured text style while drawing outlined text instead of building a new one for every glyph of every outline pass.")
                .define("memoOutlineStyle", true), v -> vanillaMemoOutlineStyle = v);
        gate(builder
                .comment("Skip the back to front quad sort for render types whose result cannot depend on draw order: additive, lightning or glint blending that writes colour but not depth, like vanilla's eyes layer. Vanilla leaves eyes unsorted, but many mods build their own glow layer from it with sorting switched on and then draw a whole model into it, so with forty Aptrgangr on screen Cataclysm's cm_eyes layer was sorted every frame for nothing. Adding light is the same in any order, so the picture is identical. Client.")
                .define("skipOrderIndependentSort", true), v -> vanillaSkipOrderIndependentSort = v);
        gate(builder
                .comment("Makes /locate a lot faster, especially for rare structures.")
                .define("fasterStructureLocation", true), v -> vanillaFasterStructureLocation = v);
        gate(builder
                .comment("Throw away the empty rows above a structure template when it is read out of a mod or datapack. A structure block saves every block inside the box you drew, air included, and a lot of mods draw that box far taller than the build: Better Village saves all 246 of its village pieces in a box 48 blocks high, so a two block tall dirt path crossroad is stored as a 40 by 48 by 40 block template where 76,550 of the 76,800 entries are air, and across the whole mod 2.7 million of its 3.6 million entries are air rows sitting above the tallest real block. Every one of those entries costs about fifty bytes in memory for as long as the template is cached, which is until the next reload, so a fully loaded set of Better Village templates is around 135 megabytes of nothing, and it is that heap that a memory profiler points at when it says villages are leaking. It costs time too: when a piece is placed the game rebuilds every entry for every chunk the piece overlaps, runs the pool's processors over it and calls setBlock on it, so a wide street piece pays for its 76,000 air entries up to nine times over. With this on, air entries higher than the tallest real block or entity in the template are dropped as the template loads, and only those; the air inside and around the build stays exactly as saved, so rooms and hillside cuts are unchanged. The template's declared size is left alone so structure bounding boxes, jigsaw collisions and beardifier terrain adjustment are identical. The only visible difference is that a tall empty box no longer carves a shaft into a mountainside above the build, which is what a template made with structure voids would do anyway. Structures saved in a world's own generated folder by a structure block are never touched. Turn it off for stock behaviour.")
                .define("trimTemplateTopAir", true), v -> vanillaTrimTemplateTopAir = v);
        gate(builder
                .comment("Stop the entity tracker allocating a SectionPos for every tracked entity every tick.")
                .define("leanTrackerSectionPos", true), v -> vanillaLeanTrackerSectionPos = v);
        gate(builder
                .comment("Do the suffocation check with a loop instead of a Java stream.")
                .define("leanSuffocationScan", true), v -> vanillaLeanSuffocationScan = v);
        gate(builder
                .comment("Stop the container sync allocating a memoizing supplier for every slot every tick.")
                .define("leanMenuBroadcast", true), v -> vanillaLeanMenuBroadcast = v);
        gate(builder
                .comment("Stop the entity tracker allocating a movement vector for entities that did not move.")
                .define("leanTrackerDelta", true), v -> vanillaLeanTrackerDelta = v);
        gate(builder
                .comment("Answer repeated biome lookups on the render thread from a small per tick cache. Fog and sky colour sample 27 biomes per call, several times a frame.")
                .define("cacheBiomeQuartLookups", true), v -> vanillaCacheBiomeQuartLookups = v);
        gate(builder
                .comment("Work out what fluid the camera is in once per camera position instead of once per caller.")
                .define("memoCameraFluid", true), v -> vanillaMemoCameraFluid = v);
        gate(builder
                .comment("Index each mod jar's assets and data once so a lookup for a file the jar does not have is answered without touching the jar. Every model, blockstate and texture lookup asks every mod sharing a namespace, and each miss inside a jar throws three exceptions.")
                .define("indexModFiles", true), v -> vanillaIndexModFiles = v);
        gate(builder
                .comment("Build a mod jar's file index on a background thread and keep answering from the jar until it is ready, instead of making whoever asks first wait for the whole walk while every other thread asking about the same jar waits on its lock. Needs indexModFiles. Turn it off to build the index in place.")
                .define("indexModFilesInBackground", true), v -> vanillaIndexModFilesInBackground = v);
        gate(builder
                .comment("Answer folder listings of a mod jar (every tag, recipe, loot table and model folder, on every data pack load and resource reload) from its file index instead of walking the jar again. The index is kept for the whole session, Forge makes new pack objects for every load. Needs indexModFiles.")
                .define("indexModFileListing", true), v -> vanillaIndexModFileListing = v);
        gate(builder
                .comment("Remember which namespaces a mod jar's assets and data folders hold instead of walking the jar again each time somebody asks. FancyMenu asks every one of a few hundred mod packs for its namespace list every time it lists resources, and each answer is a directory walk inside the jar that, for every mod without a data folder or without an assets folder, ends in a file not found exception that is caught and turned into an empty list, which is tens of thousands of exceptions and walks at launch. Only packs read from inside a jar are remembered, a pack that is an ordinary folder on disk is always asked again, and every caller still gets its own fresh set with the same contents, in the same order, that the walk returned.")
                .define("cachePackNamespaces", true), v -> vanillaCachePackNamespaces = v);
        gate(builder
                .comment("Skip listing a mod jar for a namespace it does not have. Loading models, textures, lang files and recipes asks every mod pack to list a namespace, and a pack that has no folder for it answers by searching a path that is not there and throwing a file not found exception that is caught and ignored, which is the biggest single source of exceptions at launch. With this on the pack's remembered namespace list is checked first and a pack that does not have the namespace lists nothing, which is the same result. Needs cachePackNamespaces.")
                .define("skipAbsentPackNamespaces", true), v -> vanillaSkipAbsentPackNamespaces = v);
        gate(builder
                .comment("Place features with plain loops instead of a stream pipeline. Vanilla builds a stream with a lambda and a flat map stage for every placement modifier of every feature placement, which makes up a large part of the garbage world generation produces. Positions come out in the same order and the random source is used in the same order. Does nothing when Bye Pregen's own placement rewrite is active.")
                .define("placeFeaturesWithoutStreams", true), v -> vanillaPlaceFeaturesWithoutStreams = v);
        gate(builder
                .comment("Skip parsing an empty owner name as a UUID when a tameable animal is loaded. Every wild wolf, cat, parrot and modded tameable has an empty owner, and vanilla throws and swallows an exception to find that out. The owner stays unset.")
                .define("skipEmptyOwnerUuidParse", true), v -> vanillaSkipEmptyOwnerUuidParse = v);
        gate(builder
                .comment("Reuse the processor, graphics card and memory module lines of a crash report after the first one has been built. Building a crash report asks the hardware library for them again every time, which reads files from every mod jar, throws thousands of exceptions and takes a few hundred milliseconds, and mods build crash reports for errors they handle. Live memory figures are still read fresh.")
                .define("cacheStaticHardwareReport", true), v -> vanillaCacheStaticHardwareReport = v);
        gate(builder
                .comment("Index each zipped resource pack or data pack once so listing a folder inside it only walks the files under that folder. Vanilla walks every file in the zip for every listing, and a reload lists every folder of every namespace the zip has, so a big pack with many namespaces is walked thousands of times per reload. Same files in the same order.")
                .define("indexZipPacks", true), v -> vanillaIndexZipPacks = v);
        gate(builder
                .comment("Work out the sky colour once per camera position per frame. Fog setup, the sky renderer and shader uniform packs each ask for it separately and every call samples 27 biomes.")
                .define("memoSkyColour", true), v -> vanillaMemoSkyColour = v);
        gate(builder
                .comment("Keep the sky colour for a whole client tick instead of recomputing it whenever the camera position or partial tick changes, which is every frame while moving. The biome blend it samples spans 24 blocks, so one tick of camera movement cannot change it visibly. Needs memoSkyColour. Client.")
                .define("memoSkyColourPerTick", true), v -> vanillaMemoSkyColourPerTick = v);
        gate(builder
                .comment("Sample the blended biome fog colour once per client tick instead of once per frame. Same 216 biome lookups and Vec3 garbage per call as the sky colour. This is not bit exact: inside one tick every frame reuses the colour sampled at the start of that tick, so while you move across a biome border the fog colour can trail the camera by up to one twentieth of a second. Client.")
                .define("memoBiomeFogColour", true), v -> vanillaMemoBiomeFogColour = v);
        gate(builder
                .comment("Give NoiseChunk the empty beardifier marker when the chunk has no structure pieces or junctions to adapt terrain to, including the enhanced lists YUNG's API, Moog's Structure Lib and Integrated API keep. Beardifier.compute is called per noise cell and four mods inject into it, boxing a Double per call, so most chunks paid for structures they do not have. Server.")
                .define("skipEmptyBeardifier", true), v -> vanillaSkipEmptyBeardifier = v);
        gate(builder
                .comment("Drop dead and departed players from static player maps in Celestisynth, Brutality, More Artifacts and Tons of Enchants. Those mods key static maps by the player object and never remove the entry, so every death, respawn and dimension change kept the old player, its inventory and its level alive for the rest of the session. Keys whose entity is already removed are swept, nothing else in those maps is touched. Both sides.")
                .define("purgeStalePlayerMaps", true), v -> vanillaPurgeStalePlayerMaps = v);
        gate(builder
                .comment("Map each shared piece of the terrain density graph once per NoiseChunk instead of once per reference, and reuse the final density graph instead of rebuilding and rehashing it a second time. Every generated chunk and every structure height query builds a NoiseChunk. The resulting graph is the same one vanilla builds. Skipped for world generators whose density graph has little shared structure, where the bookkeeping would cost more than it saves. Server.")
                .define("reuseNoiseChunkDensityGraph", true), v -> vanillaReuseNoiseChunkDensityGraph = v);
        gate(builder
                .comment("Share the preliminary surface height of a column between NoiseChunks of the same world generator instead of rescanning it in every NoiseChunk. Only used where the result cannot depend on the NoiseChunk: no terrain blending, and only density function types whose output is known to depend on position alone. Server.")
                .define("shareSurfaceEstimates", true), v -> vanillaShareSurfaceEstimates = v);
        fastBiomeBlendValue = gate(builder
                .comment("Blend biome colours from a cached, incrementally summed grid instead of resampling the biome under every block in the blend square. Vanilla resamples the full square for every block, which is up to 225 biome lookups per block at the default blend radius. Output is identical.")
                .define("fastBiomeBlend", true), v -> vanillaFastBiomeBlend = v);
        gate(builder
                .comment("Every five seconds, drop dead player copies that another mod left registered as chunk loaders. Such ghosts keep hundreds of chunks loaded and spawning mobs at wherever they died until restart.")
                .define("purgeGhostPlayers", true), v -> vanillaPurgeGhostPlayers = v);
        gate(builder
                .comment("Remember which bone of a model a given name belongs to instead of walking the whole model tree again for every bone of every keyframe animation. Vanilla answers that question with a recursive stream over every part of the model, once per animated bone per entity per frame, and every walk allocates a few objects per part it visits. Result is identical.")
                .define("cacheModelPartLookups", true), v -> vanillaCacheModelPartLookups = v);
        gate(builder
                .comment("Run keyframe animations with plain loops. Vanilla allocates two capturing lambdas per animated bone and one more per animation channel, every entity every frame, which is a large part of the garbage the render thread produces. Output is identical, but a mod injecting into the middle of KeyframeAnimations.animate will not run while this is on.")
                .define("leanKeyframeAnimation", true), v -> vanillaLeanKeyframeAnimation = v);
        gate(builder
                .comment("Answer block and biome lookups in a chunk section that holds a single value with one type check and a field read instead of two interface calls through the palette and the bit storage. Air, deep stone and open water sections are all single value sections and they are the most common kind, so a large share of the lookups world generation, lighting, pathfinding and mob spawning make land here. Output is identical.")
                .define("uniformSectionLookup", true), v -> vanillaUniformSectionLookup = v);
        gate(builder
                .comment("Rotate a pose stack around a single axis with the short form of the multiplication instead of the general quaternion product. Nearly every rotation an entity, item or model part applies is around X, Y or Z alone, and JOML does about three times the arithmetic for those. Same result up to floating point rounding. Client.")
                .define("axisAlignedRotation", true), v -> vanillaAxisAlignedRotation = v);
        gate(builder
                .comment("Walk BlockPos.betweenClosed and Cursor3D with three counters instead of two integer divisions per position. Cursor3D is what every entity uses each tick to find the block it stands on, and what mob pathfinding uses for its collision checks. Same positions in the same order. An inverted BlockPos range yields nothing instead of looping forever.")
                .define("leanBlockPosRange", true), v -> vanillaLeanBlockPosRange = v);
        gate(builder
                .comment("Skip the fire and lava check that runs at the end of every entity movement when its answer cannot change anything. For a mob that is not burning and was not burning last tick, the game only uses this check to reset a fire timer that already holds the reset value, so the block scan is skipped. Burning entities and the client still run the full check. Turn it off for stock behaviour.")
                .define("skipIrrelevantFireScan", true), v -> vanillaSkipIrrelevantFireScan = v);
        gate(builder
                .comment("Raycasts that ignore fluids, such as mob line of sight, arrows and explosion exposure, no longer read the fluid in every block they cross. That fluid could never stop the ray, so every hit is the same. Rays that can hit fluids run exactly as before. Turn it off for stock behaviour.")
                .define("dryRaycast", true), v -> vanillaDryRaycast = v);
        gate(builder
                .comment("Game events such as steps, landings and block changes skip chunk sections that have no listeners, instead of creating an empty listener list in each of them and keeping it until the chunk unloads. The same listeners hear the same events in the same order. Turn it off for stock behaviour.")
                .define("leanGameEventLookup", true), v -> vanillaLeanGameEventLookup = v);
        gate(builder
                .comment("Zombies, husks, drowned and other mobs that hunt turtle eggs scan about 15,000 blocks around them every few seconds. When no chunk section in that area can contain the target block, the scan is skipped because it would find nothing. Mods whose goals use their own target test keep the full scan. Turn it off for stock behaviour.")
                .define("precheckBlockRemovalScan", true), v -> vanillaPrecheckBlockRemovalScan = v);
        gate(builder
                .comment("Piglins and hoglins look for soul fire, warped fungus and other repellents in a 17 by 9 by 17 area every second. When no chunk section in that area holds a repellent, the search is skipped and reports nothing, as it would have. Areas that reach into unloaded chunks still run the full search. Turn it off for stock behaviour.")
                .define("precheckRepellentScan", true), v -> vanillaPrecheckRepellentScan = v);
        gate(builder
                .comment("Undead mobs in daylight ask whether they stand in water, rain or a bubble column only after the light level and the random roll have already allowed them to burn, instead of every tick. The answer and the random numbers are the same. Mobs with their own water check keep the original order. Turn it off for stock behaviour.")
                .define("deferSunBurnWetCheck", true), v -> vanillaDeferSunBurnWetCheck = v);
        gate(builder
                .comment("Mob goal selectors skip the per goal check for disabled control flags while no flag is disabled, which is nearly always: flags are only disabled while a rider steers the mob. The same goals start and stop. Turn it off for stock behaviour.")
                .define("skipEmptyDisabledFlags", true), v -> vanillaSkipEmptyDisabledFlags = v);
        gate(builder
                .comment("When a chunk is generated next to villages, ruins or other structures that blend into the terrain, every block adds up a term for every structure piece and every jigsaw connection nearby, even though a piece only reaches 5 to 12 blocks past its box. With this on, each chunk works out once which pieces can reach each column, and every block only adds up those, in the same order. Every term it skips is exactly zero, so the result is identical to the last bit. Chunks whose pieces use unusual box types keep the full list. Turn it off for stock behaviour.")
                .define("boundBeardifierReach", true), v -> vanillaBoundBeardifierReach = v);
        gate(builder
                .comment("Every tag test the game makes, such as is this block climbable or is this item a sword, looks the tag up in the block's or item's tag set by hashing the tag name and comparing it with the stored names. With this on, each tag gets a number the first time it is used and each block, item and entity type keeps the sorted numbers of its tags, so a test compares two numbers instead. The numbers are rebuilt whenever tags are reloaded, so the answer is always the same as the set's own. Turn it off for stock behaviour.")
                .define("numberTagChecks", true), v -> vanillaNumberTagChecks = v);
        gate(builder
                .comment("Every tick the game walks every chunk that has an overdue block or fluid tick and asks whether that chunk is close enough to a player to run it. Chunks loaded by a large view distance but outside the simulation distance never are, so their overdue ticks are asked about again every single tick, twice. With this on, a chunk that says no is asked again a second later instead. Its ticks still run once a player gets close, at most one second later than they would have. Turn it off for stock behaviour.")
                .define("deferBlockedTickContainers", true), v -> vanillaDeferBlockedTickContainers = v);
        gate(builder
                .comment("Structure placement keeps asking the world generator how high the ground is at a column, and every answer builds a small slice of terrain over the full world height, which costs one to four milliseconds. The same column is often asked again, from a different worker thread or for another structure. With this on, each world generator remembers the last 32768 answers for its seed and height range and returns them instead of building the slice again. The answer only depends on the generator, the seed, the column and the height range, so it is the same number. Turn it off for stock behaviour.")
                .define("shareColumnHeights", true), v -> vanillaShareColumnHeights = v);
        builder.pop();
















        builder.comment("Easy Villagers patches.").push("easyvillagers");
        gate(builder
                .comment("Ticks between the idle dirty marks the Easy Villagers blocks make. The trader, auto trader, farmer, iron farm and breeder block entities all call setChanged unconditionally on every single tick while a villager is inside them, and setChanged is not a flag write: it marks the chunk unsaved and then runs the comparator update, which allocates a position and reads the block state for all four horizontal neighbours and can walk a second ring on top of that. A trading hall or an iron farm wall is dozens of those blocks doing it twenty times a second for a villager whose only change is one tick of age. The blocks still mark themselves dirty on the ticks that actually do something, since those paths call setChanged themselves, so nothing is lost on save, and a comparator reading one of these blocks updates within this many ticks instead of instantly. 1 restores the stock every tick behaviour.")
                .defineInRange("dirtyMarkInterval", 20, 1, 200), 1, v -> easyvillagersDirtyMarkInterval = v);
        builder.pop();









        builder.comment("Entity Texture Features patches.").push("etf");
        gate(builder
                .comment("Stop the missing property error from copying a call stack. When a texture or model rule file leaves out a property, Entity Texture Features builds that property by reading it from the file and throwing its own error if the key is not there, and the code that asked catches it, discards it and moves on, which happens for every property type of every rule in every file. A big pack throws over ten thousand of those at launch and each one copies the loading thread's call stack, which nothing ever reads, because the only thing the mod does with the error is log its message. With this on the same error with the same message is thrown without capturing a stack. Client.")
                .define("stacklessPropertyExceptions", true), v -> etfStacklessPropertyExceptions = v);
        builder.pop();

        builder.comment("Async Particles patches.").push("asyncparticles");
        gate(builder
                .comment("Use fixed sampler names when the GPU particle renderer sets up its shader. Before every particle layer it draws, the renderer binds twelve texture samplers by building each name, Sampler0 to Sampler11, as a new string, which is twelve throwaway strings per layer per frame for as long as particles are on screen. With this on the same twelve names are kept as constants and everything else about the shader setup is unchanged.")
                .define("constantSamplerNames", true), v -> asyncparticlesConstantSamplerNames = v);
        builder.pop();








        builder.comment("NotEnoughAnimations patches.").push("notenoughanimations");
        gate(builder
                .comment("Work the pet petting check's entity raytrace out once per tick instead of once per frame. NotEnoughAnimations asks every one of its animations whether it applies for every player model it sets up, so once per player per frame, and the pet animation's only guard is whether that player is crouching. Crouching is something players do constantly, and the moment one is, the check fires a real raytrace: it builds the eye position, the view vector, the reach point and an inflated bounding box, then asks the level for every entity that box touches and tests each of them, all to find out whether there is a tamed wolf or cat right in front of the player's face. That is a full entity sweep per crouching player per frame, and with a shader pack's shadow pass it is paid again for every extra pass. Every input the raytrace reads, the previous tick position, the rotation, the bounding box and the eye height, only ever changes on a tick boundary, so the answer is identical for every frame inside the same tick; it is now worked out on the first frame of a tick and reused for the rest of it. The hit result handed back is the same object the raytrace produced, so the animation, the pet it picks and the height check behind it are unchanged.")
                .define("cachePetScan", true), v -> notenoughanimationsCachePetScan = v);
        gate(builder
                .comment("Stop the sheathed sword tracker running on the logical server. NotEnoughAnimations is a client only mod, but the mixin that carries its animation data sits on Player rather than on the client player class, so in single player and on a listen server the integrated server's own copy of every player runs it too, and every one of those player ticks calls the render layer's tracker: two held item reads and two lookups into a static HashSet, writing the answer into a field that only the renderer ever reads and that only exists on the client copy of the player. That set is filled once on the render thread the first time the sword layer draws, and read here from the server thread, which is an unsynchronised HashSet touched from two threads for a result nothing uses. With this on the server side of the tick returns immediately, the client side is untouched, and the sheathed sword still shows up exactly as before.")
                .define("skipServerSwordTracking", true), v -> notenoughanimationsSkipServerSwordTracking = v);
        builder.pop();





        builder.comment("Bosses of Mass Destruction patches.").push("bmd");
        gate(builder
                .comment("Skip the mob ward proximity scan while no mob ward is loaded. Bosses of Mass Destruction hooks the natural spawner so a mob ward blocks spawning around it, and the check it runs there is the most expensive thing in the mod: for every spawn position the game accepts, anywhere in the world, it builds a chunk position, resolves a capability off the level into an Optional, allocates a closure, and then walks a nine by nine block of chunks around that position, allocating a fresh chunk position and a fresh result list for each one of the eighty one, just to learn that not one of them holds a ward. The game asks that question hundreds to thousands of times a second on a loaded server, so on a world with no wards in it this alone is tens of thousands of throwaway objects a second on the tick thread. This patch keeps track of every position the mod itself puts into that cache and every position it takes back out, and returns straight away when there is no ward anywhere, which is the same answer the scan would have arrived at. So the guard cannot go stale if a future version of the mod changes how positions get in, one call in every four thousand runs the real scan anyway and anything it finds is folded back into the tracker, which turns the guard off within a second of a ward actually existing.")
                .define("leanWardSpawnScan", true), v -> bmdLeanWardSpawnScan = v);
        gate(builder
                .comment("Skip the monolith explosion scan while no monolith is loaded. Every explosion in the world, from any mod or any creeper, goes through the mod's hook that asks whether a monolith is standing within sixty four blocks so it can boost the blast, and that question is answered by the same nine by nine chunk walk the mob ward uses, with a fresh chunk position and a fresh result list for each of the eighty one chunks. Monoliths only exist in the Obsidilith arena, so in an ordinary world every single explosion pays for a hundred and sixty odd throwaway objects to be told no. This uses the same position tracker as the ward patch and returns the unmodified blast power straight away while no monolith is loaded, with the same one in four thousand real scan as a safety net.")
                .define("leanMonolithExplosionScan", true), v -> bmdLeanMonolithExplosionScan = v);
        gate(builder
                .comment("Skip the levitation block flight check while no levitation block is loaded. The mod runs this off the player tick event for every player on the server, and it is not cheap: it allocates a list, fills it with nine block positions, streams that into a set of chunk positions through a lambda, then streams the set again and for each chunk resolves the level capability into an Optional and asks it for a list of levitation blocks, allocating a result list every time. That is roughly sixty short lived objects per player per tick, on the tick thread, in a world where the Obsidilith arena that contains those blocks may not even be generated yet. With this on a player who cannot already fly and is nowhere near a tracked levitation block skips the whole thing. Players who can fly, which includes creative and spectator and anyone the block has already granted flight to, always run the stock code, so flight is still taken back correctly when you walk out of the beam. One call in four thousand runs the real check regardless so the tracker cannot drift.")
                .define("leanLevitationFlightScan", true), v -> bmdLeanLevitationFlightScan = v);
        gate(builder
                .comment("Run the mod's player tick handler once a tick instead of twice. Forge fires the player tick event twice per tick, once before the player ticks and once after, and this handler never checks which of the two it is in, so everything behind it runs double: the capability lookup and the closure it allocates for every player every tick, the levitation block flight check, and the movement history the Lich reads to lead its shots. That last one is the visible part, since the history only holds ten samples and at two samples a tick it covers five ticks of movement rather than the ten the mod is written for, which makes the Lich's aim read your position over a shorter window than intended. With this on only the end of the tick runs. Turn it off if you want the stock double firing back.")
                .define("singlePlayerTickPhase", true), v -> bmdSinglePlayerTickPhase = v);
        builder.pop();








        builder.comment("Deeper and Darker patches.").push("deeperdarker");
        gate(builder
                .comment("Look at which block was broken before reading the enchantments off the tool. Deeper and Darker listens to every block break in the game and the very first thing its handler does is ask the held item for its Silk Touch level, which walks the whole enchantment list stored on that item and builds and looks up a fresh ResourceLocation for every entry on it, and only after that does it check whether the broken block is one of the three blocks it actually cares about, which are crystallized amber, the ice lily and the ancient vase. Every other block in the game, and there are tens of thousands of them in a mining trip, pays that scan for nothing, and a vein miner breaking a whole ore body in one tick pays it once per block. With this on the block is checked first and the enchantment scan only happens for those three blocks, which is exactly when its answer was ever used. Turn it off for stock behaviour.")
                .define("leanBreakEvent", true), v -> deeperdarkerLeanBreakEvent = v);
        gate(builder
                .comment("Let the sculk jaw skip the same things when you stand in it that it already skips when you step on it. The jaw has two separate bite paths: the step on path lets creative players, sneaking players and the mod's own sculk mobs walk over it untouched and only bites while the block says it can bite, and a second path that fires for anything standing inside the block has none of those checks at all. So a creative player, a sneaking player or a sculk mob that ends up inside an open jaw gets chewed anyway, which is the opposite of what the mod's own rule says. With this on the inside path uses the same three checks the step on path uses. Turn it off for stock behaviour.")
                .define("fixSculkJawGuards", true), v -> deeperdarkerFixSculkJawGuards = v);
        gate(builder
                .comment("Bite once per sculk jaw instead of once per tick. When the jaw bites it sets itself to biting, which removes its collision box so the victim drops into the block, and it schedules itself to reopen thirty five ticks later. Meanwhile the second bite path fires for everything inside the block on every single tick with no cooldown of its own, so one step on an open jaw is the three damage the bite is written for plus another three damage twenty times a second for the whole thirty five ticks, around a hundred damage from one block, and the same damage source is built again on the client every tick as well. With this on the inside path stays quiet while the jaw is already mid bite, so a jaw does the one bite its step on path deals and then stays shut until it reopens. Turn it off for stock behaviour.")
                .define("fixSculkJawRepeatBite", true), v -> deeperdarkerFixSculkJawRepeatBite = v);
        gate(builder
                .comment("Only write the sonorous staff's charged flag when it changes. The staff rewrites that flag into its own NBT on every tick for every slot it sits in on both logical sides, and it uses the get or create form, so a staff that has never been charged still gets a tag permanently attached and then written to twenty times a second for as long as it is in anyone's inventory, on both sides, forever. The flag is only read to decide whether the staff glows. With this on the flag is compared first and only written when it actually flips, and a staff that is not charged is left with no tag at all, which is the same answer the glow check reads. Turn it off for stock behaviour.")
                .define("leanStaffChargeTag", true), v -> deeperdarkerLeanStaffChargeTag = v);
        gate(builder
                .comment("Reuse one random number generator for the warden heart's heartbeat instead of building a new one every tick. The heart rolls for its ambient thump from its inventory tick, and it builds a brand new random source for that roll, which means a fresh seed uniquifier compare and swap, a nanotime read and an atomic long allocation, per heart, per slot, per tick, on both logical sides, to then almost always decide not to play anything. With this on the roll comes from one generator kept per thread. The thump happens exactly as often as before. Turn it off for stock behaviour.")
                .define("reuseHeartRandom", true), v -> deeperdarkerReuseHeartRandom = v);
        builder.pop();







        builder.comment("Iron's Spells 'n Spellbooks patches.").push("ironsspellbooks");
        gate(builder
                .comment("Answer \"is this curio worn\" out of the shared per tick curio contents cache instead of walking the whole curio inventory every time. The mod's curio items answer that question by asking the curios api for the wearer's inventory, handing it a lambda and letting it walk every slot of every curio type until it finds a match, allocating a lazy optional, a slot context, a slot result and a java optional along the way, and it asks from places that run constantly: the invisibility ring is checked from the entity invisibility hook, which fires for every entity anything asks about on every frame on the client and again from mob targeting on the server, and the summon and spell paths ask again on top of that. The answer only changes when someone actually equips or unequips something. With this on the question is answered from the set of worn items this mod already keeps per entity, which is rebuilt at most once per entity tick and thrown away the moment a curio change, equip or unequip event fires, so a repeat question is one set lookup with no allocation. A curio swapped by something that never fires a curios event can read one tick stale. Turn it off for stock behaviour.")
                .define("leanCurioEquipCheck", true), v -> ironsspellbooksLeanCurioEquipCheck = v);
        gate(builder
                .comment("Throw away the client's copy of other players' spell state when you leave a world. The client keeps a map of every player it has been told is casting, keyed on that player's entity id, and nothing ever removes from it: not logging out, not changing world, not the player leaving. It grows for the whole time the game is open, and because entity ids start again from low numbers on every world or server you join, the next world hands those same ids to completely different players, who then inherit whatever the old occupant of that id was doing. That shows up as players stuck in a casting pose, spin attack fire trails on someone standing still, and entities being drawn through the frustum cull because the stale entry says they are casting, until a real packet happens to overwrite it. With this on the map is emptied when you disconnect and again when you join, so a fresh world starts from nothing. Turn it off for stock behaviour.")
                .define("clearStaleSyncedData", true), v -> ironsspellbooksClearStaleSyncedData = v);
        gate(builder
                .comment("Hand the armour models the same resource location every time they ask for their model, texture or animation file instead of building and validating a new one on every draw. A cultist wearing its armour asked three times per frame. The transmog aware generic armour model is left alone. Client.")
                .define("reuseArmorModelLocations", true), v -> ironsspellbooksReuseArmorModelLocations = v);
        builder.pop();




        builder.comment("L2 Archery patches.").push("l2archery");
        gate(builder
                .comment("Remember the upgrade list a bow's NBT spells out instead of parsing it back from scratch on every question, and stop the lookup attaching an empty NBT compound to every bow it is handed. Every single thing this mod wants to know about a bow goes through one static reader: what features it has, whether it takes flux energy, how much energy it holds, how many upgrade slots are left, and what its tooltip says. That reader opens the stack with getOrCreateTag, which permanently glues an empty compound onto any bow that did not have one, including the throwaway display stacks JEI and the creative tabs hand it, and then for every entry in the upgrade list it builds a fresh ResourceLocation out of the stored string, namespace and path character validation included, and looks that up in the registry. The callers are on the frame path: vanilla asks whether an item's durability-style bar should be drawn once per rendered stack per frame, this mod's flux bar decorator asks again and then twice more for the energy numbers, so one bow icon on screen is three or four full parses per frame and an inventory or a JEI page full of them is dozens. The server pays it twice per tick per side for as long as a bow is being drawn. The registry hands back the same upgrade object for a given id forever, so the answer cannot change unless the NBT changes. With this on the decoded list is remembered against the upgrade list tag itself, so a repeat question is one identity map read and a copy of a list that is almost always empty or one element long, a bow with no upgrade tag answers immediately without touching its NBT at all, and anything with malformed or unexpected NBT falls through to the mod's own code untouched. The cache is per thread and bounded. A mod that edits an existing upgrade list tag in place rather than replacing it could read one answer stale. Turn it off for stock behaviour.")
                .define("cacheBowUpgrades", true), v -> l2archeryCacheBowUpgrades = v);
        gate(builder
                .comment("Reuse the arrow texture path instead of building a new one for every arrow on every frame. The arrow renderer works out which texture to draw by asking the item registry for the arrow item's id and then allocating a fresh ResourceLocation out of that id's namespace and a fixed path string, and it does that once per arrow entity per frame, so a volley in flight is a steady stream of short lived objects and two string validations each, all producing the exact same handful of values. With this on the finished path is remembered against the namespace it was built from and handed back unchanged. Turn it off for stock behaviour.")
                .define("cacheArrowTexture", true), v -> l2archeryCacheArrowTexture = v);
        gate(builder
                .comment("Skip rebuilding the whole bow stat model every frame just to find out the field of view should not change. This mod hooks the field of view calculation, which runs on every single frame, and the moment you are holding one of its bows it copies the bow's upgrade list into a new list, parses the bow's entire enchantment tag into a new map, and then wraps the bow config in one extra object per stat carrying upgrade and enchantment, all to read two numbers out of it. For as long as you are not actually drawing the bow the pull progress is zero, which makes the whole calculation collapse to the untouched field of view put through the game's own fov effect scale option, so none of that work can change the answer, and holding a bow without drawing it is the normal case. With this on that case is answered directly and the model is only built while the bow is actually being pulled. One difference: a bow config with a pull time of zero produced a not-a-number field of view before and now produces a sane one. Turn it off for stock behaviour.")
                .define("skipIdleBowFov", true), v -> l2archerySkipIdleBowFov = v);
        gate(builder
                .comment("Rebuild the bow info sidebar text only when the bow or the arrow actually changes. The sidebar that lists damage, punch, pull time, speed and field of view under the hotbar rebuilds its entire contents from scratch on every frame it is visible, and it is visible for a couple of seconds after every switch and continuously while the sidebar hold key is down. Each rebuild parses the bow's upgrades and enchantments into new collections, merges two feature lists, wraps the bow config once per stat source, runs a potion effect aggregator over the result and then allocates a fresh translated and coloured component for every line, a dozen or so objects a line, for text that is character for character identical to the frame before. With this on the finished lines are remembered against a copy of the bow stack and a copy of the arrow stack and handed straight back while both still match, so anything that changes either one, an upgrade applied, an enchantment added, energy spent, a different arrow picked up, drops the cache and rebuilds exactly as before. Turn it off for stock behaviour.")
                .define("cacheBowInfoText", true), v -> l2archeryCacheBowInfoText = v);
        builder.pop();

        builder.comment("L2 Backpack patches.").push("l2backpack");
        gate(builder
                .comment("Count what is inside a bag by reading its stored item list instead of rebuilding every stack in it. A bag reports how many of its slots are used by asking for its full contents, and that call builds a list as long as the bag is wide, sixty four slots at base and up to two hundred and fifty six on a fully upgraded one, and then deserialises a real ItemStack out of NBT for every entry in it, each one copying that entry's whole item tag, enchantments, stored energy, nested bag contents and all. The answer is then used for nothing but counting the entries that are not empty. Two callers sit on the frame path and both run for every bag icon that is on screen: the little number the mod draws in the corner of the icon asks once, and the item model predicate that picks the open or closed bag texture asks again, so one bag in your hotbar is two full rebuilds per frame and an inventory page or a JEI listing of them is dozens. With this on the count is taken straight off the stored list, one pass over the NBT entries with no stack ever built and no list allocated, and slots written twice in the same list still resolve the same way they did before. The lookup also stops attaching an empty compound to bags that had no NBT. An entry whose item no longer exists in the game counts as empty exactly as it did before. Turn it off for stock behaviour.")
                .define("leanBagCount", true), v -> l2backpackLeanBagCount = v);
        gate(builder
                .comment("Read a bag's upgrade level and its stored item list without gluing an empty NBT compound onto it. Both readers open the stack with getOrCreateTag, which permanently attaches an empty compound to any bag that did not already have one, and the list reader does it twice in the same two line method. Every throwaway display stack the creative tabs and JEI hand these items picks that compound up, and on a real bag it means a tag that is written into the save file and sent over the network forever for no content at all. Neither reader ever writes anything, so the compound serves no purpose. With this on both read the tag only if it is already there and answer with the same upgrade level and the same empty list they would have answered with otherwise. Turn it off for stock behaviour.")
                .define("leanBagTagRead", true), v -> l2backpackLeanBagTagRead = v);
        gate(builder
                .comment("Look up which curio slot holds a wearable backpack once per tick per entity instead of twice per frame. This mod hangs two render layers, one for the backpack model and one for the item worn on the back, on every humanoid renderer in the game, which means every zombie, skeleton, piglin, villager and player you can see pays both of them on every frame. Each layer checks the chest slot first, and when there is no backpack there, which is the case for essentially every mob in the world, it falls through to a full walk of that entity's curios inventory, every slot type, cosmetic stacks and worn stacks both, with a boxed render flag read per slot, and finds nothing. Fifty mobs on screen is a hundred complete curios walks per frame. A curios inventory can only change on a tick boundary, so the answer cannot differ between two frames of the same tick. With this on the result of that walk is remembered against the entity and the layer asking, and thrown away whenever the client tick advances, so the walk happens at most once per tick per entity per layer instead of once per frame. The cache is only used on the render thread and is bounded. A backpack equipped or removed mid tick shows up one tick later than it did before. Turn it off for stock behaviour.")
                .define("cacheBackLayerScan", true), v -> l2backpackCacheBackLayerScan = v);
        builder.pop();

        builder.comment("L2 Library patches.").push("l2library");
        gate(builder
                .comment("Stop the effect icon overlay walking every entity on screen on every frame when it has nothing to draw. L2 Library hangs a handler on the living entity render event, and on the GeckoLib render event on top of that, so every mob, every animal and every player you can see pays it once or twice per frame. The handler asks the entity for its effect icon capability, asks a second time to actually pull it out, allocates a list, and walks the effect map looking for effects that want to draw an icon over the mob's head. Almost no effect in the game wants that, so for the overwhelming majority of entities the whole thing is two capability lookups, two throwaway Optionals, a list and a map iterator per entity per frame to find nothing. On top of that the mod's own renderOverlayIcons client option, the one that is supposed to turn the icons off, is only read much later when the icons are about to be drawn, so switching it off still pays for building the entire list every frame and then throws it away. With this on the handler answers straight away when the option is off, and otherwise resolves the capability once and stops there when the entity has no tracked effects on it at all, which is the same nothing the mod was already going to do. An entity that actually has an icon effect falls through to the mod's own code untouched. Turn it off for stock behaviour.")
                .define("leanEffectIconScan", true), v -> l2libraryLeanEffectIconScan = v);
        gate(builder
                .comment("Stop one player walking away from a mob wiping that mob's effect icons off everybody else's screen. When a player stops tracking an entity L2 Library loops over every tracked effect on that entity and sends an effect removed packet, but it sends it with the tracking entity distributor, which means the packet goes to every player who can see that entity, not just the one who walked off. On a server that is a straight desync: the players still standing next to the mob lose its icons for no reason and do not get them back until the effect is applied again. The packet is pointless even for the player it was meant for, because their client is throwing the entity away on the very same tick, which takes the icon data with it. With this on the stop tracking handler does nothing at all, which fixes the desync and also removes one packet per remaining viewer per effect every time anything leaves anyone's range, which on a busy world is constant. Single player is unaffected either way because there is nobody else to send to. Turn it off for stock behaviour.")
                .define("fixStopTrackingSync", true), v -> l2libraryFixStopTrackingSync = v);
        gate(builder
                .comment("Remember whether an effect is one of the ones L2 Library syncs instead of asking the tag system every single time. Every potion effect applied, removed or expired anywhere in the world, on any entity, on either side, runs through one check, and so does every effect on an entity that enters or leaves a player's tracking range. That check looks the mod's tracked effects tag up in the registry tag manager and then walks the tag's contents comparing entries until it finds a match or runs out, so a pack with a long tag pays that walk for every effect of every entity that comes into view. The answer cannot change unless tags reload. With this on the answer is remembered against the effect itself in a small per thread identity map and a repeat question is a single array probe, and any datapack or tag reload throws the whole thing away and rebuilds it from the mod's own code. Turn it off for stock behaviour.")
                .define("cacheTrackedEffects", true), v -> l2libraryCacheTrackedEffects = v);
        gate(builder
                .comment("Resolve an L2 capability once per lookup instead of two or three times. Every L2 mod in the pack reads its data through one pair of helper methods, and both of them ask the capability for its value, wrap the answer in a fresh Optional, ask whether that Optional is empty, throw it away and then do the exact same thing again to actually read the value out. L2 Hostility goes through this for its trait data on essentially every mob on every tick, and the player side runs once per registered capability per player per tick on top of whatever the mods themselves ask for. With this on the value is pulled out once with no Optional at all and handed straight back, and a capability that genuinely is not there still falls through to the mod's own code, including the player side revive dance it does for a freshly cloned player. Turn it off for stock behaviour.")
                .define("leanCapabilityResolve", true), v -> l2libraryLeanCapabilityResolve = v);
        gate(builder
                .comment("Skip the conditional token bookkeeping for players who have no tokens. L2 Library gives every player a conditional data capability that other L2 mods hang short lived state on, and its tick swaps the whole token map out for a brand new HashMap, ticks the old one, copies anything that was added back in and swaps again, all so that a token that adds another token while ticking does not trip the iterator. When the map is empty, which it is for most players most of the time, nothing can be added because nothing runs, so the entire dance is one HashMap and one iterator allocated and thrown away per player per tick to move zero entries around. With this on an empty map skips straight to the respawn health restore the same tick does and stops there, which leaves the capability in exactly the state the mod's own code would have left it in. A player who actually has tokens runs the mod's code untouched. Turn it off for stock behaviour.")
                .define("skipEmptyConditionalTick", true), v -> l2librarySkipEmptyConditionalTick = v);
        builder.pop();


        builder.comment("L2 Complements patches.").push("l2complements");
        gate(builder
                .comment("Ask the cheap question first when something takes damage. This mod listens to every single damage event in the game and runs up to seven \"is the victim immune to this\" checks through one shared helper, and that helper is not cheap: for each check it walks all six of the victim's equipment slots, and for every enchantment written on every one of those stacks it pulls the id string back out of the NBT and builds a fresh ResourceLocation out of it, namespace and path character validation included, just to compare it against one enchantment. Six of those seven checks are then immediately paired with a much cheaper question that decides the whole thing anyway, things like \"was this actually fire damage\" or \"was there even an attacker\", and the mod asks them in the wrong order, so a mob in enchanted armour pays the full armour walk seven times for every arrow, every tick of burning and every hit it takes, on a pack where hundreds of damage events can land in a single tick, only to be told at the end that the damage type never matched. With this on the cheap damage type question is asked first and the armour walk only happens when it can still change the outcome. The answer for every check is the same one the mod would have reached, because both halves are plain reads with no side effects, only the order changed. Turn it off for stock behaviour.")
                .define("leanAttackChecks", true), v -> l2complementsLeanAttackChecks = v);
        gate(builder
                .comment("Compare the enchantment id as a string instead of rebuilding it into a ResourceLocation for every entry. This mod overrides the maximum durability of every enchanted item in the game so its own armour durability enchantment can multiply it, and the lookup behind that override walks the item's whole enchantment list and, for every entry, parses the stored id string back into a new ResourceLocation with full namespace and path validation before comparing it. Nothing calls that override rarely: vanilla asks for maximum durability once per rendered stack per frame to work out the durability bar width and again for its colour, plus every damage check and every tooltip, so an open inventory of enchanted gear is hundreds of throwaway ResourceLocations a frame to answer a question that is almost always no. With this on the stored id is compared directly against the wanted id as a string, which is exactly the same comparison without the parsing or the allocation, and anything with an id written in the short form the parser would have expanded falls through to the mod's own code untouched. Turn it off for stock behaviour.")
                .define("leanEnchantmentLookup", true), v -> l2complementsLeanEnchantmentLookup = v);
        gate(builder
                .comment("Do not rebuild a tooltip that has nothing from this mod in it. This mod's tooltip handler replaces its own enchantment description lines with longer ones, and to do that it copies every line of the tooltip into a new wrapper object in a new list and parses the item's whole enchantment tag into a new map before it looks at whether any of those lines are even its own. Tooltips are rebuilt from scratch on every frame the item is hovered, and JEI rebuilds them again for its own panels, so every enchanted item anyone looks at pays that copy on every frame to find nothing and throw the copy away. With this on the item's enchantment tags are scanned for an id belonging to this mod first, which is a plain string check with no allocation, and the handler is skipped entirely when there is none. An item that does carry one of this mod's enchantments, and enchanted books that store them, go through the mod's own code exactly as before. Turn it off for stock behaviour.")
                .define("skipUnrelatedTooltips", true), v -> l2complementsSkipUnrelatedTooltips = v);
        gate(builder
                .comment("Make the chain digging guard set safe to read from two threads. This mod keeps a plain HashSet of the players currently in the middle of a chain break so that the blocks it breaks itself do not recurse, and it takes a lock around adding and removing but not around reading. The read happens in its block particle hook, which fires on the client render thread as well as the server thread, so in single player one thread can be walking that set while the other is growing it, which is the case where a HashSet can hand back a wrong answer or spin inside a resize. With this on the set is swapped for a concurrent one when the class loads, so reads never see a half finished write and the lock the mod already takes still works exactly as before. Nothing else changes and nothing gets slower, the concurrent set is lock free for the read side. This one is decided when the enchantment class first loads, which is before configs finish loading, so turning it off only takes effect from the next game start.")
                .define("fixBreakerSetRace", true), v -> l2complementsFixBreakerSetRace = v);
        builder.pop();



        builder.comment("Ars Additions patches.").push("arsadditions");
        gate(builder
                .comment("Only run a charm's per tick work for the charm you are actually holding. Every charm item in this mod runs the whole charm routine from Item#inventoryTick, which the game calls once per tick for every single slot of your inventory, and that routine then asks \"which charm of this type is the player using\" through a lookup that only ever looks at your main hand, your off hand and your curio slots. A charm sitting in your backpack can therefore never be the one that is found, so its tick either does nothing at all or, far worse, finds the charm you do have equipped and spends one of that charm's charges. That is the bug: carry three spare night vision charms and the one in your curio slot drains four times as fast as it should, because all four stacks ran the routine and all four of them pointed at the same equipped charm. It is also pure waste, since each of those ticks reads the light level at your feet, reads the block state under you and, whenever one of those cheap checks passes, resolves your curios capability and builds a fresh list of matching curio slots, twenty times a second per spare charm. With this on a charm that is not in one of your hands does nothing on inventory tick, which is exactly what it should already have been doing: held charms are untouched, and equipped charms keep going through the curios tick the mod registers for them. Turn it off for stock behaviour.")
                .define("skipStowedCharmTick", true), v -> arsadditionsSkipStowedCharmTick = v);
        gate(builder
                .comment("Remember that there was no ruined portal under your feet instead of asking the world again every second. This mod runs a server tick handler that, once a second for every player online, asks the structure manager whether the block the player is standing on belongs to a ruined portal, purely so it can hand out one advancement. That question is not cheap: it pulls the player's chunk up to the structure references stage, walks every structure that has ever placed a reference into that chunk, and for each one does a registry id lookup and builds a throwaway Optional to test it against a tag, then reads the piece bounding boxes of the survivors. On a pack with dozens of structure mods that is a real amount of work per player per second, and it keeps happening forever, long after everyone has the advancement. With this on a block position that came back empty is remembered in a small fixed table and answered from there, so a player who is standing still, fighting, building or simply idle pays the lookup once instead of once a second. Only the empty answers are remembered, so a real ruined portal is always found by the mod's own code, and the table is wiped periodically so a structure placed by command is still picked up. Turn it off for stock behaviour.")
                .define("cacheRuinedPortalScan", true), v -> arsadditionsCacheRuinedPortalScan = v);
        gate(builder
                .comment("How far away a magelight lantern still bothers to make its glow particles, in blocks. Every lit magelight lantern spawns ten glow particles every single tick, which is two hundred a second each, and it does that for every lantern loaded on your client no matter how far away it is, building a fresh colour object and a fresh particle definition for each one. A decorated base with twenty of them is four thousand particle spawns a second, almost all of them for lanterns you cannot even make out. With this at thirty two a lantern further away than that skips the burst entirely, which is beyond the range the glow reads as anything but a faint dot. Raise it if you want the glow visible from further out, lower it if you want it cheaper still, or set it to zero to leave the mod alone entirely. Nothing on the server is affected, this code already only runs on the client.")
                .defineInRange("magelightParticleDistance", 32, 0, 256), 0, v -> arsadditionsMagelightParticleDistance = v);
        builder.pop();

        builder.comment("Duplicationless patches.").push("duplicationless");
        gate(builder
                .comment("Only keep the Duplicationless entity tracker up to date once something has actually asked it a question. Duplicationless is a library: it ships no features of its own, it exists so other mods can ask it which entities are in a given chunk or chunk section. To be able to answer that it wires itself into three of the busiest paths in the game. It wraps the position write on every entity, so every mob, item, arrow and player on both the server and your client runs its chunk and section comparison and three boxed doubles every time anything moves. It wraps the per chunk tick, so every loaded chunk allocates and fires two events on every server tick. It hooks every block state change. All of that feeds one set of maps, and every entity that joins a world, leaves a world, walks across a chunk border or moves across a section border allocates a nine field task object and pushes it onto a queue for those maps, forever, whether or not a single mod in the pack ever reads them. With this on the map bookkeeping is skipped until the first real query arrives for that dimension. The moment one does, the tracker is rebuilt from the world's own live entity list before the answer is handed back, so the answer is the same one the stock code would have given, and from then on that dimension is kept up to date normally. If nothing ever asks, nothing is ever paid. The event firing itself lives inside Duplicationless' own mixins and cannot be reached from here, so that part is unchanged. Turn this off if a mod that reads the tracker is misbehaving.")
                .define("lazyEntityTracker", true), v -> duplicationlessLazyEntityTracker = v);
        builder.pop();

        builder.comment("Corpse patches.").push("corpse");
        gate(builder
                .comment("Ticks between the idle bookkeeping passes a corpse entity does while it just lies there. Every corpse asks isEmpty() on every single server tick, and that question is four Java stream pipelines over the whole stored death inventory: the thirty six main slots, the four armour slots, the offhand slot and then the additional items list, which in a modded pack holds every curio, trinket and backpack the player was carrying. The streams short circuit on the first stack they find, so a corpse nobody has looted yet is cheap, but a corpse that has already been emptied has to walk every one of those lists to the very end before it can answer, and an emptied corpse is exactly what sits in the world for the whole despawn window, or forever if despawning is turned off. The same tick also writes the skeleton flag into the corpse's synced data every tick whether or not it changed. With this at twenty both of those happen once a second instead of twenty times a second, so a corpse despawns and turns into a skeleton up to this many ticks later than it would have, which is nothing against timers measured in minutes or hours. 1 restores the stock every tick behaviour.")
                .defineInRange("idleScanInterval", 20, 1, 200), 1, v -> corpseIdleScanInterval = v);
        gate(builder
                .comment("Seconds between sweeps of the old death file folder. Corpse remembers every death on disk and deletes the files older than maxDeathAge, and it kicks that sweep off from the death event itself, by starting a brand new bare java thread every single time any player dies. That thread lists the death folder of every player and stats and deletes files, so on a busy server several of those threads end up walking and deleting inside the same folders at the same time with nothing coordinating them, and they are not daemon threads so a stuck one keeps the process alive. maxDeathAge is measured in days, so there is no reason at all to do this after every death. With this at three hundred the sweep still runs, just at most once every five minutes across the whole server, and the deaths that would have been cleaned are cleaned by the next one. 0 restores the stock sweep after every death.")
                .defineInRange("deathSweepInterval", 300, 0, 86400), 0, v -> corpseDeathSweepInterval = v);
        builder.pop();

        builder.comment("Fluidium patches.").push("fluidium");
        gate(builder
                .comment("Answer Fluidium's land claim question once per chunk per tick instead of once per flowing block. Fluidium's whole job is to make flowing water and lava cheaper, and it does that by letting a fluid block skip its update when nobody is anywhere near it. The catch is the very first thing it checks on every single fluid update, before the cheap checks and before the dice roll that decides whether to skip at all, is whether the block sits inside a land claim. With FTB Chunks installed that question is not a flag read: it asks the claim mod for its manager, builds a throwaway chunk position object and a throwaway wrapper around it, then does a map lookup, and it does all of that for every flowing block of every waterfall, every lava lake edge and every draining pool in the world, twenty times a second. A single flowing pool is hundreds of those a tick and they are all asking about the same one or two chunks. With this on the answer for a chunk is remembered for the rest of that game tick in a small fixed table, so the first fluid block in a chunk pays the claim lookup and the rest read the remembered answer. Nothing is remembered across ticks, so claiming or unclaiming land takes effect on the very next tick exactly as it does now, and the table is a fixed size so it can never grow. Turn it off for stock behaviour.")
                .define("cacheClaimLookups", true), v -> fluidiumCacheClaimLookups = v);
        gate(builder
                .comment("Let Fluidium forget about the dimension you just walked out of. Fluidium keeps a list, per dimension, of the chunks that still have a player close enough to matter, and fluids outside that list are the ones allowed to skip updates. That list is only rebuilt for a dimension when something marks the dimension as stale, and the mod marks it when a player logs in, logs out, moves across a chunk border or moves far enough up or down. It also marks it when a player arrives through a portal, but it only ever marks the dimension the player arrived in, never the one they left. So the moment you step through a nether portal the list for the side you came from freezes with your old position still in it, and it stays frozen for as long as nobody else in that dimension moves. Every fluid in those chunks keeps updating at full speed forever, which is exactly the work Fluidium exists to avoid, and the more you portal around the more of these frozen pockets you leave behind. With this on the dimension you left is marked stale too, so it rebuilds on its next tick and, with nobody there any more, empties out and goes back to skipping fluid updates. Nothing else changes: the rebuild is the mod's own code, run one tick later than it already runs for the destination. Turn it off for stock behaviour.")
                .define("refreshDepartedDimension", true), v -> fluidiumRefreshDepartedDimension = v);
        builder.pop();









        builder.comment("Farmer's Delight patches.").push("farmersdelight");
        gate(builder
                .comment("Look for items around a basket without rebuilding the search boxes and streaming the results every tick. A basket is Farmer's Delight's hopper, and like a hopper it counts down a transfer cooldown every tick, but because the cooldown is only ever set back to eight after a successful pickup, a basket with nothing to pick up runs its search again on every single tick. That search takes one of the six collection shapes, which are fixed constants, and converts it back into a fresh list of boxes, allocates a new box for the offset, opens a stream, flat maps a second stream over the entities the level hands back and collects the whole thing into yet another list, all so the caller can walk it and stop at the first item it manages to absorb. That is six or seven throwaway objects per basket per tick, twenty times a second, for every basket in every loaded chunk, and item farms are built out of rows of them. With this on the boxes for each of the six facings are worked out once and kept, and the search walks the entities the level returns directly and stops at the first successful pickup, which is exactly where the stock code stopped. The entities looked at, the order they are looked at in and the item that gets absorbed are all unchanged. Turn it off for stock behaviour.")
                .define("leanBasketScan", true), v -> farmersdelightLeanBasketScan = v);
        gate(builder
                .comment("Stop a basket resending an item entity to every nearby client when it absorbed nothing from it. When a basket tries to take an item it copies the stack, pushes the copy through its slots, and then writes whatever is left back onto the item entity. Writing to an item entity goes through the synced data the server keeps for it, and item stacks are compared by identity there, not by contents, so a freshly made copy always counts as a change even when it holds exactly what it held before. A basket that is full, or full of something else, therefore marks every item entity floating next to it as changed every tick, and each of those turns into an entity data packet to every player in range. A pile of items sitting on a blocked basket is a steady stream of packets that say nothing. With this on the write only happens when the count actually went down, which is the only thing the insert can change, so a real partial pickup still syncs and a pickup that took nothing leaves the item entity alone. Turn it off for stock behaviour.")
                .define("skipNoopItemSync", true), v -> farmersdelightSkipNoopItemSync = v);
        gate(builder
                .comment("Skip the blocked check on a stove that has no food on it. Every stove block entity ticks whether or not anything is cooking, and the first thing it does is ask whether something is sitting on top of it, which reads the block above out of the world and runs a shape intersection against the grilling area. That answer is only ever used to decide whether to drop the food off the stove, so on an empty stove it is thrown away. Stoves are mostly empty in practice because the usual thing to do with one is put a cooking pot on it and use it as a heat source, and a kitchen can hold a lot of them. With this on an empty stove skips that lookup and the shape test, which costs six slot reads instead. A stove with food on it is checked exactly as before. Turn it off for stock behaviour.")
                .define("skipEmptyStoveWork", true), v -> farmersdelightSkipEmptyStoveWork = v);
        gate(builder
                .comment("Stop handing out tempt goals to mobs on the client. Farmer's Delight listens for entities joining a level and gives horse feed and cabbage tempt goals to the mobs that care about them, and that listener runs on the client as well as the server. Client side copies of mobs never run their goal selector at all, so every goal built there is thrown away without ever being asked anything, and building one means a fresh ingredient with its own item array plus the goal itself plus a stream over the mob's existing goals to work out the priority, for every animal that comes into view. With this on the listener does nothing on the client, where its work was already dead, and the server keeps adding the goals exactly as before. Turn it off for stock behaviour.")
                .define("skipClientTemptGoals", true), v -> farmersdelightSkipClientTemptGoals = v);
        gate(builder
                .comment("Find the priority of a mob's existing tempt goal with a plain loop instead of a stream. The helper that works out where to slot the new tempt goal opens a stream over the mob's goal list, filters it with a lambda, takes the first match and maps it through an optional, and it does that for every mob that joins a level and qualifies. The answer is the priority of the first goal in that list that is a tempt goal, which a loop finds in the same order with nothing allocated. With this on the loop is used and the number that comes back is the same one, including the minus one for a mob that has no tempt goal. Turn it off for stock behaviour.")
                .define("leanTemptGoalPriority", true), v -> farmersdelightLeanTemptGoalPriority = v);
        gate(builder
                .comment("Remember which HUD element the nourishment and comfort overlays are waiting for instead of searching the overlay list every time. Both overlays listen for the end of every HUD element being drawn, and the first thing each one does is ask Forge to find the food bar or the health bar by name, which walks the whole list of registered overlays behind a stream. That event fires once per element per frame, so in a pack with a few dozen HUD elements the two listeners between them run a few dozen of those searches every single frame, on the render thread, purely to compare the result against the element that was just drawn. The overlay list is fixed once the game has loaded, so the answer never changes. With this on each listener looks the element up once and keeps it, and the comparison after that is a pointer check. Turn it off for stock behaviour.")
                .define("cacheOverlayLookup", true), v -> farmersdelightCacheOverlayLookup = v);
        gate(builder
                .comment("Scan the item registry once per tool action when recipes load, instead of once per cutting board ingredient. Farmer's Delight's tool ingredients, such as strip with an axe or dig with a pickaxe, each build their item list by making a stack of every registered item and asking whether it can do that action. A big pack can have close to a hundred of them over only four actions, so every recipe load (server start, /reload, and a client reading the recipe list when it joins a server) walks the whole registry that many times. With this on, within one recipe load the first ingredient for an action runs exactly as before and records which items matched, and later ones for that action make fresh stacks of only those items and check each one again. Every ingredient gets the same items in the same order as separate stacks of its own, and the item registry is never walked outside a load. Turn it off for stock behaviour.")
                .define("shareToolActionScan", true), v -> farmersdelightShareToolActionScan = v);
        builder.pop();



        builder.comment("Sophisticated Backpacks patches.").push("sophisticatedbackpacks");
        gate(builder
                .comment("Let Sophisticated Backpacks leave alone every mob that is not carrying one of its backpacks. The mod listens to the living tick event, which fires for every living entity on the server every single tick, and the first thing it does there is ask the entity for its Forge persistent data and then pull the sophisticatedbackpacks compound out of it, twice. Asking for the persistent data creates and permanently attaches an empty compound to any entity that did not already have one, and that empty compound is then written into the save file as a ForgeData tag for the rest of that entity's life, while pulling out a compound that is not there hands back a freshly allocated one that is thrown away immediately. A world with a few hundred mobs loaded is paying a couple of thousand throwaway compound tags a second and is quietly growing the saved data of everything that has ever ticked, all to find out that the mob has no backpack. This reads the persistent data field directly without creating it, and if the mob has no sophisticatedbackpacks compound then neither branch of the handler could have done anything, so the handler is skipped outright. The same check is put in front of the chunk unload path that clears a dropped backpack's contents id, which reads the same data the same way for every monster that leaves the level. Mobs that actually spawned wearing a backpack take exactly the stock path, so mob backpack spawning, the jukebox upgrade they can carry and the contents cleanup all behave as before. Turn it off for stock behaviour.")
                .define("skipEntityDataChecks", true), v -> sophisticatedbackpacksSkipEntityDataChecks = v);
        builder.pop();




        builder.comment("Lionfish API patches.").push("lionfishapi");
        gate(builder
                .comment("Make the fluid walking check that Lionfish API bolts onto every living thing's movement cheaper, with the exact same result. The library hooks into the movement code of every living entity in the game, on both the server and your client, and every time one of them moves while not going up it looks at twelve spots around its feet for fluid, allocating a fresh table of twelve offsets and twelve block positions to do it, purely so that a mod can say this entity may stand on this fluid. In practice only the Ignitium boots from L_Ender's Cataclysm use it, to walk on lava, but every cow, zombie and villager pays for it every tick. Those twelve spots are really only six different blocks, because half of the offsets round down onto a block that was already checked, and a repeat can never change the answer since the check only takes a strictly higher value. With this on the six real blocks are read once each in the same order, with no allocation at all when there is no fluid next to the entity, which is nearly always, and the whole thing is skipped outright if nothing is listening for the stand on fluid event. When fluid is found the exact same shape test is used, the same event is fired and the same result is applied. Turn it off for stock behaviour.")
                .define("leanFluidCollision", true), v -> lionfishapiLeanFluidCollision = v);
        gate(builder
                .comment("Stop Lionfish API firing an event every time the game asks which render layer a fluid block belongs in, when nothing is listening for it. The library hooks the lookup the chunk builder makes for every single water and lava block while it builds the meshes of a chunk, creates a new event object and sends it through the whole Forge event bus, so that a mod could swap the layer. Cataclysm itself does not listen for it, and when nothing does every one of those events is thrown away and the layer that comes back is always the normal one. With this on the event is only built and sent when at least one listener is actually registered for it, and it is checked live every time, so a mod that does listen gets it exactly as before. Client side only. Turn it off for stock behaviour.")
                .define("skipIdleFluidRenderEvent", true), v -> lionfishapiSkipIdleFluidRenderEvent = v);
        gate(builder
                .comment("Remember which model part has which name instead of searching for it over and over. Models built on Lionfish API, which includes a good part of L_Ender's Cataclysm, play their keyframe animations by looking every animated bone up by name, every frame, for every one of those mobs on screen. The lookup builds a brand new list of every part of the model, streams through it and compares names until it finds a match, so a boss with eighty parts and forty animated bones does a few thousand string compares and forty throwaway lists per frame just to find parts that never change. The library even declares a field for caching this and then never uses it. With this on each model keeps its own small name to part table, filled the first time a name is asked for, and hands back the same part the search would have found, the first one with that name. Client side only. Turn it off for stock behaviour if an addon model ever swaps its parts out after it is built.")
                .define("cacheModelDescendants", true), v -> lionfishapiCacheModelDescendants = v);
        gate(builder
                .comment("Draw Lionfish API model boxes (L_Ender's Cataclysm bosses, mobs and 3D items) through Embeddium's bulk vertex writer, the same path the Citadel option uses. Stock creates a new vector for every face and every corner of every box and pushes each corner through the slow one vertex at a time path, every frame. With this on each box's corners are worked out once and then written in bulk with the same math, so the result is the same vertices. The only possible difference is that a corner landing exactly on zero may be written as plus zero instead of minus zero, which is the same point. Client. Turn it off for stock behaviour.")
                .define("fastModelRender", true), v -> lionfishapiFastModelRender = v);
        gate(builder
                .comment("Reuse one rotation object when Lionfish API turns a model box, instead of creating up to three new ones per box per frame. The rotation is filled with exactly the same values, so boxes turn exactly the same. Client. Turn it off for stock behaviour.")
                .define("reuseBoxRotations", true), v -> lionfishapiReuseBoxRotations = v);
        gate(builder
                .comment("Remember the list of parts of each Cataclysm model instead of building a new list every frame to reset the model's pose. Every Cataclysm model always lists the same fixed parts in the same order, so the reset is exactly the same. Models from other mods built on Lionfish API are left alone. Client. Turn it off for stock behaviour.")
                .define("cacheModelParts", true), v -> lionfishapiCacheModelParts = v);
        gate(builder
                .comment("Make Lionfish API's attack animation steps reuse their bookkeeping. Every step of an attack animation copied one table of part movements into another and threw away and recreated a movement object for every animated part, every frame, for every boss mid attack. With this on the two tables are swapped instead of copied and the movement objects are cleared and reused, so each part moves by exactly the same amounts. Client. Turn it off for stock behaviour.")
                .define("leanModelAnimator", true), v -> lionfishapiLeanModelAnimator = v);
        gate(builder
                .comment("Play Lionfish API keyframe animations (Coralssus, Wadjet and other newer Cataclysm models) with plain loops instead of building a pair of throwaway functions per animated bone per frame. Same keyframe search, same math, same order, so the animation is exactly the same. Client. Turn it off for stock behaviour.")
                .define("leanKeyframeAnimation", true), v -> lionfishapiLeanKeyframeAnimation = v);
        builder.pop();



        builder.comment("Create Crafts & Additions patches.").push("createaddition");
        gate(builder
                .comment("Fix the electric motor, alternator, tesla coil and accumulator hum restarting every quarter second. Create Crafts & Additions copied Create's SoundScapes but dropped the not on the ambient sounds setting, so with ambient sounds turned on it treats them as turned off and stops every running sound loop every 5 ticks, then the next block tick starts a brand new one. With this on the loops keep playing and only stop when no machine nearby is making that sound anymore, same as Create's own. Turn it off for stock behaviour.")
                .define("fixAmbientSoundToggle", true), v -> createadditionFixAmbientSoundToggle = v);
        gate(builder
                .comment("Tick the Crafts & Additions sound scapes once per client tick instead of twice. Its handler has no phase check, so it runs at both the start and the end of every client tick and can prune sounds twice in the same tick. With this on it only runs at the end of the tick, which is where Create runs its own. Turn it off for stock behaviour.")
                .define("soundscapeTickOnce", true), v -> createadditionSoundscapeTickOnce = v);
        gate(builder
                .comment("Run the held wire spool check once per client tick instead of twice, and clear the held wire flag when your hand is empty. The flag was only ever updated while holding something, so after holding a connected spool it stayed on and every connector on screen reread the selected item's NBT every frame. Visuals are unchanged. Turn it off for stock behaviour.")
                .define("leanHeldWireCheck", true), v -> createadditionLeanHeldWireCheck = v);
        builder.pop();



        builder.comment("Dungeons Delight patches.").push("dungeonsdelight");
        gate(builder
                .comment("Give every player their own Tenacity heal timer. Stock keeps the timer in one field on the shared effect object, so every player with Tenacity overwrites it for everyone else and a well fed player slows down the healing of a starving one. With an amplifier of 3 or more the timer can also reach zero, and the next check divides by it and crashes the server tick. With this on each player heals on the interval their own food level gives, and a zero interval heals every tick instead of crashing. Turn it off for stock behaviour.")
                .define("perPlayerTenacityInterval", true), v -> dungeonsdelightPerPlayerTenacityInterval = v);
        gate(builder
                .comment("Swap a normal effect for its monster version with one lookup instead of walking every active effect every tick. Stock loops over all effects of every mob with a monster effect each tick, and when it does swap it removes an effect in the middle of that loop, which throws a ConcurrentModificationException that vanilla silently swallows. Same swap, no exception from the inner loop. Turn it off for stock behaviour.")
                .define("leanMonsterEffectSwap", true), v -> dungeonsdelightLeanMonsterEffectSwap = v);
        gate(builder
                .comment("Only refresh the Monster Yam's Strength and Speed aura on nearby undead when it is half used up. Stock reapplies both effects to every undead in range every single tick, and each reapply strips and re adds the attribute modifiers, so the server sends an attribute update for every one of those mobs every tick. The buff stays on without gaps. After leaving the aura it can wear off up to half a second sooner. Turn it off for stock behaviour.")
                .define("leanYamAura", true), v -> dungeonsdelightLeanYamAura = v);
        gate(builder
                .comment("Stop the Monster Yam adding the same rotten zombie to the world twice on Hard. The second add is always rejected by vanilla because the zombie is already there, but it logs an 'UUID of added entity already exists' warning for every zombie of every summon. Nothing else changes. Turn it off for stock behaviour.")
                .define("noDuplicateZombieAdd", true), v -> dungeonsdelightNoDuplicateZombieAdd = v);
        gate(builder
                .comment("Roll the Feral Bite chance with the thread's shared random instead of building a new java.util.Random for every hurt event of every mob in the world. Same one in four chance. Turn it off for stock behaviour.")
                .define("sharedFeralBiteRandom", true), v -> dungeonsdelightSharedFeralBiteRandom = v);
        gate(builder
                .comment("Skip drawing the Ravenous Rush screen overlay when it is fully transparent. Stock draws it every frame as a full screen quad with zero alpha even when you have no effects at all. Nothing visible changes. Client only. Turn it off for stock behaviour.")
                .define("skipInvisibleRushOverlay", true), v -> dungeonsdelightSkipInvisibleRushOverlay = v);
        builder.pop();

        builder.comment("Chimes patches.").push("chimes");
        gate(builder
                .comment("Stop wind chimes and wind bells looking for phantoms on the client. Every chime and bell ticks on both logical sides and each tick asks the level for every phantom in a box 51 blocks wide for chimes and 9 for bells, then clears their target and plays the phantom hurt sound. On the client the target clear does nothing because phantom targets are only decided by the server, and the sound call from a client level with no player attached plays nothing, so the whole scan is thrown away. With this on the client skips the scan and the server keeps doing it exactly as before. Nothing visible or audible changes. Turn it off for stock behaviour.")
                .define("skipClientPhantomScan", true), v -> chimesSkipClientPhantomScan = v);
        gate(builder
                .comment("How often, in ticks, each wind chime and wind bell scans for phantoms on the server. Stock scans every tick, and a chime's box is 51 blocks wide, so it walks roughly 64 entity sections per chime per tick just to find the phantoms it should calm, which adds up fast with a porch full of chimes. Each chime keeps its own offset so they do not all scan on the same tick. A phantom that picks a target near a chime can keep it for up to this many ticks minus one before the chime clears it; a phantom takes seconds to line up a swoop so this is not noticeable at small values. 1 scans every tick like stock.")
                .defineInRange("phantomScanInterval", 4, 1, 40), 1, v -> chimesPhantomScanInterval = v);
        builder.pop();


    }

    public static void onLoad(ModConfigEvent.Loading event) {
        bake();
    }

    public static void onReload(ModConfigEvent.Reloading event) {
        bake();
    }

    public static void setFastBiomeBlend(boolean value) {
        vanillaFastBiomeBlend = masterEnabled && value;
        if (SPEC.isLoaded()) {
            fastBiomeBlendValue.set(value);
        }
    }

    public static void save() {
        if (SPEC.isLoaded()) {
            SPEC.save();
        }
    }

    private static void bake() {
        if (!SPEC.isLoaded()) {
            return;
        }
        for (Runnable baker : BAKERS) {
            baker.run();
        }
    }
}
