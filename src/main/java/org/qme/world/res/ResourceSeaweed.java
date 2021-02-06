package org.qme.world.res;

import org.qme.world.TileType;

public class ResourceSeaweed extends Resource {
    public ResourceSeaweed() {
        super();
    }

    @Override
    public int getSpawnChance(TileType type) {
        if (type == TileType.OCEAN) {
            return 30;
        } else if (type == TileType.SEA) {
            return 25;
        } else {
            return 0;
        }
    }

    @Override
    public ResourceType getType() {
        return ResourceType.SEAWEED;
    }

    @Override
    public String getTexturePath() {
        return "Seaweed.png";
    }
}
