package com.qubid.backend.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String msg;
    public ApiResponse(T data,String msg){
        this.data=data;
        this.msg=msg;
    }

}
