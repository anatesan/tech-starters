package com.siq.iqrestmenu.web;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class RestErrorResponse {
    @Setter
    @Getter
    long errorCode;

    @Setter
    @Getter
    String message;

    @Setter
    @Getter
    String body;

}
