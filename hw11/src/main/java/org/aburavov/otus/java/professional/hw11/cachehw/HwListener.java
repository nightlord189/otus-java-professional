package org.aburavov.otus.java.professional.hw11.cachehw;

public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
