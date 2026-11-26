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
- CERBON's API: the multipart bounding box check is cached per entity instead of running on every bounding box call
- ImmediatelyFast: render layer order is worked out once per layer instead of on every comparison while sorting
- Placebo: item component hashing (used by Apotheosis affixes) no longer builds a stream and array per lookup
- Sound Physics Remastered: sound rays skip through air and look up each block once, with no allocations per block
- Malum: no longer creates, ticks and saves its soul, curio, touch of darkness and geas data on every mob
- Eidolon: Repraised: thrall checks no longer create empty data tags on every mob, and the Bonelord armor scan only runs when it's used
- Konkrete: the volume watcher thread only starts if something actually uses Konkrete sounds
- Aquamirae: entity searches (collisions, pushing, AI, attacks) no longer get wrapped and checked for Shellback parts while no Shellback is loaded
- Enderscape: block sound lookups are instant instead of scanning a list that grows to thousands of entries, and structure music only checks structures in the player's chunk
- Antarchy: its world generation profilers no longer wrap every feature and chunk when they're off
- Iron's Spells: its aquifer check during terrain generation exits right away for chunks without its structures instead of running on every block with a callback object
- Blur+: no longer writes several debug log lines on every frame

### Memory
- Too Many Recipe Viewers no longer keeps a rebuilt copy of every JEI recipe in memory (over 100 MB in big packs)
- Unused structure templates are released after 5 minutes idle
- Fixed old worlds staying in memory after leaving, from Citadel models, Bosses of Mass Destruction and Corpse renderers
- Fixed leaks in Alex's Mobs (world maps), Citadel (book entities), Alex's Caves (cave maps), Xaero's Minimap (slime chunk tiles) and Dungeon Now Loading
- More Quest Types event buffers are now cleared regularly
- Majrusz's Difficulty no longer keeps the last player checked by the phantom spawner
- EEEAB's Mobs Immortal boss no longer keeps players who left or respawned in its target list
- EMI Loot no longer keeps the last world's loot tables and registries after leaving
- Enderscape no longer keeps players who died or left the End in its structure music maps
- Iron's Spellbooks: Arcane Anvil recipe pages share spell data instead of copying it for every weapon, spell and level

### Fixes
- Fixed some mobs (like the Netherite Monstrosity) disappearing or animating wrong when another mod changes their model
- Fixed the door fix making doors flicker or half open while clicking them
- The in-game config screen has readable names and only shows mods you have installed
- Fixed a crash with Create when ComputerCraft is not installed
- Fixed Ars Elemental spell entities (dripstone spike, water jet, magnet) crashing after a reload
- Fixed Undergarden Munchers breaking blocks with mobGriefing off
- Fixed a server tick crash when every patch is switched off because of an incompatible mod (Tickweave)
- Fixed the game getting stuck on the loading screen with Species and GeckoBetterFPS together
- EMI Loot now shows loot table changes made with LootJS (built in from EMI Loot + LootJS Fix by momo)
- Fixed doors where only the top or bottom half opened until you relogged
- Fixed Lootr chests in new structures staying as normal chests until opened (double chests turned half Lootr, half normal)
- Fixed a startup crash when MixinSquared is not installed (Moonlight, Alex's Caves, Cataclysm and others)

Every change can be turned off in the config.

## 0.0.9
- Xaero's config lookups are cached
- Create and Flywheel patches
- Guard Villagers and raccoon patches
- More small patches for other mods

## 0.0.8.1
- Fixed a crash with null block entity positions
