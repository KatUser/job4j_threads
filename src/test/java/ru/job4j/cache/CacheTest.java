package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    @Test
    public void whenAddFind() throws OptimisticException {
        Base base = new Base(1,  "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        Base base = new Base(1,   "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(1);
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenMultiUpdateThrowsException() throws OptimisticException {
        Base base = new Base(1,  "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenCannotFindBaseById() {
        Cache cache = new Cache();
        assertThat(cache.findById(1)).isEmpty();
    }

    @Test
    public void whenUpdateBaseThenCacheReturnsNotNull() throws OptimisticException {
        Cache cache = new Cache();
        Base base = new Base(1, "Base", 1);
        cache.add(base);
        assertThat(cache.update(new Base(1, "Base updated", 1)))
                .isNotEqualTo(null);
    }

    @Test
    public void whenAddBaseThenCacheReturnsNotNull() throws OptimisticException {
        Cache cache = new Cache();
        Base base = new Base(1, "Base", 1);
        assertThat(cache.add(base)).isNotEqualTo(null);
    }

    @Test
    public void whenBaseVersionIsUpdatedTwice() throws OptimisticException {
        Base base = new Base(1, "Base", 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base", 1));
        cache.update(new Base(1, "Base", 2));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.get().version())
                .isEqualTo(3);

    }

    @Test
    public void whenDeleteThenCannotFindById() throws OptimisticException {
        Cache cache = new Cache();
        Base base = new Base(1, "Base", 1);
        cache.add(base);
        cache.delete(1);
        assertThat(cache.findById(1)).isEmpty();
    }
}