package practicekafka.practicekafka.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SendEmailResponseDto {
        private String from;
        private String to;
        private String subject;
        private String body;
    }

