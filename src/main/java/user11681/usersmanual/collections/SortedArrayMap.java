package user11681.usersmanual.collections;

import com.google.common.annotations.VisibleForTesting;

@VisibleForTesting
public class SortedArrayMap<K extends Comparable<K>, V extends Comparable<V>> extends ArrayMap<K, V> {
    public SortedArrayMap(final int initialLength) {
        super(initialLength);
    }

    public SortedArrayMap(final ParallelList<K, V> from) {
        super(from);
    }

    @Override
    public boolean add(final K key, final V value) {
        final int size = this.size;

        if (size == this.length) {
            this.resize(size * 2);
        }

        int index = this.indexOfFirstKey(key);

        if (index < 0) {
            index = -index - 1;
        }

        this.size++;

        if (index < size) {
            this.shift(1, index, size);
        }

        this.keys[index] = key;
        this.values[index] = value;

        return true;
    }

    protected <T extends Comparable<T>> int binarySearchFirst(final T[] array, final T target) {
        int first = 0;
        int last = this.size - 1;

        while (first <= last) {
            final int middle = (first + last) / 2;
            final int previous = middle - 1;
            final T middleElement = array[middle];

            if (target.compareTo(middleElement) <= 0) {
                last = previous;
            } else if (middle != 0 && target.compareTo(array[previous]) <= 0 && !middleElement.equals(target)) {
                first = middle + 1;
            } else {
                return middle;
            }
        }

        return -first - 1;
    }

    protected <T extends Comparable<T>> int binarySearchLast(final T[] array, final T target) {
        final int end = this.size - 1;
        int first = 0;
        int last = end;

        while (first <= last) {
            final int middle = (first + last) / 2;
            final int next = middle + 1;
            final T middleElement = array[middle];

            if (target.compareTo(middleElement) < 0) {
                last = middle - 1;
            } else if (middle != end && target.compareTo(array[next]) >= 0 && !middleElement.equals(target)) {
                first = next;
            } else {
                return middle;
            }
        }

        return -first - 1;
    }

    protected void sort() {
        final int size = this.size;
        final K[] keys = this.keys;
        final V[] values = this.values;
        final K[] tempKeys = ArrayUtil.comparable(size);
        final V[] tempValues = ArrayUtil.comparable(size);

        for (int width = 1; width < size; width *= 2) {
            for (int i = 0; i < size; i += 2 * width) {
                final int right = Math.min(i + width, size);
                final int end = Math.min(i + 2 * width, size);
                int j = i;
                int k = right;

                for (int l = i; l < end; l++) {
                    if (j < right && (k >= end || keys[j].compareTo(keys[k]) <= 0)) {
                        tempKeys[l] = keys[j];
                        tempValues[l] = values[j];
                        j++;
                    } else {
                        tempKeys[l] = keys[k];
                        tempValues[l] = values[k];
                        k++;
                    }
                }
            }

            System.arraycopy(tempKeys, 0, keys, 0, size);
            System.arraycopy(tempValues, 0, values, 0, size);
        }
    }

    @Override
    public int indexOfFirstKey(final K target) {
        return this.binarySearchFirst(this.keys, target);
    }

    @Override
    public int indexOfFirstValue(final V target) {
        return this.binarySearchFirst(this.values, target);
    }

    @Override
    public int indexOfLastKey(final K target) {
        return this.binarySearchLast(this.keys, target);
    }

    @Override
    public int indexOfLastValue(final V target) {
        return this.binarySearchLast(this.values, target);
    }

    @Override
    public boolean containsKey(final K key) {
        return this.indexOfFirstKey(key) > -1;
    }

    @Override
    public boolean containsValue(final V value) {
        return this.indexOfFirstValue(value) > -1;
    }
}
