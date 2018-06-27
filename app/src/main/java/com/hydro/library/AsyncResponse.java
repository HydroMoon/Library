package com.hydro.library;

import java.util.List;

public interface AsyncResponse {
    void AResponse(List<Object> bList);
}

interface AdapterParse {
    void SendData(List<Object> bList);
}

interface DismissDialog {
    void Dismiss();
}