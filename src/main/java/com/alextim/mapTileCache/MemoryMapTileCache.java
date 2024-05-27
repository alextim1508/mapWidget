package com.alextim.mapTileCache;

import com.alextim.mapTileControl.MapTile;
import com.alextim.mapTileSource.TileSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;;

public class MemoryMapTileCache implements MapTileCache {

    private static final int CACHE_SIZE = 200;

    private final int cacheSize;

    private final Map<String, MapTile> hash;
    private final LinkedList<MapTile> lru;

    public MemoryMapTileCache() {
        this(CACHE_SIZE);
    }

    public MemoryMapTileCache(int cacheSize) {
        this.cacheSize = cacheSize;
        hash = new HashMap<>(cacheSize);
        lru = new LinkedList<>();
    }

    @Override
    public void addTile(MapTile tile) {
        if (hash.put(tile.getKey(), tile) == null) {
            lru.addFirst(tile);
            if (hash.size() > cacheSize || lru.size() > cacheSize) {
                removeOldEntries();
            }
        }
    }

    @Override
    public MapTile getTile(TileSource source, int x, int y, int z) {
        MapTile entry = hash.get(MapTile.getTileKey(source, x, y, z));
        if (entry == null)
            return null;
        lru.remove(entry);
        lru.addFirst(entry);
        return entry;
    }

    protected void removeOldEntries() {
        while (lru.size() > cacheSize) {
            removeEntry(lru.getLast());
        }
    }

    protected void removeEntry(MapTile mapTile) {
        hash.remove(mapTile.getKey());
        lru.remove(mapTile);
    }

    @Override
    public void clear() {
        hash.clear();
        lru.clear();
    }

    @Override
    public int getTileCount() {
        return hash.size();
    }

    @Override
    public int getCacheSize() {
        return cacheSize;
    }
}
