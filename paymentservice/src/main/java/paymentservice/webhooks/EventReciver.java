package paymentservice.webhooks;

import com.stripe.model.Event;
import jakarta.persistence.Entity;
import paymentservice.entity.BaseModels;


public class EventReciver  extends BaseModels {
    String account;
    String apiVersion;
    Long created;
    Event.Data data;
    Boolean livemode;
    String object;
    Long pendingWebhooks;
    Event.Request request;
    String type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Event.Data getData() {
        return data;
    }

    public void setData(Event.Data data) {
        this.data = data;
    }


    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getPendingWebhooks() {
        return pendingWebhooks;
    }

    public void setPendingWebhooks(Long pendingWebhooks) {
        this.pendingWebhooks = pendingWebhooks;
    }

    public Event.Request getRequest() {
        return request;
    }

    public void setRequest(Event.Request request) {
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
