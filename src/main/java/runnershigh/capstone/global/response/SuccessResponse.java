package runnershigh.capstone.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessResponse<T>{
    private int httpStatusCode;
    private String httpStatusMessage;
    private T data;
}
