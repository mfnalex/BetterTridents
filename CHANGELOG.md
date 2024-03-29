## 2.2.3
- Minor optimizations
- Fixed loyalty tridents entering portals despite being disabled in the config
- Fixed tridents with loyalty already returning to player at Y=-60

## 2.1.1
- Improved void saving in 1.17+

## 2.1.0
- Tridents will not be instantly returned when hitting Y=0
- Fixed players without "bettertridents.savevoid" permissions being able to use the "void-saving" feature anyway

## 2.0.2
- Fixed tridents disappearing when throwing similar tridents (same durability, etc.) from the offhand

## 2.0.1
- Fixed tridents getting lost on return when throwing many of them from the offhand while having "return-to-offhand" set to "true"

## 2.0.0
- Renamed plugin to "BetterTridents"
- Added new mechanics and config options:
  - Bedrock impaling: Impaling enchantment behaves like in Bedrock (it damages ALL mobs touching water, not only ocean mobs). Default: Enabled
  - Bedrock drop chance: Raises the drop chance for tridents to the bedrock value (25% for every drowned yielding a trident + 4% per looting level) Default: Enabled
  - Return to offhand: Tridents thrown from the offhand will also return to the offhand when picking them up. Default: Enabled
  - Disable loyalty portals: Prohibit tridents with Loyalty to travel through portals to avoid losing them. Default: Enabled
- Added automatic config updater
- Added reload command (/bettertridents, permission bettertridents.reload)

## 1.1.0
- Added UpdateChecker and bStats