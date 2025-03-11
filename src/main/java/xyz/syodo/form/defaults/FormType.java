package xyz.syodo.form.defaults;

import lombok.Getter;

@Getter
public enum FormType {
    DEFAULT(0);

    private final int id;

    FormType(int id) {
        this.id = id;
    }
}
