package xyz.syodo.form.response.defaults;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.syodo.form.response.FormResponse;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class FormCustomResponse extends FormResponse {
    private Int2ObjectOpenHashMap<Object> responses;
    private Int2ObjectOpenHashMap<FormIDResponse> dropdownResponses;
    private Int2ObjectOpenHashMap<String> inputResponses;
    private HashMap<Integer, Float> sliderResponses;
    private Int2ObjectOpenHashMap<FormIDResponse> stepSliderResponses;
    private HashMap<Integer, Boolean> toggleResponses;
    private Int2ObjectOpenHashMap<String> labelResponses;
}
