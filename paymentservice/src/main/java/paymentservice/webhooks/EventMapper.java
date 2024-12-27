package paymentservice.webhooks;

import com.stripe.model.Event;

public class EventMapper {
    public static EventReciver fromWebhookEven(Event event){
        EventReciver reciver=new EventReciver();
        reciver.setAccount(event.getAccount());
        reciver.setCreated(event.getCreated());
        reciver.setData(event.getData());
//        reciver.setId(event.getId());
        reciver.setLivemode(event.getLivemode());
        reciver.setApiVersion(event.getApiVersion());
        reciver.setType(event.getType());
        reciver.setPendingWebhooks(event.getPendingWebhooks());
        reciver.setRequest(event.getRequest());
        return reciver;

    }
}
