package guru.qa.niffler.data.repository.logging;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class JsonAllureAppender {

    private final String templateName = "json.ftl";
    private final AttachmentProcessor<AttachmentData> attachmentProcessor = new DefaultAttachmentProcessor();

    @SneakyThrows
    public void logJson(String jsonName, String json) {
        if (StringUtils.isNoneEmpty(json)) {
            JsonAttachment attachment = new JsonAttachment(jsonName, json);
            attachmentProcessor.addAttachment(attachment, new FreemarkerAttachmentRenderer(templateName));
        }
    }
}