package me.Paabl0.net.misc;

public class DualObject {

    private Object key;
    private Object value;

    public DualObject(Object key, Object value){
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

}
