package cartservice.expcetions;

import java.time.LocalDateTime;

public class ExceptionMessageDto {
    private String message;
    private int code;
    private LocalDateTime time;

    public ExceptionMessageDto(String message, int code, LocalDateTime time) {
        this.message = message;
        this.code = code;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
