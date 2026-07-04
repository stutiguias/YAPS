# Changelog

## 0.9.1

### Added
- Added `/yaps blockinfo` and `/yaps bi` to inspect the protected block the player is looking at.
- Added `/yaps blockwand` and `/yaps bw` to give a `YAPS Block Info Wand`.
- Added protected block owner lookup with support for memory lookup, database lookup, and pending `SaveQueue` changes.
- Added `yaps.blockinfo` permission and included it in `yaps.admin`.

### Changed
- Updated project version to `0.9.1`.
- Updated project description in `pom.xml`.
- Expanded `README.md` with the full project description, command list, permissions, requirements, and current feature status.
- Updated help output to show the new protected block info commands when the player has permission.