package xyz.syodo.form.response.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.syodo.form.response.FormResponse;

@Getter
@AllArgsConstructor
public class FormIDResponse extends FormResponse {
    protected final int id;
    protected final String text;
}
