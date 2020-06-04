import java.util.Map;

public interface Sort {
    <K,V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map);
}
