package xyz.syodo.form.element;

import com.google.gson.JsonObject;

public abstract class Element {
    protected abstract Type getType();

    public abstract JsonObject toJson();

    public enum Type {
        DROPDOWN,
        INPUT,
        LABEL,
        SLIDER,
        STEPSLIDER,
        TOGGLE
    }
}
