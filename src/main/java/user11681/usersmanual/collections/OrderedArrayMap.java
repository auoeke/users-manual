package user11681.usersmanual.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

public class OrderedArrayMap<K, V> extends ArrayMap<K, V> {
    public OrderedArrayMap(final Map<K, V> map) {
        super(map);
    }

    public OrderedArrayMap(final Supplier<V> defaultValueSupplier, final Iterable<K> keys) {
        super(defaultValueSupplier, keys);
    }

    @SafeVarargs
    public OrderedArrayMap(final Supplier<V> defaultValueSupplier, final K... keys) {
        super(defaultValueSupplier, keys);
    }

    public OrderedArrayMap() {
        super();
    }

    public OrderedArrayMap(final int initialLength) {
        super(initialLength);
    }

    @Override
    public void putAll(@Nonnull final Map<? extends K, ? extends V> map) {
        final int size = this.size;

        if (size + map.size() >= this.length) {
            this.resize(size * 2);
        }

        final Iterator<? extends K> keys = map.keySet().iterator();
        final Iterator<? extends V> values = map.values().iterator();

        while (keys.hasNext()) {
            final K key = keys.next();

            int index = this.indexOfKey(key);

            if (index < 0) {
                index = -index - 1;
            }

            ++this.size;

            if (index < size) {
                this.shift(1, index, size);
            }

            this.keys[index] = key;
            this.values[index] = values.next();
        }
    }

    @Override
    public V put(final K key, final V value) {
        final int size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        int index = this.indexOfKey(key);

        final V previous;

        if (index < 0) {
            index = size;
            ++this.size;
            previous = null;
            this.keys[index] = key;
        } else {
            previous = this.values[index];
        }

        this.values[index] = value;

        return previous;
    }

    @Override
    public int indexOfKey(final Object target) {
        final K[] keys = this.keys;
        final int size = this.size;

        for (int index = 0; index < size; index++) {
            if (keys[index].equals(target)) {
                return index;
            }
        }

        return -size - 1;
    }

    @Override
    public int indexOfValue(final Object target) {
        final V[] values = this.values;
        int index = 0;

        for (int size = this.size; index < size; index++) {
            if (values[index].equals(target)) {
                return index;
            }
        }

        return -index - 1;
    }

    public int lastIndexOfKey(final Object target) {
        final K[] keys = this.keys;
        final int size = this.size;
        int index = -size - 1;

        for (int i = 0; i < size; i++) {
            if (keys[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public int lastIndexOfValue(final Object target) {
        final V[] values = this.values;
        final int size = this.size;
        int index = -size - 1;

        for (int i = 0; i < size; i++) {
            if (values[i].equals(target)) {
                index = i;
            }
        }

        return index;
    }
}
