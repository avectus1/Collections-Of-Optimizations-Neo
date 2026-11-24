# Changelog

By avectus1

## 0.1.0

### Performance
- Faster natural mob spawning: chunk, biome and spawn rule lookups are reused within each spawn attempt (same spawns as vanilla)
- Majrusz's Difficulty: mob cap limits are cached and the spawn rate check no longer scans every player
- Redomesticate: faster tameable checks
- StutterFix: thread priority is only set once
- Citadel: stops resending the tick rate packet every tick
- Elysium API: faster block property lookups
- More Quest Types: lighter summoned mob scan
- Not Enough Animations: freezing animation check is cached per tick
- Subtle Effects: lighter block and entity effect checks (shared biome lookups, fewer block entity lookups, no idle steam/lava checks, faster ticker removal, no per-block random generators in the Nether)
- The Undergarden: Rotspawn skip their big repellent scan when nothing nearby can scare them, Scintlings only check griefing when they can leave goo
- Perception: trail colors are parsed once and trails skip an unneeded player search
- Shoulder Surfing: adaptive crosshair item patterns are compiled once
- Mowzie's Mobs: no longer creates, ticks and saves its data on every living entity until it's actually needed, and glow effects reuse their render types
- Too Many Recipe Viewers: recipe slots from JEI plugins build their item list once instead of several times per frame
- GeckoLib: easing curves are built once instead of per bone and frame, keyframe lookups skip throwaway objects (works alongside GeckoLib Better FPS)

### Memory
- Unused structure templates are released after 5 minutes idle
- Fixed old worlds staying in memory after leaving, from Citadel models, Bosses of Mass Destruction and Corpse renderers
- Fixed leaks in Alex's Mobs (world maps), Citadel (book entities), Alex's Caves (cave maps), Xaero's Minimap (slime chunk tiles) and Dungeon Now Loading
- More Quest Types event buffers are now cleared regularly
- Majrusz's Difficulty no longer keeps the last player checked by the phantom spawner
- EEEAB's Mobs Immortal boss no longer keeps players who left or respawned in its target list
- EMI Loot no longer keeps the last world's loot tables and registries after leaving
- Iron's Spellbooks: Arcane Anvil recipe pages share spell data instead of copying it for every weapon, spell and level

### Fixes
- Fixed a crash with Create when ComputerCraft is not installed
- Fixed Ars Elemental spell entities (dripstone spike, water jet, magnet) crashing after a reload
- Fixed Undergarden Munchers breaking blocks with mobGriefing off

Every change can be turned off in the config.

## 0.0.9
- Xaero's config lookups are cached
- Create and Flywheel patches
- Guard Villagers and raccoon patches
- More small patches for other mods

## 0.0.8.1
- Fixed a crash with null block entity positions
