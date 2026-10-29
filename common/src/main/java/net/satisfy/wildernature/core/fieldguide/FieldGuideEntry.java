package net.satisfy.wildernature.core.fieldguide;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.List;

public record FieldGuideEntry(
        Identifier id,
        Identifier entityId,
        Identifier ambientSound,
        boolean friendly,
        boolean neutral,
        boolean defensive,
        boolean tameable,
        boolean male,
        String title,
        String food,
        List<Identifier> biomes
) {
    public static FieldGuideEntry fromJson(Identifier id, JsonObject jsonObject) {
        Identifier entityId = Identifier.parse(GsonHelper.getAsString(jsonObject, "entity"));
        Identifier ambientSound = jsonObject.has("ambient_sound") ? Identifier.parse(GsonHelper.getAsString(jsonObject, "ambient_sound")) : null;
        boolean friendly = GsonHelper.getAsBoolean(jsonObject, "friendly", false);
        boolean neutral = GsonHelper.getAsBoolean(jsonObject, "neutral", false);
        boolean defensive = GsonHelper.getAsBoolean(jsonObject, "defensive", false);
        boolean tameable = GsonHelper.getAsBoolean(jsonObject, "tameable", false);
        boolean male = GsonHelper.getAsBoolean(jsonObject, "male", false);
        String title = jsonObject.has("title") ? GsonHelper.getAsString(jsonObject, "title") : null;
        String food = jsonObject.has("food") ? GsonHelper.getAsString(jsonObject, "food") : null;

        JsonObject spawnObject = GsonHelper.getAsJsonObject(jsonObject, "spawn", new JsonObject());
        List<Identifier> biomes = parseResourceLocationList(GsonHelper.getAsJsonArray(spawnObject, "biomes", new JsonArray()));

        return new FieldGuideEntry(id, entityId, ambientSound, friendly, neutral, defensive, tameable, male, title, food, List.copyOf(biomes));
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeIdentifier(this.id);
        friendlyByteBuf.writeIdentifier(this.entityId);
        friendlyByteBuf.writeBoolean(this.ambientSound != null);
        if (this.ambientSound != null) {
            friendlyByteBuf.writeIdentifier(this.ambientSound);
        }
        friendlyByteBuf.writeBoolean(this.friendly);
        friendlyByteBuf.writeBoolean(this.neutral);
        friendlyByteBuf.writeBoolean(this.defensive);
        friendlyByteBuf.writeBoolean(this.tameable);
        friendlyByteBuf.writeBoolean(this.male);
        friendlyByteBuf.writeBoolean(this.title != null);
        if (this.title != null) {
            friendlyByteBuf.writeUtf(this.title);
        }
        friendlyByteBuf.writeBoolean(this.food != null);
        if (this.food != null) {
            friendlyByteBuf.writeUtf(this.food);
        }
        friendlyByteBuf.writeVarInt(this.biomes.size());
        this.biomes.forEach(friendlyByteBuf::writeIdentifier);
    }

    public static FieldGuideEntry read(FriendlyByteBuf friendlyByteBuf) {
        Identifier id = friendlyByteBuf.readIdentifier();
        Identifier entityId = friendlyByteBuf.readIdentifier();
        Identifier ambientSound = friendlyByteBuf.readBoolean() ? friendlyByteBuf.readIdentifier() : null;
        boolean friendly = friendlyByteBuf.readBoolean();
        boolean neutral = friendlyByteBuf.readBoolean();
        boolean defensive = friendlyByteBuf.readBoolean();
        boolean tameable = friendlyByteBuf.readBoolean();
        boolean male = friendlyByteBuf.readBoolean();
        String title = friendlyByteBuf.readBoolean() ? friendlyByteBuf.readUtf() : null;
        String food = friendlyByteBuf.readBoolean() ? friendlyByteBuf.readUtf() : null;
        int biomeCount = friendlyByteBuf.readVarInt();
        if (biomeCount < 0 || biomeCount > 4096) throw new IllegalArgumentException("Invalid biome count");
        List<Identifier> biomes = new ArrayList<>(biomeCount);
        for (int i = 0; i < biomeCount; i++) biomes.add(friendlyByteBuf.readIdentifier());

        return new FieldGuideEntry(id, entityId, ambientSound, friendly, neutral, defensive, tameable, male, title, food, List.copyOf(biomes));
    }

    public boolean hasFood() {
        return this.food != null && !this.food.isBlank();
    }

    public boolean foodIsTag() {
        return this.hasFood() && this.food.startsWith("#");
    }

    private static List<Identifier> parseResourceLocationList(JsonArray jsonArray) {
        List<Identifier> resourceLocations = new ArrayList<>();

        for (int index = 0; index < jsonArray.size(); index++) {
            resourceLocations.add(Identifier.parse(jsonArray.get(index).getAsString()));
        }

        return resourceLocations;
    }
}