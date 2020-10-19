package be.vdab.frituur.forms;

import javax.validation.constraints.NotBlank;

public class BeginletterForm {
    @NotBlank
    private final String beginletter;

    public String getBeginletter() {
        return beginletter;
    }

    public BeginletterForm(String beginletter) {
        this.beginletter = beginletter;
    }
}
