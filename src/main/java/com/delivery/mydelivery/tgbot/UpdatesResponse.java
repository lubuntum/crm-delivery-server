package com.delivery.mydelivery.tgbot;


import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class UpdatesResponse {
    private boolean status;
    private List<Update> result;

    public boolean getStatus() {
        return status;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}
