package user11681.usersmanual.item;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.item.Item;

public final class ItemModifiers extends Item {
    private static final Set<UUID> RESERVED_IDENTIFIERS = new HashSet<>();

    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = reserve(Item.ATTACK_DAMAGE_MODIFIER_ID);
    public static final UUID ATTACK_SPEED_MODIFIER_ID = reserve(Item.ATTACK_SPEED_MODIFIER_ID);

    public static UUID reserve(final String uuid) {
        return reserve(UUID.fromString(uuid));
    }

    public static UUID reserve(final UUID uuid) {
        RESERVED_IDENTIFIERS.add(uuid);

        return uuid;
    }

    public static UUID getOriginal(final UUID equal) {
        for (final UUID uuid : RESERVED_IDENTIFIERS) {
            if (uuid.equals(equal)) {
                return uuid;
            }
        }

        return null;
    }

    private ItemModifiers(final Settings settings) {
        super(settings);
    }
}
